package tp.projekt.go_game.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client extends Observer {
	
	private ClientInterpreter interpreter;
	/**
     * gniazdko klienta
     */
    private Socket socket = null;
    /**
     * komunikaty do serwera
     */
    private PrintWriter toServer = null;
    /**
     * komunikaty od serwera
     */
    private Scanner fromServer = null;
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
			fromServer = new Scanner(socket.getInputStream());
			toServer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
            System.exit(1);
		}
	}
	
	private void getGameParameters() {

			toServer.println("params");
			String line = fromServer.nextLine();
			if (line.charAt(0)=='-') {
				getParsFromClient();
				String bot = "f";
				if (ifBot) bot = "t";
				toServer.println(Integer.toString(boardSize)+" "+bot);
			}
			else {
				this.boardSize = Integer.parseInt(line);
				toServer.println("got");
			}
			this.interpreter = new ClientInterpreter(boardSize);
			interpreter.frame.myAdapter.attach(this);
			if (line.charAt(0)!='-') {
				handleOpponentsMove();
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
			
				System.out.println("wysylam ruch");
				toServer.println(interpreter.sendMessage());
				String line = fromServer.nextLine();
				System.out.println("dostalem "+line);
				interpreter.handleMessage(line);
				if(!interpreter.isDoMove())
				handleOpponentsMove();
			
			
	}
	
	private void handleOpponentsMove() {
		
			yourTurn = false;
			System.out.println("czekam na ruch przeciwnika");
			String line = fromServer.nextLine();
			interpreter.handleMessage(line);
			yourTurn = true;
		
	}
	
	@Override
	public void update() {

		if(yourTurn) exchangeWithServer();
		else return;

	}
}
