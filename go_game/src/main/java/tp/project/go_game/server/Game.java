package tp.project.go_game.server;

import tp.project.go_game.logic.AppEngine;

public class Game {
	
	private AppEngine engine;
	private ClientHandler client1;
	private ClientHandler client2;
	private int boardSize;
	private String currentColor;
	private ServerInterpreter interpreter;
	private int oldTurnCounter;
	
	
	public Game(int boardSize, ClientHandler client1, ClientHandler client2) {
		this.boardSize = boardSize;
		this.client1 = client1;
		this.client2 = client2;
		this.currentColor = "black";
		this.oldTurnCounter = 0;
		engine = new AppEngine(boardSize);
		interpreter = new ServerInterpreter(engine);
		runGame();
		
	}
	
	public void runGame() {
		String move;
		String response;
		while (client1.checkIfPresent() && client2.checkIfPresent()) {
        	do {
        		move = client1.getMove();
        		response = interpreter.handleMessage(move);
        		client1.sendMessage(response);
        	} while (oldTurnCounter == engine.getTurnCounter());
        	oldTurnCounter = engine.getTurnCounter();
        	client2.sendMessage(response);
        	do {
        		move = client2.getMove();
        		response = interpreter.handleMessage(move);
        		client2.sendMessage(response);
        	} while (oldTurnCounter == engine.getTurnCounter());
        	oldTurnCounter = engine.getTurnCounter();
        	client1.sendMessage(response);
        }
		//TODO obsluga co jak jeden wyjdzie
	}
}
