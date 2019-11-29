package tp.project.go_game.gui;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel{
	
	
	private Image img;
	private GUIAdapter myAdapter;
	private int boardSize;


	
	public Board(GUIAdapter myAdapter, int boardSize) {
		
		this.myAdapter = myAdapter;
		this.boardSize = boardSize;
		
		
		
		    initializeBoard();
		    
		
	}
	
	
	public void initializeBoard() {
		
		setSize(840,840);
	    setLayout(null);
		
	    
	    switch (boardSize) {
	    
	    case 9: 
	    	this.img = new ImageIcon("images/plansza99.png").getImage();
	    	break;
	    case 13:
	    	this.img = new ImageIcon("images/plansza99.png").getImage();
	    	break;
	    case 19:
	    	this.img = new ImageIcon("images/plansza99.png").getImage();
	    	break;
	    default:
	    	System.exit(0);
	    	break;
	    
	    }
	    
	  
	}
	
	
	
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	   // g.fillOval(20, 20, 300, 300);
	    //g.drawOval(0, 0, 200, 200);
	    
	  }

	
}
