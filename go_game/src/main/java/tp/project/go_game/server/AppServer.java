package tp.project.go_game.server;

import java.io.IOException;
import java.net.ServerSocket;


public class AppServer {
	

	/**
     * gniazdko serwera
     */
    public ServerSocket server = null;
    private ClientHandler client1;
    private ClientHandler client2;
    private boolean ifBot;
    private int boardSize;
    
    public AppServer() {
    	this.ifBot =false;
    	try {
            server = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
    public void listenSocket() throws Exception {
    	
        try {
			client1 = new ClientHandler(server.accept(), "black");
			this.boardSize = client1.getBoardSize();
			if (client1.checkIfBot() == 1) {
				//TODO tu robi sie bot
			} else if (client1.checkIfBot() == 0) {
				client2 = new ClientHandler(server.accept(), "white",boardSize);
				client1.opponent = client2;
				client2.opponent = client1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        while (client1.checkIfPresent() || client2.checkIfPresent()) {
        	do {
        		client1.processYourMove();
        	} while (!client1.checkIfMoveWasLegit());
        	do {
        		client2.processYourMove();
        	} while (!client2.checkIfMoveWasLegit());
        }
        System.exit(0);
    }
    
}
