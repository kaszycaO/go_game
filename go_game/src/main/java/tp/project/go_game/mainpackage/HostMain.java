package tp.project.go_game.mainpackage;


import tp.project.go_game.server.AppServer;

/**
 * 
 * @author Oliwier Kaszyca & Dominika Szydlo
 * 
 * Główna klasa uruchamiająca serwer
 *
 */
public class HostMain 
{
    public static void main( String[] args )
    {
		AppServer server = new AppServer();
		try {
			server.listenSocket();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
