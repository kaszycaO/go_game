package tp.projekt.go_game.client;

import tp.project.go_game.gui.MainFrame;

public class ClientInterpreter implements ClientInterpreterInterface {
	
	protected MainFrame frame;
	
	public ClientInterpreter(int boardSize) {
		this.frame = new MainFrame(boardSize);
	}
	
	@Override
	public String sendMessage() {
		
		
		if(frame.isMousePressed()) {
			
			return "coordinates" + " " + frame.getXclicked() + " " + frame.getYclicked();		
		}
		
		else
			
			return frame.getButtonClicked();
	}

	@Override
	public String handleMessage(String message) {
		// TODO wygenerwanie odpowiedzi na wiadomosc od serwera
		return null;
	}
}
