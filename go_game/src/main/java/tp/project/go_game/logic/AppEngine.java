package tp.project.go_game.logic;


import java.awt.Color;
import java.util.ArrayList;

import tp.project.go_game.exceptions.*;

public class AppEngine implements EngineInterface {

	
	private int squareX;
	private int squareY;
	private int boardSize;
	private Stone[][] currentBoard;
	private Stone[][] previousBoard;
	private Stone[][] koBoard;
	private boolean blackTurn;
	private int passCounter;
	private int turnCounter;
	private StoneFactory factory;
	private String changes;
	

	public AppEngine(int boardSize) {
		
		this.boardSize = boardSize;
		this.currentBoard = new Stone[boardSize][boardSize];
		this.koBoard = new Stone[boardSize][boardSize];
		this.previousBoard = new Stone[boardSize][boardSize];
		blackTurn = true;
		passCounter = 0;
		turnCounter = 0;
		factory = new ConcreteStoneFactory();
		changes = "";
		
	}
	
	public void handleMove(int X, int Y) throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException, IntersectionTakenException {
		if (turnCounter > 1) {
			switchArrays(koBoard,previousBoard);
		}
		if (turnCounter > 0) {
			switchArrays(previousBoard,currentBoard);
		}
		coordinatesConverter(X, Y);
		
		if(checkIfInBounds(X, Y)) {
			if (checkIfTaken(X, Y)) {
				throw new IntersectionTakenException();
			}
			else {
					addStone(X, Y);
				if (checkIfKo(X, Y)) {
					removeStone(X, Y);
					throw new KoRuleViolatedException();
				}
				else {
					if (checkIfStrangles(X, Y)) {
						removeStrangledStones();
						changeTurn();
						passCounter = 0;
					} 
					
					else {
					
						if (checkIfSuicidal(X, Y)) {
							removeStone(X, Y);
							throw new SuicidalMoveException();
						} 
						else {
							changeTurn();
							passCounter = 0;
						}
					}
				}
			}
		}
		else
			throw new CoordinatesOutOfBoundsException();
		
	}
	
	public boolean checkIfInBounds(int X, int Y) {
		boolean outcome = false;
		if((X >=0 && Y >=0) && (X < boardSize && Y < boardSize)) outcome = true;
		return outcome;
	}


	public boolean checkIfKo(int X, int Y) {
		if (turnCounter < 2) {
			return false;
		}

		boolean outcome = true;
		for (int i=0;i<boardSize;i++) {
			for (int j=0;j<boardSize;j++) {
				if (currentBoard[i][j] == null) {
					if(koBoard[i][j] != null) outcome = false;
				} else {
					if(koBoard[i][j] == null || koBoard[i][j].getColor()!=currentBoard[i][j].getColor()) outcome = false; 
				}
			}
		}
		return outcome;
	}


	public boolean checkIfSuicidal(int X, int Y) {

		boolean outcome;
		if (checkIfStrangledDomki(X,Y)) {

			outcome = true;
		}
		else {
			outcome = false;
		}

		return outcome;
	}

	public boolean checkIfNeighbour(int X, int Y, int A, int B) {
		boolean outcome = false;
		int xDif = Math.abs(X-A);
		int yDif = Math.abs(Y-B);
		if ((xDif ==1 && yDif ==0) || (xDif == 0 && yDif==1)) outcome = true;
		return outcome;
	}
	
