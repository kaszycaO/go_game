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
    private int boardSize;
    
    
    
    public AppServer(int boardSize, boolean ifBot) {
    	this.boardSize = boardSize;
    	try {
            server = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
    public void listenSocket() {

        while(true) {
        	Game game = new Game();
        	try {
				Thread client1 = new ClientHandler(server.accept(), boardSize, "black", game);
				client1.start();
				Thread client2 = new ClientHandler(server.accept(), boardSize,  "white", game);
				client2.start();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
        }
    }

}
