package tp.project.go_game.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import tp.project.go_game.logic.AppEngine;

/*
 * funkcja obslugujaca klienta
 */
public class ClientHandler implements ClientHandlerInterface{
	
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
    /*
     * flaga przechowujaca informacje czy klient jest obecny
     */
	private boolean plays;
	/*
	 * rozmiar planszy
	 */
	private int boardSize =-1;
	/*
	 * flaga przechowujaca informacje o tym, czy trzeba utworzyc bota
	 */
	private int ifBot = -1;
	
	/*
	 * konstruktor klienta, gdy rozmiar planszy nie jest ustalony
	 */
	public ClientHandler(Socket socket) {
		this.socket = socket;
		this.plays = true;
        initializePlayer();
	}
	
	/*
	 * konstruktor klienta, gdy rozmiar planszy jest ustalony
	 */
	public ClientHandler(Socket socket, int boardSize) {
		this.socket = socket;
		this.plays = true;
		this.boardSize = boardSize;
        initializePlayer();
	}
	
	/*
	 * funkcja tworzaca strumienie danych od i do klienta
	 */
	private void initializePlayer() {
        try {
        	fromClient = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			toClient = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		getParams();
	}
	
	/*
	 * funkcja pobierajaca parametry gry od klienta
	 */
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
	
	/*
	 * funkcja zamykajaca gniazdko klienta i jego strumienie danych
	 */
	@Override
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
	
	/*
	 * funkcja sprawdzajaca czy klient jest obecny
	 */
	@Override
	public boolean checkIfPresent() {
		return this.plays;
	}
	
	/*
	 * funkcja pobierajaca komunikat od klienta
	 */
	@Override
	public String getMove(){
		String msg = "";
		try {
			msg = fromClient.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	/*
	 * funkcja wysylajaca komunikat do klienta
	 */
	@Override
	public void sendMessage(String msg) {
		try {
			toClient.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * funckja sprawdzajaca czy rozgrywka odbedzie sie z botem
	 */
	@Override
	public int checkIfBot() {
		return this.ifBot;
	}
	
	/*
	 * funkcja zwracajaca rozmiar planszy
	 */
	@Override
	public int getBoardSize() {
		return this.boardSize;
	}	
}

