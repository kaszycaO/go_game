package tp.project.go_game.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tp.project.go_game.mainpackage.Game;

public class ClientHandler implements Runnable {
	
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
	public ClientHandler opponent;
	private String color;
	private Game game;
	
	public ClientHandler(Socket socket, int boardSize, String color, Game game) throws Exception {
		this.color = color;
		this.socket = socket;
		this.game = game;
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
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

