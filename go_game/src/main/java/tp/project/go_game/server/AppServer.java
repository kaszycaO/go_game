package tp.project.go_game.server;

import java.io.BufferedInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tp.project.go_game.gui.Board;
import tp.project.go_game.logic.AppEngine;
import tp.project.go_game.mainpackage.Game;
import tp.projekt.go_game.client.Player;


public class AppServer {
	
	
	
	private AppEngine engine;
	private ServerInterpreter interpreter;
	
	/**
     * gniazdko serwera
     */
    public ServerSocket server = null;

    /**
     * gniazdko klienta
     */
    private Socket client = null;

    /**
     * komunikaty od klienta
     */
    private DataInputStream fromClient = null;
    /**
     * dane wysylane do klienta
     */
    private DataOutputStream toClient = null;
    private String recievedMessage = "";
    private ExecutorService pool;
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
    	engine = new AppEngine(boardSize);
    	interpreter = new ServerInterpreter(engine);
    	pool = Executors.newFixedThreadPool(200);
    }
    
    /**
     * odbieranie polecenia od klienta
     */
    public void listenSocket() {

        while(true) {
        	
        	Game game = new Game();
        	try {
				pool.execute(new ClientHandler(server.accept(), boardSize, "black", game));
				pool.execute(new ClientHandler(server.accept(), boardSize,  "white", game));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            try {
                client = server.accept();
                fromClient = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                toClient = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
                
               // recievedMessage = fromClient.readUTF();
                //toClient.writeUTF(interpreter.handleMessage(recievedMessage));
                
        }
    }

}
