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
    private Socket socket = null;
    /**
     * komunikaty do serwera
     */
    private DataOutputStream  toServer = null;
    /**
     * komunikaty od serwera
     */
    private DataInputStream fromServer = null;
	private MainFrame myFrame;
	
	
	public GUIAdapter(MainFrame myFrame) {
		
		this.myFrame = myFrame;
		
		
	}
	//TODO trzeba poczytac o observer, zrobic atrybuty xclicked, yclicked, buttonclicked w main frame i update'owac te zmienne
	//z mainframe'a trzeba przekazac do client interpretera, przekonwertowac, podac do klienta i na serwer
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