	public ArrayList<Integer> getDomkaChain(int X, int Y){
		ArrayList<Integer> domkaChain = new ArrayList<>();
		Color color = currentBoard[X][Y].getColor();
		ArrayList<Integer> colorChain = new ArrayList<>();
		domkaChain.add(X);
		domkaChain.add(Y);
		currentBoard[X][Y].ifChecked = true;
		for(int i=0; i< boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				if (currentBoard[j][i] != null && currentBoard[j][i].getColor() == color) {
					colorChain.add(j);
					colorChain.add(i);
				}
			}
		}
		for (int k=0;k<2;k++) {
			for(int i=0; i< colorChain.size()/2;i++) {
				if (!currentBoard[colorChain.get(2*i)][colorChain.get(2*i+1)].ifChecked) {
				for(int j=0; j<domkaChain.size()/2;j++) {
					if (checkIfNeighbour(colorChain.get(2*i),colorChain.get(2*i+1),domkaChain.get(2*j),domkaChain.get(2*j+1))) {
						domkaChain.add(colorChain.get(2*i));
						domkaChain.add(colorChain.get(2*i+1));
						currentBoard[colorChain.get(2*i)][colorChain.get(2*i+1)].ifChecked = true;
						break;
					}
				}}
			}
		}
		for(int j=0; j<domkaChain.size()/2;j++) {
			System.out.println(j+": "+domkaChain.get(2*j)+" "+domkaChain.get(2*j+1));
		}
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				
				if(currentBoard[i][j] != null)
					currentBoard[i][j].setChecked(false);
				
			}
		}
		return domkaChain;
	}
	
	public boolean checkIfStrangledDomki(int X, int Y) {
		ArrayList<Integer> domkaChain = getDomkaChain(X,Y);
		boolean outcome = true;
		for (int i=0;i<domkaChain.size()/2;i++) {
			if (checkIfGotBreaths(domkaChain.get(2*i),domkaChain.get(2*i+1))) outcome = false;
		}
		return outcome;
	}
	
	public boolean checkIfGotBreaths(int X, int Y) {
		boolean outcome = false;
		ArrayList<Integer> coords = getCoordsToCheck(X,Y);
		

		for (int i=0;i<coords.size()/2;i++) {
	
			if (currentBoard[coords.get(i*2)][coords.get(2*i+1)] == null) {
				outcome = true;
			}
		}

		return outcome;
	}
	
	public ArrayList<Integer> getCoordsToCheck(int X, int Y){
		ArrayList<Integer> coords = new ArrayList<Integer>();
		if (X-1>=0) {
			coords.add(X-1);
			coords.add(Y);
		}
		if (X+1 < boardSize) {
			coords.add(X+1);
			coords.add(Y);
		}
		if (Y-1>= 0) {
			coords.add(X);
			coords.add(Y-1);
		}
		if (Y+1 < boardSize) {
			coords.add(X);
			coords.add(Y+1);
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
	
	private ArrayList<Integer> getCoordsToRemove(int X, int Y){
		ArrayList<Integer> coords = new ArrayList<Integer>();
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				if(!(i == X && j == Y) && currentBoard[i][j] != null) {
					if(checkIfStrangledDomki(i,j)) {
						coords.add(i);
						coords.add(j);
					}
				}
			}
		}
		return coords;
	}
	
	private void removeStone(int X, int Y) {

		
		currentBoard[X][Y] = null;

	}


	public void removeStrangledStones() {
		
		ArrayList<Integer> coords = getCoordsToRemove(squareX,squareY);
		for(int i=0;i<coords.size()/2;i++) {
		 
			removeStone(coords.get(2*i), coords.get(2*i+1));
		}
	}


	public boolean checkIfStrangles(int X, int Y) {
		
		Color color = currentBoard[X][Y].getColor();
		ArrayList<Integer> coords = getCoordsToCheck(X,Y);
		for (int i=0;i<coords.size()/2;i++) {
			int newX = coords.get(2*i);
			int newY = coords.get(2*i+1);
			if (currentBoard[newX][newY] != null && currentBoard[newX][newY].getColor() != color) {
				if (checkIfStrangledDomki(newX,newY)) {

					return true;
				}
			}
		}
		return false;
	}


	private boolean checkIfTaken(int X, int Y) {
		if (currentBoard[X][Y]==null) {
			return false;
		}
		else return true;
	}

	@Override
	public void handleButtons(String button) {
		if (button.equals("pass")) {
			passCounter += 1;
			if (passCounter == 2) {
				changes += getScore();
			}

		} else if (button.equals("resign")) {
			changes += getScore();
		}
	
	}
	
	public void switchArrays(Stone[][] array1, Stone[][] array2) {
		
		for(int i = 0 ; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				array1[i][j] = array2[i][j];
			}
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



	private String getScore() {
		return "";
		//TODO ogarnac
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
		
	public void resetChanges() {
		changes = "";
	}
	
	public String getChanges(){
		for (int i=0;i<boardSize;i++) {
			for (int j=0;j<boardSize;j++) {
				if (previousBoard[i][j] == null ) {
					if(currentBoard[i][j] != null ) {
						changes += Integer.toString(i);
						changes += " ";
						changes += Integer.toString(j);
						changes += " ";
						if (currentBoard[i][j].getColor() == Color.white) changes += "white ";
						else changes += "black";
					}
				}
				else {
					if(currentBoard[i][j] == null) {
						changes += Integer.toString(i);
						changes += " ";
						changes += Integer.toString(j);
						changes += " ";
						changes += "null ";
					}
				}
			}
		}
		return this.changes;
	}
	
}
