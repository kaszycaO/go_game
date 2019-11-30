package tp.project.go_game.gui;


import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel{
	
	
	private Image img;
	private GUIAdapter myAdapter;
	private int boardSize;


	
	public Board(GUIAdapter myAdapter, int boardSize) {
		
		this.myAdapter = myAdapter;
		this.boardSize = boardSize;
		
		setSize(840,840);
	    setLayout(null);
		
		
		    initializeBoard();
		    addMouseListener(myAdapter);
		    
		
	}
	
	
	public void initializeBoard() {
		
		switch (boardSize) {
	    
	    case 9: 
	    	this.img = new ImageIcon("images/plansza99.png").getImage();
	    	break;
	    case 13:
	    	this.img = new ImageIcon("images/plansza1313.png").getImage();
	    	break;
	    case 19:
	    	this.img = new ImageIcon("images/plansza1919.png").getImage();
	    	break;
	    default:
	    	System.exit(0);
	    	break;
	    
	    }
	    
	  
	}
	
	
	
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	    
	    if(myAdapter.getDrawX() != 0 && myAdapter.getDrawY() !=0) 
	    	g.fillOval(myAdapter.getDrawX() -25, myAdapter.getDrawY()-25,50,50);
	    //g.drawOval(0, 0, 200, 200);
	    
	  }

	
}
