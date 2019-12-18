package tp.project.go_game.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tp.project.go_game.logic.AppEngine;

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
	private boolean plays;
	private int boardSize =-1;
	private int ifBot = -1;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
		this.plays = true;
        initializePlayer();
	}
	
	public ClientHandler(Socket socket, int boardSize) {
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
	
	public String getMove(){
		String msg = "";
		try {
			msg = fromClient.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public void sendMessage(String msg) {
		try {
			toClient.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int checkIfBot() {
		return this.ifBot;
	}
	
	public int getBoardSize() {
		return this.boardSize;
	}	
}

