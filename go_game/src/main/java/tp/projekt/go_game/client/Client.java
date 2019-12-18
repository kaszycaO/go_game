package tp.projekt.go_game.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Client extends Observer {
	
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
    private int boardSize;
    private boolean ifBot;
    private String[] sizes = {"9x9","13x13","19x19"};
    private boolean yourTurn = true;
	
	public Client() {
		this.boardSize = -1;
		initializeClient();
		getGameParameters();
	}
	
	private void initializeClient() {
		try {
			socket = new Socket("localhost", 4444);
	        toServer = new DataOutputStream(socket.getOutputStream());
	        fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
            System.exit(1);
		}
	}
	
	private void getGameParameters() {
		try {
			toServer.writeUTF("params");
			String line = fromServer.readUTF();
			if (line.charAt(0)=='-') {
				getParsFromClient();
				String bot = "f";
				if (ifBot) bot = "t";
				toServer.writeUTF(Integer.toString(boardSize)+" "+bot);
			}
			else {
				this.boardSize = Integer.parseInt(line);
				toServer.writeUTF("got");
			}
			this.interpreter = new ClientInterpreter(boardSize);
			interpreter.frame.myAdapter.attach(this);
			if (line.charAt(0)!='-') {
				handleOpponentsMove();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void getParsFromClient() {
    	int n = JOptionPane.showConfirmDialog(null, "Czy chcesz grac przeciwko innemu uzytkownikowi?", "Wybierz tryb rozgrywki", JOptionPane.YES_NO_OPTION);
		String size = (String)JOptionPane.showInputDialog(null, "Wybierz rozmiar planszy", "Nowa gra", JOptionPane.QUESTION_MESSAGE,null, sizes,sizes[0]);
		if (n == JOptionPane.NO_OPTION) ifBot = true;
		else ifBot = false;
		if (size == null) {	
			System.exit(1);
		}
		else if (size.equals(sizes[0])) {
			boardSize = 9;
		}
		else if(size.equals(sizes[1])) {
			boardSize = 13;
		}
		else if (size.equals(sizes[2])) {	
			boardSize = 19;
		}
	}
	
	private void closeConnection() {
		try {
			socket.close();
	        toServer.close();
	        fromServer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
            System.exit(1);
		}
	}
	
	private void exchangeWithServer() {
			try {
				toServer.writeUTF(interpreter.sendMessage());
				String line = fromServer.readUTF();
				interpreter.handleMessage(line);
			}
			catch (IOException e) {				
				System.out.println(e.getMessage());
				System.exit(1);
			}
			if(!interpreter.isDoMove()) {
				SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					handleOpponentsMove();
				
				}});
				
			}
			else return;
	}
	
	private void handleOpponentsMove() {
		try {
			yourTurn = false;
			String line = fromServer.readUTF();
			interpreter.handleMessage(line);
			yourTurn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		if (yourTurn) {
			exchangeWithServer();
		}
	}
}
