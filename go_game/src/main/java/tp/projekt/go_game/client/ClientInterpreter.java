package tp.projekt.go_game.client;

import tp.project.go_game.gui.MainFrame;

public class ClientInterpreter implements ClientInterpreterInterface {
	
	public MainFrame frame;
	private int squareX;
	private int squareY;
	public ClientInterpreter(int boardSize) {
		this.frame = new MainFrame(boardSize);
	}
	
	@Override
	public String sendMessage() {
		if(frame.isMousePressed()) {
			coordinatesConverter(frame.getXclicked(), frame.getYclicked()); 
			return "coordinates" + " " + squareX + " " + squareY;		
		}
		else return frame.getButtonClicked();
	}

	@Override
	public void handleMessage(String message) {
		
		String[] convertedMessage;

		//TODO przerobic na switch case'a
		if(message.charAt(0) == '0') {
			convertedMessage = interpretMessage(message);
			
			
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
			
			frame.setPanelMessage("Pole zajete!");
			
		}
		else if(message.charAt(0) == '5') {
			
			convertedMessage = interpretMessage(message);
			frame.setPanelMessage("Wygral: " + 	convertedMessage[1]);
			
		}
	}
	
	private String[] interpretMessage(String message){
		// TODO rozmiar tablicy to liczba zmian liczona w engine
		//TODO przerobic na liste
		int changes = 0;
		StringBuilder sb = new StringBuilder();
		
		char thirdLast = message.charAt(message.length() - 3);
		char secondLast = message.charAt(message.length() - 2);
		char firstLast = message.charAt(message.length() - 1);
		
		
		
		
		//gdyby byla 3 cyfrowa liczba zmian xd
		
		
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
	
	
}
