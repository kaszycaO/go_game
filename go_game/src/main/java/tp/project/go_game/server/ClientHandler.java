package tp.project.go_game.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tp.project.go_game.logic.AppEngine;
import tp.project.go_game.mainpackage.Game;

public class ClientHandler implements Runnable {
	
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
	private Game game;
	private ServerInterpreter interpreter;
	private AppEngine engine;
	
	public ClientHandler(Socket socket, int boardSize, String color, Game game) throws Exception {
		this.color = color;
		this.socket = socket;
		this.game = game;
		this.engine = new AppEngine(boardSize);
		this.interpreter = new ServerInterpreter(engine);
		fromClient = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        toClient = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		try {
	           if(color == "black") {
	        	   game.currentPlayer = this;
	        	   toClient.writeUTF("waitin for opponent");
	           }
	           else {
	        	   opponent = game.currentPlayer;
	        	   opponent.opponent = this;
	        	   opponent.toClient.writeUTF("moje zmiany");
	           }
	           while (fromClient != null) {
	        	   String recievedMessage = fromClient.readUTF();
	        	   toClient.writeUTF(interpreter.handleMessage(recievedMessage));
	           }
	        }
	        catch (IOException e) {
	            System.out.println(e.getMessage());
	            System.exit(1);
	        }
		finally {
			if (opponent != null && opponent.toClient != null) {
				try {
					opponent.toClient.writeUTF("other player left");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			try {
				socket.close();
		        toClient.close();
		        fromClient.close();
			} catch (IOException e) {
				e.printStackTrace();
		}
	}
}

