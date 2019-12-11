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
public class Client extends MouseAdapter implements ActionListener {

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
	
	
	public Client(MainFrame myFrame) {
		
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
	           
	           myFrame.myBoard.repaint();

	           socket.close();
	           toServer.close();
	           fromServer.close();
	        }
	        catch (IOException e) {
	            System.out.println(e.getMessage());
	            System.exit(1);
	        }

		
		}

    
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
	           socket = new Socket("localhost", 4444);
	           toServer = new DataOutputStream(socket.getOutputStream());
	           fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	           if(e.getSource() == myFrame.myFeaturesPanel.resignButton) {
	        	   toServer.writeUTF("button resign");
	   			}
	   		else if (e.getSource() == myFrame.myFeaturesPanel.passButton) {
	   			toServer.writeUTF("button pass");
	   		}
	           String line = fromServer.readUTF();

	           myFrame.myFeaturesPanel.message.setText(line);
	           
	           myFrame.myBoard.repaint();

	           socket.close();
	           toServer.close();
	           fromServer.close();
	        }
	        catch (IOException ex) {
	            System.out.println(ex.getMessage());
	            System.exit(1);
	        }
	}


	

	

}
