package tp.project.go_game.mainpackage;

import javax.swing.JOptionPane;

import tp.project.go_game.gui.MainFrame;
import tp.project.go_game.server.AppServer;
import tp.projekt.go_game.client.Client;

public class Game {
	private int boardSize = -1;
	private String[] sizes = {"9x9","13x13","19x19"};
	private AppServer server;
	private boolean ifBot;
	private Client host;
	
	public Game() {
		initializeGame();
	}
	
	private void initializeGame() {
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
    	server = new AppServer(boardSize, ifBot);
    	host = new Client(boardSize,true);
    	server.listenSocket();	 
	}
	
}
