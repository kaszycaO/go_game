package tp.project.go_game.logic;

import javax.swing.JOptionPane;

public class AppEngine {

	
	private int squareX;
	private int squareY;
	private int boardSize;
	private String message;
	private String[] convertedMessage;
	private Stone[][] currentBoard;
	private Stone[][] previousBoard;
	private Stone[][] koBoard;
	private boolean blackTurn;
	private int passCounter;
	private int turnCounter;
	

	public AppEngine(int boardSize) {
		
		this.boardSize = boardSize;
		message = "";
		this.currentBoard = new Stone[boardSize][boardSize];
		this.koBoard = currentBoard;
		this.previousBoard = currentBoard;
		blackTurn = true;
		passCounter = 0;
		turnCounter = 0;
		
	}
	
	
	public void doMove(String recievedMessage) {
		
		if (turnCounter>1) {
			koBoard = previousBoard;
		}
		if(turnCounter>0) {
			previousBoard = currentBoard;
		}
		convertedMessage = interpretMessage(recievedMessage);
		if (convertedMessage[0].equals("button")) {
			handleButtons();
		}
		else {
			handleMove();
		}
		/**if (blackTurn) {
			blackTurn = false;
		}
		else {
			blackTurn = true;
		}
		turnCounter++; trzeba dac w inne miejsce*/
	}
	
	
	
	
	
	
	private void handleMove() {
		passCounter = 0;
		int xClick, yClick;
		xClick = Integer.parseInt(convertedMessage[1]);
		yClick = Integer.parseInt(convertedMessage[2]);
		
		
	}


	private void handleButtons() {
		if (convertedMessage[1].equals("pass")) {
			passCounter += 1;
			message = "Poddales swoj ruch";
			if (passCounter == 2) {
				message = "Koniec gry";
				//getScore();
			}
		} else if (convertedMessage[1].equals("resign")) {
			message = "Koniec gry";
			//getScore();
		}
		
	}
	
	private void clearBoard() {
		for (int i=0;i<boardSize;i++) {
			for (int j=0;j<boardSize;j++) {
				currentBoard[i][j] = null;
			}
		}
		koBoard = currentBoard;
		previousBoard = currentBoard;
		blackTurn = true;
		passCounter = 0;
		turnCounter = 0;
	}


	private void getScore() {
		//TODO ogarnac
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
			while(j < message.length() && (message.charAt(j) != ' ')) {
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


	public String[] getConvertedMessage() {
		return convertedMessage;
	}

	
}
