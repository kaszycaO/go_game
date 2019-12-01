package tp.project.go_game.logic;

public class AppEngine {

	
	private int squareX;
	private int squareY;
	private int boardSize;
	

	public AppEngine(int boardSize) {
		
		this.boardSize = boardSize;
		
	}
	
	
	public void doMove(int X, int Y) {
		
		coordinatesConverter(X,Y);
		
		
		
		
		
		
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

	
	
	public int getSquareY() {
		return squareY;
	}

	public int getSquareX() {
		return squareX;
	}

	
}
