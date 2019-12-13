package tp.projekt.go_game.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tp.project.go_game.mainpackage.Game;

public class Player extends Observer implements Runnable {
	
	private ClientInterpreter interpreter;
	/**
     * gniazdko klienta
     */
    private Socket socket = null;
    /**
     * komunikaty do serwera
     */
    private DataOutputStream  toServer = null;
    /**
     * komunikaty od serwera
     */
    private DataInputStream fromServer = null;
    
    private boolean blackTurn = true;
    private boolean blackPlayer;
	public Player opponent;
	private String color;
	private Game game;
	
	public Player(Socket socket, int boardSize, String color, Game game) throws Exception {
		interpreter = new ClientInterpreter(boardSize);
		//this.blackPlayer = blackPlayer;
		this.color = color;
		interpreter.frame.myAdapter.attach(this);
		this.socket = socket;
		this.game = game;
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	
	private void exchangeWithServer() {
		try {
			  
	           toServer.writeUTF(interpreter.sendMessage());
	           String line = fromServer.readUTF();
	           char checkTurn = line.charAt(0);
	           if(checkTurn == '1') {
	        	   blackTurn = !blackTurn;
	           }
	           interpreter.handleMessage(line);
	           socket.close();
	           toServer.close();
	           fromServer.close();
	        }
	        catch (IOException e) {
	            System.out.println(e.getMessage());
	            System.exit(1);
	        }
	}

	@Override
	public void update() {
		
		if(blackTurn == blackPlayer) {
			
			exchangeWithServer();
			
		}
			
		
	}

	@Override
	public void run() {
		try {
	           if(color == "black") {
	        	   game.currentPlayer = this;
	        	   toServer.writeUTF("waitin for opponent");
	           }
	           else {
	        	   opponent = game.currentPlayer;
	        	   opponent.opponent = this;
	        	   opponent.toServer.writeUTF("moje zmiany");
	           }
	           socket.close();
	           toServer.close();
	           fromServer.close();
	        }
	        catch (IOException e) {
	            System.out.println(e.getMessage());
	            System.exit(1);
	        }		
	}
}
