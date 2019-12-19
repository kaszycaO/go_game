package tp.project.go_game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import tp.projekt.go_game.client.Observer;;



/**
 * 
 * @author Oliwier Kaszyca & Dominika Szydlo
 * 
 * Adapter okna i paneli
 */
public class GUIAdapter extends MouseAdapter implements ActionListener {

	/**
	 * glowne okno
	 */
	private MainFrame myFrame;
	
	/**
	 * lista obserwatorow
	 */
	private ArrayList<Observer> observers;
	
	/**
	 * czy twoja tura
	 */
	private boolean yourTurn = false;
	
	
	public GUIAdapter(MainFrame myFrame) {
		
		observers = new ArrayList<Observer>();
		this.myFrame = myFrame;
	}

	@Override
	public void mousePressed(MouseEvent event) {

		myFrame.setXclicked(event.getX());
		myFrame.setYclicked(event.getY());
		myFrame.setMousePressed(true);
		notifyAllObservers();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		myFrame.setMousePressed(false);
		if(e.getSource() == myFrame.myFeaturesPanel.resignButton) {
     	  
     	    myFrame.setButtonClicked("button resign");
     	   notifyAllObservers();
			
		}
		else if (e.getSource() == myFrame.myFeaturesPanel.passButton) {
			
			  myFrame.setButtonClicked("button pass");
			  notifyAllObservers();	
		}
		else if (e.getSource() == myFrame.myFeaturesPanel.exitButton) {
			
			int n = JOptionPane.showConfirmDialog(null, "Czy jestes pewien?", "Koniec gry", JOptionPane.YES_NO_OPTION); 
			if (n == JOptionPane.YES_OPTION) {
				myFrame.setButtonClicked("button exit");
				notifyAllObservers();
			}	
		}
	}
	
	/**
	 * Dodaj obserwatora do listy
	 * @param observer
	 */
	public void attach(Observer observer) {
      observers.add(observer);		
      }
	 
	 /**
	  * Metoda informujaca wszystkich obserwatorow 
	  */
	 public void notifyAllObservers(){

	      for (Observer observer : observers) {
	         observer.update();
	      }
	   }

	 
	public boolean isYourTurn() {
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	

		

	

}
