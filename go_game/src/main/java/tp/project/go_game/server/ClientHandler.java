package tp.project.go_game.server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import tp.project.go_game.logic.AppEngine;

public class ClientHandler {
	
	/**
     * gniazdko klienta
     */
    private Socket socket = null;
	/**
     * komunikaty od klienta
     */
    private Scanner fromClient = null;
    /**
     * dane wysylane do klienta
     */
    private PrintWriter toClient = null;
	public ClientHandler opponent;
	private String color;
	private ServerInterpreter interpreter;
	private AppEngine engine;
	private boolean plays;
	private int boardSize =-1;
	private int ifBot = -1;
	private int turn1 = 0;
	private int turn2 = 0;
	
	public ClientHandler(Socket socket, String color) {
		this.color = color;
		this.socket = socket;

		this.plays = true;
        initializePlayer();
	}
	
	public ClientHandler(Socket socket, String color, int boardSize) {
		this.color = color;
		this.socket = socket;
		this.plays = true;
		this.boardSize = boardSize;
        initializePlayer();
	}
	
	public void initializePlayer() {
        try {
        	fromClient = new Scanner(socket.getInputStream());
			toClient = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getParams();
	}
	
	
	public String getMessage() {
		
		String responseMessage = null;
		
		while(fromClient.hasNextLine()) {
			
			responseMessage = fromClient.nextLine();
			
			if(responseMessage!=null) {
				
				break;
			}
			
		}
		
		return responseMessage;
	}
	
	private void getParams() {
		
			String recievedMessage = getMessage();
			if (recievedMessage.equals("params")) {
				if(this.boardSize == -1) {
					toClient.println("-");
				}
				else {
					toClient.println(Integer.toString(boardSize));

				}
			}
			recievedMessage = getMessage();
			if (!recievedMessage.equals("got")) {
				String[] msg = {"",""};
				int j=0;
				for (int i=0;i<recievedMessage.length();i++) {
					if (recievedMessage.charAt(i) != ' ') {
						msg[j] += recievedMessage.charAt(i);
					}
					else {
						j++;
					}
				}
				this.boardSize = Integer.parseInt(msg[0]);
				if (msg[1].equals("f")) this.ifBot = 0;
				else this.ifBot = 1;
			}
		
		this.engine = new AppEngine(this.boardSize);
		this.interpreter = new ServerInterpreter(engine);
	}
	
	public void closeConnection() {
		try {
			socket.close();
			toClient.close();
		    fromClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.plays = false;
	}
	
	public boolean checkIfPresent() {
		return this.plays;
	}

	public void processYourMove() {
		System.out.println("proc kolor "+color);
		String recievedMessage = getMessage();
		System.out.println("dostal wiadomosc "+recievedMessage);
		String response = interpreter.handleMessage(recievedMessage);
		toClient.println(response);
		System.out.println("wyslal odpowiedz "+response);
		opponent.interpreter.handleMessage(recievedMessage);
		System.out.println("interpreter oponenta dostal "+recievedMessage);
		opponent.toClient.println(response);
		System.out.println("wyslal do oponenta "+response);
		turn1 = turn2;
		turn2 = engine.getTurnCounter();
	}
	
	public int checkIfBot() {
		return this.ifBot;
	}
	
	public int getBoardSize() {
		return this.boardSize;
	}
	
	public boolean checkIfMoveWasLegit() {
		boolean outcome =false;
		if (turn1 != turn2) {
			outcome = true;
		}
		return outcome;
	}
	
	public void setTurn2(int turn2) {
		this.turn2 = turn2;
	}
	
	public void setTurn1(int turn1) {
		this.turn1 = turn1;
	}
	
}

