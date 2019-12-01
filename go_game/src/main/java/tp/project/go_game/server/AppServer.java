package tp.project.go_game.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tp.project.go_game.gui.Board;
import tp.project.go_game.logic.AppEngine;


public class AppServer {
	
	
	
	private AppEngine engine;
	
	Board board;
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
    
    
    public AppServer(Board board) {
    	try {
            server = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    	this.board = board;
    	engine = new AppEngine(board.getBoardSize());
    }
    
    /**
     * odbieranie polecenia od klienta
     */
    public void listenSocket() {

        while(true) {
            try {
                client = server.accept();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            try {
                fromClient = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                toClient = new DataOutputStream(client.getOutputStream());
                recievedMessage = fromClient.readUTF();
                board.setBoard(engine.doMove(recievedMessage));
                toClient.writeUTF(engine.getMessage());
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

}
