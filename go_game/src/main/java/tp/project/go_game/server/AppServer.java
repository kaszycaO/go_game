package tp.project.go_game.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;


public class AppServer {
	

	/**
     * gniazdko serwera
     */
    public ServerSocket server = null;
    /*
     * obiekt obslugujacy klienta 1
     */
    private ClientHandler client1;
    /*
     * obiekt obslugujacy klienta 2
     */
    private ClientHandler client2;
    /*
     * rozmiar planszy
     */
    private int boardSize;
    
    /*
     * konstruktor serwera, tworzy gniazdko serwera
     */
    public AppServer() {
    	try {
            server = new ServerSocket(4444);
            InetAddress ip = InetAddress.getByName("localhost");
            System.out.println(ip);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
    /*
     * funkcja nasluchujaca na porcie
     */
    public void listenSocket() throws Exception {
    	Game game;
        try {
			client1 = new ClientHandler(server.accept());
			this.boardSize = client1.getBoardSize();
			if (client1.checkIfBot() == 1) {
				game = new Game(client1.getBoardSize(), client1);
			} else {
				client2 = new ClientHandler(server.accept(), boardSize);
				game = new Game(client1.getBoardSize(), client1, client2);
			}
			 while (game.isOn()) {
		        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Zamknieto serwer - brak graczy");
        System.exit(0);
    }
    
}
