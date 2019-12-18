package tp.project.go_game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import tp.projekt.go_game.client.Observer;;



/**
 * 
 * @author Oliwier Kaszyca
 * 
 * Client
 */
public class GUIAdapter extends MouseAdapter implements ActionListener {


	private MainFrame myFrame;
	private ArrayList<Observer> observers;
	
	
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
	}
		

	

	   public void attach(Observer observer){
	      observers.add(observer);		
	   }

	   public void notifyAllObservers(){

	      for (Observer observer : observers) {
	         observer.update();
	      }
	   } 	

	

}
