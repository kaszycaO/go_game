package tp.project.go_game.server;

import tp.project.go_game.exceptions.CoordinatesOutOfBoundsException;
import tp.project.go_game.exceptions.KoRuleViolatedException;
import tp.project.go_game.exceptions.SuicidalMoveException;
import tp.project.go_game.logic.AppEngine;


public class ServerInterpreter {
	
	private AppEngine engine;
	private String messageForServer;
	
	public ServerInterpreter(AppEngine engine) {
		
		this.engine = engine;
		
	}
	
	public String doMove(String message) {
		
		
		String[] convertedMessage = interpretMessage(message);
		
		if (convertedMessage[0].equals("button")) {
			engine.handleButtons();
		}
		else {
			try {
				engine.handleMove();
			} catch (KoRuleViolatedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoordinatesOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SuicidalMoveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "hi";
		

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

	
	
	public String getMessageForServer() {
		return this.messageForServer;
	}

}
