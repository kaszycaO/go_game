package tp.project.go_game.mainpackage;

import tp.project.go_game.server.ClientHandler;
import tp.projekt.go_game.client.Client;

public class Game {
	
	public ClientHandler currentPlayer;
	
	public boolean getPermissiontoMove(ClientHandler player) {
		if (player != currentPlayer || player.opponent == null) {
	          return false;
	   } else {
	     currentPlayer = currentPlayer.opponent;
	     return true;
	   }
	}
	
	public void setCurrentPlayer(ClientHandler player) {
		this.currentPlayer = player;
	}
}
