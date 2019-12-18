package tp.project.go_game.server;

import tp.project.go_game.logic.AppEngine;

public class Game {
	
	private AppEngine engine;
	private ClientHandler client1;
	private ClientHandler client2;
	private ServerInterpreter interpreter;
	private int oldTurnCounter;
	private boolean isOn;
	
	
	public Game(int boardSize, ClientHandler client1, ClientHandler client2) {
		this.client1 = client1;
		this.client2 = client2;
		this.oldTurnCounter = 0;
		engine = new AppEngine(boardSize);
		interpreter = new ServerInterpreter(engine);
		isOn = true;
		runGame();
	}
	
	public void runGame() {
		
		while (isOn) {
        	int out = turn(client1, client2);
        	if (out == 1) {
        		isOn = false;
        		break;
        	}
        	out = turn(client2, client1);
        	if (out == 1) {
        		isOn = false;
        		break;
        	}
        	
        }
		isOn = false;
	}
	
	public int turn (ClientHandler client1, ClientHandler client2) {
		String move;
		String response;
		int out = 0;
		do {
    		move = client1.getMove();
    		response = interpreter.handleMessage(move);
    		client1.sendMessage(response);
    	} while (oldTurnCounter == engine.getTurnCounter());
    	oldTurnCounter = engine.getTurnCounter();
    	client2.sendMessage(response);
    	if (response.equals("6")) {
    		out++;
    		client1.closeConnection();
    	}
    	return out;
	}
	
	public boolean isOn() {
		return isOn;
	}
}
