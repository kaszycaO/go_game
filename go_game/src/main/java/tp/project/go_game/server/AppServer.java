package tp.project.go_game.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class AppServer {
	
	/**
     * gniazdko serwera
     */
    ServerSocket server = null;

    /**
     * gniazdko klienta
     */
    Socket client = null;

    /**
     * komunikaty od klienta
     */
    DataInputStream fromClient = null;
    /**
     * dane wysylane do klienta
     */
    DataOutputStream toClient = null;
    
    public AppServer() {
    	try {
            server = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
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
                setMessage(recievedMessage);
                toClient.writeUTF(sentMessage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }
    

    public void setMessage(String message) {
    	this.sentMessage = message;
    }
    
    public String getMessage() {
    	return this.recievedMessage;
    }

}
