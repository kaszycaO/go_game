package tp.project.go_game.logic;

import java.awt.Color;
import java.util.ArrayList;

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
	private StoneFactory factory;
	

	public AppEngine(int boardSize) {
		
		this.boardSize = boardSize;
		message = "";
		this.currentBoard = new Stone[boardSize][boardSize];
		this.koBoard = currentBoard;
		this.previousBoard = currentBoard;
		blackTurn = true;
		passCounter = 0;
		turnCounter = 0;
		factory = new ConcreteStoneFactory();
		
	}
	
	
	public Stone[][] doMove(String recievedMessage) {
		
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
		return currentBoard;
	}
	
	
	
	public void changeTurn() {
		if (blackTurn) {
			blackTurn = false;
		}
		else {
			blackTurn = true;
		}
		turnCounter++;
	}
	
	
	public void handleMove() {
		passCounter = 0;
		squareX = Integer.parseInt(convertedMessage[1]);
		squareY = Integer.parseInt(convertedMessage[2]);
		
		coordinatesConverter(squareX, squareY);
		
		if((squareX >=0 && squareY >=0) && (squareX < boardSize && squareY < boardSize)) {
			
			
		
			if (checkIfTaken(squareX, squareY)) {
				message = "Pole zajete";
			}
			else {
			
				if (checkIfKo(squareX, squareY)) {
					message = "Naruszona zasada Ko";
				}
				else {
					
					if (checkIfStrangles(squareX, squareY)) {
						addStone(squareX, squareY);
						removeStrangledStones();
						changeTurn();
						message = "";
					
					} 
					else {
					
						if (checkIfSuicidal(squareX, squareY)) {
							message = "Ruch samobojczy";
						} 
						else {
							addStone(squareX, squareY);
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


	public boolean checkIfKo(int X, int Y) {
		if (turnCounter < 2) {
			return false;
		}
		addStone(X,Y);
		boolean outcome = true;
		for (int i=0;i<boardSize;i++) {
			for (int j=0;j<boardSize;j++) {
				if (currentBoard[i][j] != koBoard[i][j]) { 
					outcome = false; 
				}
			}
		}
		removeStone(X,Y);
		return outcome;
	}


	public boolean checkIfSuicidal(int X, int Y) {
		addStone(X,Y);
		boolean outcome;
		if (checkIfStrangled(X,Y)) {
			outcome = true;
		}
		else {
			outcome = false;
		}
		removeStone(X,Y);
		return outcome;
	}


	public boolean checkIfStrangled(int X, int Y) {
		boolean outcome = true;
		if (!checkIfGotBreaths(X,Y)) {
			ArrayList<Integer[]> coords = getCoordsToCheck(X,Y);
			for (Integer[] co : coords) {
				if (blackTurn) {
					if (currentBoard[co[0]][co[1]].getColor()==Color.black && !currentBoard[co[0]][co[1]].ifChecked) {
						currentBoard[X][Y].ifChecked = true;
						return checkIfStrangled(co[0],co[1]);
					}
				}
				else if(!blackTurn) {
					if (currentBoard[co[0]][co[1]].getColor()==Color.white && !currentBoard[co[0]][co[1]].ifChecked) {
						currentBoard[X][Y].ifChecked = true;
						return checkIfStrangled(co[0],co[1]);
					}
				}
			}
		}
		else outcome = false;
		return outcome;
	}
	
	public boolean checkIfGotBreaths(int X, int Y) {
		boolean outcome = false;
		ArrayList<Integer[]> coords = getCoordsToCheck(X,Y);
		for (Integer[] co : coords) {
			if (currentBoard[co[0]][co[1]] == null) {
				outcome = true;
			}
		}
		return outcome;
	}
	
	public ArrayList<Integer[]> getCoordsToCheck(int X, int Y){
		Integer[] single = new Integer[2];
		ArrayList<Integer[]> coords = new ArrayList<Integer[]>();
		if (X-1>=0) {
			single[0] = X-1;
			single[1] = Y;
			coords.add(single);
		}
		if (X+1 < boardSize) {
			single[0] = X+1;
			single[1] = Y;
			coords.add(single);
		}
		if (Y-1>= 0) {
			single[0] = X;
			single[1] = Y-1;
			coords.add(single);
		}
		if (Y+1 < boardSize) {
			single[0] = X;
			single[1] = Y+1;
			coords.add(single);
		}
		return coords;
	}


	public void addStone(int X, int Y) {
		
		if(blackTurn) {
			
	    	Stone blackStone = factory.getStone("Black");
			currentBoard[X][Y] = blackStone; 
				
		} else {
			
			Stone whiteStone = factory.getStone("White");
			currentBoard[X][Y] = whiteStone;
			
		}
	}
	
	private void removeStone(int X, int Y) {
		currentBoard[X][Y] = null;
	}


	private void removeStrangledStones() {
		// TODO Auto-generated method stub
		
	}


	private boolean checkIfStrangles(int X, int Y) {
		addStone(X,Y);
		Color color = currentBoard[X][Y].getColor();
		ArrayList<Integer[]> coords = getCoordsToCheck(squareX,squareY);
		for (Integer[] co : coords) {
			if (currentBoard[co[0]][co[1]].getColor() != color) {
				if (checkIfStrangled(co[0],co[1])) {
					currentBoard[squareX][squareY] = null;
					return true;
				}
			}
		}
		removeStone(X,Y);
		return false;
	}


	private boolean checkIfTaken(int X, int Y) {
		if (currentBoard[X][Y]==null) {
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
		
		System.out.println(X + "  " + Y);

		int squareSize = 840/(boardSize + 1) ; 
		int newX = X + squareSize/2;
		int newY = Y + squareSize/2;
		
		// -1 -> przesuniecie kwadratu bedacego poza plansza
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
