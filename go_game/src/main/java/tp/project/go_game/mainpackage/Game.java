package tp.project.go_game.mainpackage;

import tp.projekt.go_game.client.Player;

public class Game {
	
	public Player currentPlayer;
	
	 public synchronized void move(Player player) {
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
