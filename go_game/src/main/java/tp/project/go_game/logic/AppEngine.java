package tp.project.go_game.logic;

import java.util.Arrays;

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
		this.koBoard = new Stone[boardSize][boardSize];
		this.previousBoard = new Stone[boardSize][boardSize];
		blackTurn = true;
		passCounter = 0;
		turnCounter = 0;
		
	}
	
	public void switchArrays(Stone[][] array1, Stone[][] array2) {
		
		for(int i = 0 ; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				
				
				array1[i][j] = array2[i][j];
						
				
			}
		
			
		}
		
		
	}
	
	public Stone[][] doMove(String recievedMessage) {
		
		if (turnCounter > 1) {

			switchArrays(koBoard,previousBoard);
		}
		
		if (turnCounter > 0) {

			switchArrays(previousBoard,currentBoard);
		}
		
		
		
		convertedMessage = interpretMessage(recievedMessage);
		System.out.println("Gra : " + turnCounter);
		if (convertedMessage[0].equals("button")) {
			handleButtons();
		}
		else {
			handleMove();
		}
		
		
		return currentBoard;
	}
	
	
	
	private void changeTurn() {
		if (blackTurn) {
			blackTurn = false;
		}
		else {
			blackTurn = true;
		}
		turnCounter++;
	}
	
	
	private void handleMove() {
		passCounter = 0;
		squareX = Integer.parseInt(convertedMessage[1]);
		squareY = Integer.parseInt(convertedMessage[2]);
		
		coordinatesConverter(squareX, squareY);
		
		if((squareX >=0 && squareY >=0) && (squareX < boardSize && squareY < boardSize)) {
			
			
		
			if (checkIfTaken()) {
				message = "Pole zajete";
			}
			else {
			
				if (checkIfKo()) {
					message = "Naruszona zasada Ko";
				}
				else {
					
					if (checkIfStrangles()) {
						addStone();
						removeStrangledStones();
						changeTurn();
						message = "";
					
					} 
					else {
					
						if (checkIfSuicidal()) {
							message = "Ruch samobojczy";
						} 
						else {
							addStone();
							changeTurn();
							message = "";
						}
					}
				}
			}
		}
		else
			return;
		
	}


	private boolean checkIfKo() {
		if (turnCounter < 2) {
			return false;
		}
		addStone();
		boolean outcome = true;
		for (int i=0;i<boardSize;i++) {
			for (int j=0;j<boardSize;j++) {
				
				if(currentBoard == koBoard)
				System.out.println(currentBoard[i][j] + "  "+ koBoard[i][j]);
				
				
				if (currentBoard[i][j] != koBoard[i][j]) { 
					outcome = false; 
				}
			}
		}
		currentBoard[squareX][squareY] = null;
		System.out.println("\n");
		return outcome;
	}


	private boolean checkIfSuicidal() {
		// TODO Auto-generated method stub
		return false;
	}


	private void addStone() {
		
		StoneFactory factory = new ConcreteStoneFactory();
		
		if(blackTurn) {
			
	    	Stone blackStone = factory.getStone("Black");
			currentBoard[squareX][squareY] = blackStone; 
				
		} else {
			
			Stone whiteStone = factory.getStone("White");
			currentBoard[squareX][squareY] = whiteStone;
			
		}
		
		
		
		
	}


	private void removeStrangledStones() {
		// TODO Auto-generated method stub
		
	}


	private boolean checkIfStrangles() {
		// TODO Auto-generated method stub
		return false;
	}


	private boolean checkIfTaken() {
		if (currentBoard[squareX][squareY]==null) {
			return false;
		}
		else return true;
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
		
		// -1 -> przesuniecie kwadratu bedacego poza plansza aby uzyskac numeracje tablicy od (0,0)
		int squareX = newX/squareSize - 1;
		int squareY = newY/squareSize - 1;
		
		
					
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
