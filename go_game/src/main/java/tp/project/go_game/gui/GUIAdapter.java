package tp.project.go_game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



/**
 * 
 * @author Oliwier Kaszyca
 * 
 * Client
 */
public class GUIAdapter extends MouseAdapter implements ActionListener {

	/**
     * gniazdko klienta
     */
    Socket socket = null;
    /**
     * komunikaty do serwera
     */
    DataOutputStream  toServer = null;
    /**
     * komunikaty od serwera
     */
    DataInputStream fromServer = null;
	MainFrame myFrame;
	private int squareX;
	private int squareY;
	private int drawX;
	private int drawY;
	
	public GUIAdapter(MainFrame myFrame) {
		
		this.myFrame = myFrame;
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent event) {

	
    
		try {
	           socket = new Socket("localhost", 4444);
	           toServer = new DataOutputStream(socket.getOutputStream());
	           fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

	           toServer.writeUTF("coordinates "+event.getX()+" "+event.getY());

	           String line = fromServer.readUTF();

	           myFrame.myFeaturesPanel.message.setText(line);

	           socket.close();
	           toServer.close();
	           fromServer.close();
	        }
	        catch (IOException e) {
	            System.out.println(e.getMessage());
	            System.exit(1);
	        }
		
		
	coordinatesConverter(event.getX(), event.getY());
		
		
		if(squareX > 0 && squareY >0) {
			
		drawConverter(squareX, squareY);	
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
	
	public void drawConverter(int X, int Y) {
		
		int squareSize = 840/(myFrame.getBoardSize() + 1); 
		setDrawX(X * squareSize);
		setDrawY(Y * squareSize);
		
  }
		
	
		
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getSource() == myFrame.myFeaturesPanel.resignButton) {
			
			System.out.println("Ha! przegrales");
			
		}
		
		else if (e.getSource() == myFrame.myFeaturesPanel.passButton) {
			
			System.out.println("Ha! spasowasowales");
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

}
