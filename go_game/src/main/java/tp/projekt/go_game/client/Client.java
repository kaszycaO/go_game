package tp.projekt.go_game.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Client extends Observer {
	/*
	 * interpreter klienta
	 */
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
    /*
     * rozmiar planszy
     */
    private int boardSize;
    /*
     * czy gra bedzie z botem
     */
    private boolean ifBot;
    /*
     * tablica opcji rozmiaru planszy
     */
    private String[] sizes = {"9x9","13x13","19x19"};
    /*
     * flaga przetrzymujaca informacje o turze
     */
    private boolean yourTurn = true;

	/*
	 * glowny konstruktor
	 */
	public Client() {
		this.boardSize = -1;
		initializeClient();
		getGameParameters();
	}
	
	/*
	 * funkcja inicjujaca gniazdko klienta i strumienie danych
	 */
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

	/*
	 * funkcja pobierajaca parametry gry od klienta lub wysylajaca mu je, gdy sa juz ustalone
	 */
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
			else
				setTurn(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * funkcja pyta uzytkownika o parametry gry
	 */
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
	
	/*
	 * funckja zamykajaca gniazdko klienta i strumienie danych
	 */
	private void closeConnection() {
		interpreter.frame.dispose();
		try {
			toServer.writeUTF("exit");
			socket.close();
	        toServer.close();
	        fromServer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
            System.exit(1);
		}
	}
	
	/*
	 * funkcja wysylajaca komunikaty na serwer
	 */
	private void exchangeWithServer() {
		
		String msg = interpreter.sendMessage();
		System.out.println(msg);
		if(msg != "") {
		try {
				toServer.writeUTF(msg);
				if (msg.equals("button exit")) {
					closeConnection();	
				}
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
					setTurn(false);
					handleOpponentsMove();
				
				}});
				
			}
			else return;
		}
		else return;
	}
	
	/**
	 * funkcja czekajaca na ruch przeciwnika i przetwarzajaca go
	 */
	private void handleOpponentsMove() {
		
		try {
			
			yourTurn = false;
			String line = fromServer.readUTF();
			interpreter.handleMessage(line);
			yourTurn = true;
			setTurn(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * funkcja manipulucjaca flaga z tura
	 */
	public void setTurn(final boolean statement) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				interpreter.frame.myAdapter.setYourTurn(statement);
			
			}});
		
	}
	
	/*
	 * funcja wywolywana po wcisnieciu czegokolwiek na planszy
	 */
	@Override
	public void update() {
		if (yourTurn) {
			exchangeWithServer();
		}
	}
}
