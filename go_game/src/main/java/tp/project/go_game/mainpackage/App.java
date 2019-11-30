package tp.project.go_game.mainpackage;

import tp.project.go_game.gui.MainFrame;

/**
 * 
 * @author Oliwier Kaszyca & Dominika Szydło
 * 
 * Główna klasa uruchamiająca serwer i tworząca główne okienko
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	MainFrame frame = new MainFrame();
    	if (frame.server != null) {
    		
    		frame.server.listenSocket();
    		
    	} 
    
    	
    }
}
