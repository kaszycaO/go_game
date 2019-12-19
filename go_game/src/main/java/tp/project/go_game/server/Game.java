package tp.project.go_game.server;

import tp.project.go_game.logic.AppEngine;

public class Game {
	/*
	 * silnik gry
	 */
	private AppEngine engine;
	/*
	 * obiekt obslugujacy klienta 1
	 */
	private ClientHandler client1;
	/*
	 * obiekt obslugujacy klienta 2
	 */
	private ClientHandler client2;
	/*
	 * interpreter serwera
	 */
	private ServerInterpreter interpreter;
	/*
	 * zmienna przechowujaca poprzedni¹ ilosc wykonanych ruchow
	 */
	private int oldTurnCounter;
	/*
	 * flaga mowiaca o tym czy gra trwa
	 */
	private boolean isOn;
	
	/*
	 * konstruktor gry z dwoma uzytkownikami
	 */
	public Game(int boardSize, ClientHandler client1, ClientHandler client2) {
		this.client1 = client1;
		this.client2 = client2;
		this.oldTurnCounter = 0;
		engine = new AppEngine(boardSize);
		interpreter = new ServerInterpreter(engine);
		isOn = true;
		runGame();
	}
	
	/*
	 * konstruktor gry z botem
	 */
	public Game(int boardSize, ClientHandler client1) {
		this.client1 = client1;
		this.oldTurnCounter = 0;
		engine = new AppEngine(boardSize);
		interpreter = new ServerInterpreter(engine);
		isOn = true;
		runWithBot();
	}
	
	/*
	 * rozgrywka dwoch uzytkownikow
	 */
	private void runGame() {
		
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
	
	/*
	 * rozgrywka z botem
	 */
	private void runWithBot() {
		String move;
		String response;
		while (isOn) {
			do {
	    		move = client1.getMove();
	    		response = interpreter.handleMessage(move);
	    		client1.sendMessage(response);
	    	} while (oldTurnCounter == engine.getTurnCounter());
	    	 oldTurnCounter = engine.getTurnCounter();
	    	 if (response.equals("6")) break;
	    	 response = interpreter.getBotMove();
	    	 oldTurnCounter = engine.getTurnCounter();
	    	 client1.sendMessage(response);
		}
		isOn = false;
	}
	
	/*
	 * pojedyncza tura dwoch uzytkownikow
	 */
	private int turn(ClientHandler client1, ClientHandler client2) {
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
	
	/*
	 * funkcja mowiaca o tym czy gra trwa
	 */
	public boolean isOn() {
		return isOn;
	}
}
