package tp.projekt.go_game.client;
/*
 * interface interpretera klienta
 */
public interface ClientInterpreterInterface {
	/*
	 * funkcja przetwarzajaca komunikat z serwera
	 */
	public void handleMessage(String message);
	
	/*
	 * funkcja generujaca komunikat do serwera
	 */
	public String sendMessage();
}
