package tp.project.go_game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import tp.project.go_game.mainpackage.Game;


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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        while (client1.checkIfPresent() || client2.checkIfPresent()) {
        	do {
        		System.out.println("jestem w do 1");
        		client1.run();
        	} while (!client1.checkIfMoveWasLegit());
        	do {
        		client2.run();
        		System.out.println("jestem w do 2");
        	} while (!client2.checkIfMoveWasLegit());
        }
        System.exit(0);
    }
    
}
