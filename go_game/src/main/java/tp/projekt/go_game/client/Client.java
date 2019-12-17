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
    
    private boolean goOn = true;
	
	public Client(int boardSize) {
		interpreter = new ClientInterpreter(boardSize);
		interpreter.frame.myAdapter.attach(this);
		try {
			socket = new Socket("localhost", 4444);
	        toServer = new DataOutputStream(socket.getOutputStream());
	        fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
            System.exit(1);
		}
		exchangeWithServer();
	}
	
	private void closeConnection() {
		try {
			socket.close();
	        toServer.close();
	        fromServer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
            System.exit(1);
		}
		
		
	}
	
	private void exchangeWithServer() {
		//while (true) {
			//if (interpreter.isMoveWasMade()) {
				System.out.println("Elo in info to serv");
				try {
					toServer.writeUTF(interpreter.sendMessage());
					String line = fromServer.readUTF();
					interpreter.handleMessage(line);
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
					System.exit(1);
				}
				
				//interpreter.setMoveWasMade(false);

			//}
		//	if (!goOn) {
			//	break;
			//}
	//	}
		
	}
	@Override
	public void update() {
		
		exchangeWithServer();
		
		if (!goOn)
			closeConnection();
			
		//System.out.println("siup");
		//interpreter.setMoveWasMade(true);
	}
}
