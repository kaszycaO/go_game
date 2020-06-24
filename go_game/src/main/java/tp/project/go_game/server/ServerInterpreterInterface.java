package tp.project.go_game.server;

public interface ServerInterpreterInterface {
	
	/**
	 * Metoda odpowiedzialna za przeprowadzenie ruchu
	 * @param message
	 * @return wiadomosc do klienta
	 */
	public String handleMessage(String message);
	
	/**
	 * Metoda odpowiedzialna za ruch bota
	 * @return wiadomosc o ruchu bota
	 */
	public String getBotMove(); 
}
