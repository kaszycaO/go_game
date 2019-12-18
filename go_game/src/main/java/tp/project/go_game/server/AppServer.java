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
    private int boardSize;
    
    public AppServer() {
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
			Game game = new Game(client1.getBoardSize(), client1, client2);
			 while (game != null) {
		        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.exit(0);
    }
    
}
