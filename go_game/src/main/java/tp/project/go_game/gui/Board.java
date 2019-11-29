package tp.project.go_game.gui;


import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

public class Board extends JPanel{
	
	
	private Image img;
	private GUIAdapter myAdapter;

	
	public Board(GUIAdapter myAdapter) {
		
		this.myAdapter = myAdapter;
		
		
		
		    initializeBoard();
		    
		
	}
	
	
	public void initializeBoard() {
		
		setSize(560,560);
	    setLayout(null);
		
	    this.img = new ImageIcon("images/indeks.png").getImage();
		
	}
	
	
	
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	   // g.fillOval(20, 20, 300, 300);
	   // g.drawOval(0, 0, 200, 200);
	    
	  }

	
}
