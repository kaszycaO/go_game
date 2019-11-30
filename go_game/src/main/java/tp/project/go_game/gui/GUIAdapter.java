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
	
	public GUIAdapter(MainFrame myFrame) {
		
		this.myFrame = myFrame;
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		
		
		System.out.println(event.getY());
		System.out.println(event.getY());
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

}
