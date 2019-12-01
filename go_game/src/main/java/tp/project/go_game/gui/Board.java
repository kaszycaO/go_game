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
	//chwilowa zmiana kolorów
	private int colorCounter = 1;
	
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
	    	//this.img = new ImageIcon("go_game/images/plansza99.png").getImage();
	    	this.img = new ImageIcon("images/plansza99.png").getImage();
	    	break;
	    case 13:
	    	//this.img = new ImageIcon("go_game/images/plansza1313.png").getImage();
	    	this.img = new ImageIcon("images/plansza1313.png").getImage();
	    	break;
	    case 19:
	    	//this.img = new ImageIcon("go_game/images/plansza1919.png").getImage();
	    	this.img = new ImageIcon("images/plansza1919.png").getImage();
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
	    
	    if(myAdapter.getDrawX() != 0 && myAdapter.getDrawY() !=0) {
	    	//changing color
	    	if(colorCounter%2 == 1) {
	    		g2d.setPaint(Color.black);
	    		
	    	}
	    	else {
	    		g2d.setPaint(Color.white);
	    	}
	    	
	    	colorCounter++;
	    		
	    	g2d.fillOval(myAdapter.getDrawX() -25, myAdapter.getDrawY()-25,50,50);
	    	
	    }
	    
	    
	  }
	
	
	public int getBoardSize() {
		
		return boardSize;
		
	}
	
	
	
	
	
	

	
}
