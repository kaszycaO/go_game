package tp.project.go_game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



/**
 * 
 * @author Oliwier Kaszyca
 * 
 * Client
 */
public class GUIAdapter extends MouseAdapter implements ActionListener {

	
	MainFrame myFrame;
	
	public GUIAdapter(MainFrame myFrame) {
		
		this.myFrame = myFrame;
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		
		System.out.println(e.getY());
		System.out.println(e.getY());
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getSource() == myFrame.myFeaturesPanel.resignButton) {
			
			System.out.println("Ha! przegrałeś");
			
		}
		
		else if (e.getSource() == myFrame.myFeaturesPanel.passButton) {
			
			System.out.println("Ha! spasowałeś");
		}
		
		
		
		
		
	}

}
