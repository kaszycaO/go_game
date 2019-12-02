package tp.project.go_game.gui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tp.project.go_game.logic.Stone;

/**
 * 
 * @author Oliwier Kaszyca & Dominika Szydło
 * 
 * Panel odpowiedzialny za wyświetlanie planszy
 *
 */
public class Board extends JPanel{
	
	/**
	 * Obraz tła (graficzna interpretacja planszy)
	 */
	private Image img;
	
	/**
	 *  Instancja adaptera (klienta)
	 */
	private GUIAdapter myAdapter;
	
	/**
	 * Rozmiar planszy podawany przez klienta
	 */
	private int boardSize;
	private int squareSize;
	
	private int drawX;
	private int drawY;
	
	private Stone stones[][];


	/**
	 *  Konstruktor klasy
	 * @param myAdapter GUIAdapter (klient)
	 * @param boardSize wielkoś planszy
	 */
	public Board(GUIAdapter myAdapter, int boardSize) {
		
		this.myAdapter = myAdapter;
		this.boardSize = boardSize;
		stones = new Stone[boardSize][boardSize];
		
		setSize(840,840);
	    setLayout(null);

		initializeBoard();
		addMouseListener(myAdapter);

	}
	
	/**
	 * Tworzenie odpowiedniej planszy
	 */
	public void initializeBoard() {
		switch (boardSize) {
	    
	    case 9: 
	    	this.img = new ImageIcon("go_game/images/plansza99.png").getImage();
	    	//this.img = new ImageIcon("images/plansza99.png").getImage();
	    	break;
	    case 13:
	    	this.img = new ImageIcon("go_game/images/plansza1313.png").getImage();
	    	//this.img = new ImageIcon("images/plansza1313.png").getImage();
	    	break;
	    case 19:
	    	this.img = new ImageIcon("go_game/images/plansza1919.png").getImage();
	    	//this.img = new ImageIcon("images/plansza1919.png").getImage();
	    	break;
	    default:
	    	System.exit(0);
	    	break;
	    
	    }
	    
	  
	}
	
	
	/**
	 * Metoda odpowiedzialna za rysowanie pionków i planszy
	 */
	
	public void paintComponent(Graphics g) {
	   
		g.drawImage(img, 0, 0, null);
	   
	    Graphics2D g2d = (Graphics2D) g;

	    for(int i = 0; i < boardSize; i++) {
	    	for(int j = 0; j < boardSize; j++){
	    		if(stones[i][j] != null) {
	    			g2d.setColor(stones[i][j].getColor());
	    			drawConverter(i,j);
	    			g2d.fillOval(drawX - squareSize/4, drawY - squareSize/4, squareSize/2, squareSize/2);	
	    		}	
	    	}	
	    }
	 }
	
	
	public void drawConverter(int X, int Y) {
		
		squareSize = 840/(boardSize + 1); 
		
		// +1 -> numeracja tablicy od (0,0) 
		setDrawX((1 + X) * squareSize);
		setDrawY((1 + Y) * squareSize);
		
  }
	
	
	public int getBoardSize() {
		
		return boardSize;
		
	}
	
	
	public int getDrawX() {
		return drawX;
	}

	public void setDrawX(int drawX) {
		this.drawX = drawX;
	}

	public int getDrawY() {
		return drawY;
	}

	public void setDrawY(int drawY) {
		this.drawY = drawY;
	} 

	public void setBoard(Stone[][] board) {
		this.stones = board;
	}
	
	
	

	
}
