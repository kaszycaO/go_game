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
			engine.handleButtons(convertedMessage[1]);
			//nie dzialalo wczesniej, tu musi byc return
			engine.resetChanges();
			response +=  "5 ";
			
			response +=engine.getChanges();
			
			System.out.println(response);
			return response;
		}
		else {
			int X = Integer.parseInt(convertedMessage[1]);
			int Y = Integer.parseInt(convertedMessage[2]); 
			try {
				engine.handleMove(X,Y);
				response +=  "0 ";
			} catch (KoRuleViolatedException e) {
				response += "1 ";
			} catch (CoordinatesOutOfBoundsException e) {
				response += "2 ";
				//e.printStackTrace();
			} catch (SuicidalMoveException e) {
				response += "3 ";
				//e.printStackTrace();
			} catch (IntersectionTakenException e) {
				response += "4 ";
			}
		}
	
		response += engine.getChanges();
		engine.resetChanges();
		response += engine.getChangesCounter() + 1;
		engine.setChangesCounter(0);
		System.out.println(response);
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

	
	
	public String getMessageForServer(String message) {
		return this.messageForServer;
	}

}
