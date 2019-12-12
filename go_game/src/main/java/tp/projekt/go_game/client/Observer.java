package tp.projekt.go_game.client;

import tp.project.go_game.gui.GUIAdapter;

public abstract class Observer 
{
	   protected GUIAdapter subject;
	   public abstract void update();
	
}