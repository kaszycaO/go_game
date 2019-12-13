package tp.project.go_game.mainpackage;

import tp.project.go_game.server.ClientHandler;
import tp.projekt.go_game.client.Client;

public class Game {
	
	public ClientHandler currentPlayer;
	
	 public synchronized void move(ClientHandler player) {
	        if (player != currentPlayer) {
	            throw new IllegalStateException("Not your turn");
	        } else if (player.opponent == null) {
	            throw new IllegalStateException("You don't have an opponent yet");
	        } /*else if (board[location] != null) {
	            throw new IllegalStateException("Cell already occupied");
	        }
	        board[location] = currentPlayer;*/
	        currentPlayer = currentPlayer.opponent;
	    }
	
}
