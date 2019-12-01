package tp.project.go_game.logic;

public class AppEngine {

	
	private int squareX;
	private int squareY;
	private int boardSize;
	private String message;
	private String[] convertedMessage;
	private Stone[][] currentBoard;
	private Stone[][] koBoard;
	

	public AppEngine(int boardSize) {
		
		this.boardSize = boardSize;
		message = "";
		
	}
	
	
	public void doMove(String recievedMessage) {
		
		convertedMessage = interpretMessage(recievedMessage);
		
		
		
		
		
		
	}
	
	
	
	
	
	
	private void coordinatesConverter(int X, int Y) {
		
		int squareSize = 840/(boardSize + 1) ; 
		int newX = X + squareSize/2;
		int newY = Y + squareSize/2;
		
		int squareX = newX/squareSize;
		int squareY = newY/squareSize;
		
		System.out.println(squareX);
		System.out.println(squareY); 
			
		this.squareX = squareX;
		this.squareY = squareY;
	}
	
	private String[] interpretMessage(String message){
		String[] convertedMessage = {"","",""};
		int j = 0;
		for (int i=0;i<3;i++) {
			while(message.charAt(j) != ' ') {
				convertedMessage[i] += message.charAt(j);
				j++;
			}
			j++;
		}
		return convertedMessage;
	}

	
	
	public int getSquareY() {
		return squareY;
	}

	public int getSquareX() {
		return squareX;
	}
	
	public String getMessage() {
		return this.message;
	}

	
}
