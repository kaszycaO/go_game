package tp.project.go_game.server;

import tp.project.go_game.exceptions.*;
import tp.project.go_game.logic.AppEngine;


public class ServerInterpreter implements ServerInterpreterInterface{
	
	private AppEngine engine;
	private String messageForServer;
	
	
	public ServerInterpreter(AppEngine engine) {
		
		this.engine = engine;
		
	}
	
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
				//nie dzialalo wczesniej, tu musi byc return
			
				response =  "5 ";
				//TODO fix
				response +=engine.getFinalScore();
				
				response +=" 2";
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
				//e.printStackTrace();
			} catch (SuicidalMoveException e) {
				return response += "3 ";
				//e.printStackTrace();
			} catch (IntersectionTakenException e) {
				return response += "4 ";
			}
			response += engine.getChanges();
			response += engine.getChangesCounter() + 1;
			engine.setChangesCounter(0);
		}
		return response;
	}

	
	private String[] interpretMessage(String message){
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

	public String getBotMove() {
		String move = "coordinates ";
		Integer[] coords = engine.getBMove();
		move += Integer.toString(coords[0]);
		move += " ";
		move += Integer.toString(coords[1]);
		return handleMessage(move);
	}
	
	public String getMessageForServer(String message) {
		return this.messageForServer;
	}

}
