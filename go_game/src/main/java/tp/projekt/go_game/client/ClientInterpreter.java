package tp.projekt.go_game.client;


import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tp.project.go_game.gui.MainFrame;

public class ClientInterpreter implements ClientInterpreterInterface {
	
	/*
	 * gui klienta
	 */
	public MainFrame frame;
	/*
	 * x-owa wspolrzedna klikniecia
	 */
	private int squareX;
	/*
	 * y-owa wspolrzedna klikniecia
	 */
	private int squareY;
	/*
	 * flaga przechowujaca informacje o turze
	 */
	private boolean doMove = true;
	/*
	 * counter liczacy wykonane ruchy
	 */
	public int counter = 0;
	
	/*
	 * glowny konstruktor
	 */
	public ClientInterpreter(int boardSize) {
		this.frame = new MainFrame(boardSize);
	}
	
	/*
	 * funkcja generujaca komunikat do serwera w oparciu o klikniecia na planszy
	 */
	@Override
	public String sendMessage() {
		
		System.out.println(frame.myAdapter.isYourTurn());
		if(frame.myAdapter.isYourTurn()) {
			if(frame.isMousePressed()) {
				coordinatesConverter(frame.getXclicked(), frame.getYclicked()); 
			return "coordinates" + " " + squareX + " " + squareY;		
		}
		else return frame.getButtonClicked();
	}
		return "";
	}
	
	/*
	 * funkcja przetwarzajaca komunikat od serwera
	 */
	@Override
	public void handleMessage(String message) {
		String[] convertedMessage;
		doMove = true;
		if(message.charAt(0) == '0') {
			convertedMessage = interpretMessage(message);
			doMove = false;
			if(convertedMessage[0].equals("0")) {
				for(int i = 1; i <= (convertedMessage.length)/3; i++ ) {
					int X = Integer.parseInt(convertedMessage[3*i - 2]);
					int Y = Integer.parseInt(convertedMessage[3*i - 1]); 
					frame.myBoard.addStoneToBoard(X,Y,convertedMessage[3 * i]);
					frame.setPanelMessage("");
				}
			}
		}
		else if(message.charAt(0) == '1') {
			
			frame.setPanelMessage("Naruszona zasada KO");
		}
		else if(message.charAt(0) == '2') {
			
			frame.setPanelMessage("Uzywaj planszy!");
		}
		else if(message.charAt(0) == '3') {
			
			frame.setPanelMessage("Ruch samobojczy!");	
		}
		else if(message.charAt(0) == '4') {
			
			frame.setPanelMessage("Pole zajete!");}
		else if(message.charAt(0) == '5') {
			
			convertedMessage = interpretMessage(message);
			System.out.println(convertedMessage[2]);
			
			if(convertedMessage[1].equals("remis"))
				frame.setPanelMessage(convertedMessage[1]);
			else if(convertedMessage[2].equals("Przeciwnik"))
				frame.setPanelMessage(convertedMessage[2] + " " + convertedMessage[3]);
			else
			{
				String score = "Wygral: " + 	convertedMessage[1] + " o: " + convertedMessage[2] + " pkt";
				frame.setPanelMessage("Wygral: " + 	convertedMessage[1] + " o: " + convertedMessage[2] + " pkt");
				JOptionPane.showMessageDialog(null, score, "Koniec gry!", JOptionPane.INFORMATION_MESSAGE);
				}
			
		}
		else if(message.charAt(0) == '6') {
			frame.setPanelMessage("Twoj przeciwnik uciekl :c");
		}
	}
	
	/*
	 * funcja konwertujaca komunikat z serwera na tablice stringow
	 */
	public String[] interpretMessage(String message){
		
		int changes = 0;
		StringBuilder sb = new StringBuilder();
		
		char thirdLast = message.charAt(message.length() - 3);
		char secondLast = message.charAt(message.length() - 2);
		char firstLast = message.charAt(message.length() - 1);
		if(thirdLast != ' ' && secondLast != ' ') {
			sb.append(thirdLast);
			sb.append(secondLast);
			sb.append(firstLast);
			String helper = sb.toString();

			changes = Integer.parseInt(helper);
		}
		
		
		else if(secondLast != ' ') {

			sb.append(secondLast);
			sb.append(firstLast);
			String helper = sb.toString();
			changes = Integer.parseInt(helper);
		}
		else {
			String helper = firstLast +"";
			changes = Integer.parseInt(helper);
			
			}
		
		
		String[] convertedMessage = new String[changes];
		int j = 0;
		for (int i=0;i<changes;i++) {
			convertedMessage[i] = "";
			while(j < message.length() && (message.charAt(j) != ' ')) {
				convertedMessage[i] += message.charAt(j);
				j++;
			}
			
			j++;
		}
		
		
		return convertedMessage;
	}
	
	/*
	 * funkcja konwertujaca wspolrzedne klikniecia na wspolrzedne planszowe
	 */
	private void coordinatesConverter(int X, int Y) {

		int squareSize = 840/(frame.getBoardSize() + 1) ; 
		int newX = X + squareSize/2;
		int newY = Y + squareSize/2;
		
		// -1 -> przesuniecie kwadratu bedacego poza plansza aby uzyskac numeracje tablicy od (0,0)
		int squareX = newX/squareSize - 1;
		int squareY = newY/squareSize - 1;
				
		this.squareX = squareX;
		this.squareY = squareY;
	}

	public boolean isDoMove() {
		return doMove;
	}
	
	
}
