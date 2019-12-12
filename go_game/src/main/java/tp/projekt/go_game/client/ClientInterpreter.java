package tp.projekt.go_game.client;

import tp.project.go_game.gui.MainFrame;

public class ClientInterpreter implements ClientInterpreterInterface {
	
	private MainFrame frame;
	
	public ClientInterpreter(int boardSize) {
		this.frame = new MainFrame(boardSize);
	}
	
	@Override
	public String sendMessage() {
		// TODO wygenerowanie wiadomosci do przeslania na serwer
		return null;
	}

	@Override
	public String handleMessage(String message) {
		// TODO wygenerwanie odpowiedzi na wiadomosc od serwera
		return null;
	}
}
