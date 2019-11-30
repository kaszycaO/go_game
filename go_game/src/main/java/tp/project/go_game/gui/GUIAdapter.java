package tp.project.go_game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



/**
 * 
 * @author Oliwier Kaszyca
 * 
 * Client
 */
public class GUIAdapter extends MouseAdapter implements ActionListener {

	
	MainFrame myFrame;
	private int squareX;
	private int squareY;
	
	public GUIAdapter(MainFrame myFrame) {
		
		this.myFrame = myFrame;
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	
		
		
		coordinatesConverter(e.getX(), e.getY());
		
		if(squareX > 0 && squareY >0) {
			
		myFrame.myBoard.repaint();
		System.out.println("Kwadrat: "+squareX+" "+squareY);
		
		
		}
		
		
	}
	
	public void coordinatesConverter(int X, int Y) {
		
		int squareSize = 840/(myFrame.getBoardSize() + 1) ; 
		int newX = X + squareSize/2;
		int newY = Y + squareSize/2;
		
		int squareX = newX/squareSize;
		int squareY = newY/squareSize;
		
		System.out.println(squareX);
		System.out.println(squareY); 
			
			setSquareX(squareX);
			setSquareY(squareY);
			
		
			
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getSource() == myFrame.myFeaturesPanel.resignButton) {
			
			System.out.println("Ha! przegrałeś");
			
		}
		
		else if (e.getSource() == myFrame.myFeaturesPanel.passButton) {
			
			System.out.println("Ha! spasowałeś");
		}
		
		
		
		
		
	}
	
	public void setSquareX(int X) {
		
		this.squareX = X;
		
	} 
	public void setSquareY(int Y) {
		
		this.squareY = Y;
		
	} 
	
	
	public int getSquareX() {
		
		return squareX;
		
	} 
	public int getSquareY() {
		
		return squareY;
		
	} 

}
