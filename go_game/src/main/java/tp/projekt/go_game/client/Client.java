package tp.projekt.go_game.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Observer {
	
	private ClientInterpreter interpreter;
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
    
    private boolean blackTurn = true;
    private boolean blackPlayer;
	
	public Client(int boardSize, boolean blackPlayer) {
		interpreter = new ClientInterpreter(boardSize);
		this.blackPlayer = blackPlayer;
		interpreter.frame.myAdapter.attach(this);
	}
	
	private void exchangeWithServer() {
		try {
			  
	           socket = new Socket("localhost", 4444);
	           toServer = new DataOutputStream(socket.getOutputStream());
	           fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	          
	           toServer.writeUTF(interpreter.sendMessage());
	           

	           String line = fromServer.readUTF();
	           
	           char checkTurn = line.charAt(0);
	            
	        /*   if(checkTurn == '1') {
	        	   
	        	   blackTurn = !blackTurn;
	         } */ 

	           interpreter.handleMessage(line);

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
	public void update() {
		
		if(blackTurn == blackPlayer) {
			
			exchangeWithServer();
			
		}
			
		
	}
}
