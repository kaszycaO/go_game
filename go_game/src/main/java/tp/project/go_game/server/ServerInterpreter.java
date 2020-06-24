package tp.project.go_game.server;

import tp.project.go_game.exceptions.*;
import tp.project.go_game.logic.AppEngine;


public class ServerInterpreter implements ServerInterpreterInterface{
	
	/*
	 * silnik gry
	 */
	private AppEngine engine;
	
	/*
	 * glowny konstruktor
	 */

	private DatabaseConnector connector;
	public ServerInterpreter(AppEngine engine) {
		
		this.engine = engine;
		connector = new DatabaseConnector();
		
	}
	
	/*
	 * funkcja przetwarzajaca komunikat od klienta, zwracajaca odpoiwedz na niego
	 */
	@Override
	public String handleMessage(String message) {
		
		String response = "";
		String[] convertedMessage = interpretMessage(message);
		
		if (convertedMessage[0].equals("button")) {
			if(convertedMessage[1].contentEquals("exit")) {
				response = "6";
				engine.changeTurn();
			} else {
				engine.resetChanges();
				engine.handleButtons(convertedMessage[1]);
			
				response =  "5 ";
				response +=engine.getFinalScore();
				
				response +=" 6";
			}
		}
		else {
			engine.resetChanges();
			int X = Integer.parseInt(convertedMessage[1]);
			int Y = Integer.parseInt(convertedMessage[2]); 
			try {
				engine.handleMove(X,Y);
				response +=  "0 ";
			} catch (KoRuleViolatedException e) {
				return response += "1 ";
			} catch (CoordinatesOutOfBoundsException e) {
				return response += "2 ";
			} catch (SuicidalMoveException e) {
				return response += "3 ";
			} catch (IntersectionTakenException e) {
				return response += "4 ";
			}



			response += engine.getChanges();
			if(response.charAt(0) == '0'){
				connector.initializeSave(engine.getTurnCounter(), response);
			}
			response += engine.getChangesCounter() + 1;
			engine.setChangesCounter(0);

		}
		return response;
	}

	/*
	 * funkcja konwertujaca wiadomosc od klienta
	 */
	public String[] interpretMessage(String message){
		String[] convertedMessage = {"","",""};
		int j = 0;
		for (int i=0;i<3;i++) {
			while(j < message.length() && (message.charAt(j) != ' ')) {
				convertedMessage[i] += message.charAt(j);
				j++;
			}
			j++;
		}
		return convertedMessage;
	}
	
	/*
	 * funkcja zwracajaca ruch bota
	 */
	public String getBotMove() {
		String move = "coordinates ";
		Integer[] coords = engine.getBMove();
		move += Integer.toString(coords[0]);
		move += " ";
		move += Integer.toString(coords[1]);
		return handleMessage(move);
	}

}
