package tp.project.go_game.server;

public interface ClientHandlerInterface {
	/*
	 * funkcja wysylajaca komunikat do klienta
	 */
	public void sendMessage(String msg);
	/*
	 * funckja sprawdzajaca czy rozgrywka odbedzie sie z botem
	 */
	public int checkIfBot();
	
	/*
	 * funkcja zwracajaca rozmiar planszy
	 */
	public int getBoardSize();
	/*
	 * funkcja pobierajaca komunikat od klienta
	 */
	public String getMove();
	/*
	 * funkcja zamykajaca gniazdko klienta i jego strumienie danych
	 */
	public void closeConnection();
	/*
	 * funkcja sprawdzajaca czy klient jest obecny
	 */
	boolean checkIfPresent();
}
