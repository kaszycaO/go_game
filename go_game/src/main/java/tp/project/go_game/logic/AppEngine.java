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
	
	ArrayList<Integer> chain;
	

	public AppEngine(int boardSize) {
		
		this.boardSize = boardSize;
		message = "";
		this.currentBoard = new Stone[boardSize][boardSize];
		this.koBoard = new Stone[boardSize][boardSize];
		this.previousBoard = new Stone[boardSize][boardSize];
		blackTurn = true;
		passCounter = 0;
		turnCounter = 0;
		factory = new ConcreteStoneFactory();
		chain = new ArrayList<>();
		
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
					addStone(squareX, squareY);
				if (checkIfKo(squareX, squareY)) {
					message = "Naruszona zasada Ko";
					removeStone(squareX, squareY);
				}
				else {
					if (checkIfStrangles(squareX, squareY)) {

						removeStrangledStones();
						changeTurn();
						message = "";
					} 
					
					else {
					
						if (checkIfSuicidal(squareX, squareY)) {
							message = "Ruch samobojczy";
							removeStone(squareX, squareY);
						} 
						else {

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

		boolean outcome = true;
		for (int i=0;i<boardSize;i++) {
			for (int j=0;j<boardSize;j++) {
				
				
				
				if (currentBoard[i][j] != koBoard[i][j]) { 
					outcome = false; 
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

	
	
	public boolean checkIfStrangled2(int X, int Y) {
		
		boolean breaths;
		getChain(X,Y);
		
		for(int i = 0; i < chain.size()/2; i++ ) {
			
			breaths = checkIfGotBreaths(chain.get(2*i),chain.get(2*i+1));
			
			if(breaths)
				return false;
			
			
		}
		
		
		return true;
	}
	
	
	public ArrayList<Integer> getChain(int X, int Y){

		Color color = currentBoard[X][Y].getColor();
		ArrayList<Integer> coords = getCoordsToCheck(X,Y);
		
	    if(!currentBoard[X][Y].ifChecked) {
	    	
	    	chain.add(X);
			chain.add(Y);
	    	
	    }
	
		currentBoard[X][Y].ifChain = true;
		
		for(int i = 0; i < coords.size()/2; i++) {
			int newX = coords.get(2*i);
			int newY = coords.get(2*i+1);
			if(currentBoard[newX][newY] != null && currentBoard[newX][newY].getColor() == color && !(currentBoard[newX][newY].ifChecked)) {
				
				chain.add(newX);
				chain.add(newY);
				currentBoard[newX][newY].ifChecked = true;
				
			}
			
		}
		
		int checkNextX=-1;
		int checkNextY=-1;
		
		for(int i = 0; i < chain.size()/2; i++) {
		
				if(!(currentBoard[chain.get(2*i)][chain.get(2*i+1)].ifChain)) {
					
					checkNextX = chain.get(2*i);
					checkNextY = chain.get(2*i+1);
					
					break;
						
				}
				
			}
			
		
		if(checkNextX !=-1 && checkNextY != -1) 
			return getChain(checkNextX,checkNextY);
			
			

		
		System.out.println("Doszedłem tu x");
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				
				if(currentBoard[i][j] != null) {
					
					currentBoard[i][j].setChecked(false);
					currentBoard[i][j].ifChain = false;
				}
				
				
			}
		}
		
		for(int j = 0; j < chain.size()/2; j++) {
			

			System.out.println(chain.get(2*j)+ " " +chain.get(2*j+1));
			
			
		}
		
		
		
		return chain;
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


	private void handleButtons() {
		if (convertedMessage[1].equals("pass")) {
			passCounter += 1;
			message = "Poddales swoj ruch";
		
			
			if (passCounter == 2) {
				message = "Koniec gry";

			}

		} else if (convertedMessage[1].equals("resign")) {
			message = "Koniec gry";

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
	
	public Stone[][] getCurrentBoard(){
		
		return currentBoard;
		
	}

	
}
