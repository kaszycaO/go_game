package tp.project.go_game.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tp.project.go_game.logic.AppEngine;
import tp.project.go_game.mainpackage.Game;

public class ClientHandler {
	
	/**
     * gniazdko klienta
     */
    private Socket socket = null;
	/**
     * komunikaty od klienta
     */
    private DataInputStream fromClient = null;
    /**
     * dane wysylane do klienta
     */
    private DataOutputStream toClient = null;
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
        	fromClient = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			toClient = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		getParams();
	}
	
	private void getParams() {
		try {
			String recievedMessage = fromClient.readUTF();
			if (recievedMessage.equals("params")) {
				if(this.boardSize == -1) {
					toClient.writeUTF("-");
				}
				else {
					toClient.writeUTF(Integer.toString(boardSize));
				}
			}
			recievedMessage = fromClient.readUTF();
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
		} catch (IOException e) {
			e.printStackTrace();
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

	public void run() {
		try {
	  		 String recievedMessage = fromClient.readUTF();
	  		 toClient.writeUTF(interpreter.handleMessage(recievedMessage));
	  		 opponent.toClient.writeUTF(interpreter.handleMessage(recievedMessage));
	    }
	   catch (IOException e) {
	        System.out.println(e.getMessage());
	        System.exit(1);
	    }
		turn1 =turn2;
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
		if (turn1 != turn2) outcome = true;
		return outcome;
	}
}

