package tp.projekt.go_game.client;

import tp.project.go_game.gui.GUIAdapter;

/*
 * klasa obserwujaca plansze, nasluchujaca klikniec
 */
public abstract class Observer 
{
	/*
	 * obserwowana klasa   
	 */
	protected GUIAdapter subject;
	/*
	 * funkcja wywolywana po kliknieciu na plansze
	 */
	public abstract void update();
	
}