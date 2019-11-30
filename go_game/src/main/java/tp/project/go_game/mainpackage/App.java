package tp.project.go_game.mainpackage;

import tp.project.go_game.gui.MainFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	MainFrame frame = new MainFrame();
    	frame.server.listenSocket();
    	
    }
}
