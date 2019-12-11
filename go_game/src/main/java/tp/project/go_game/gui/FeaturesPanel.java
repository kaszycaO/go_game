package tp.project.go_game.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Oliwier Kaszyca & Dominika Szydło
 * 
 * Panel z przyciskami i informacjami
 *
 */
public class FeaturesPanel extends JPanel {
	
	/**
	 * Przycisk zrezygnuj
	 */
	protected JButton resignButton;
	
	/**
	 * Przycisk spasuj
	 */
	protected JButton passButton;
	
	/**
	 * Panel z wiadomościami dotyczącymi przebiegu gry
	 */
	protected JLabel message;
	
	/**
	 * Adapter (klient) 
	 */
	private Client myAdapter;
	
	
	/**
	 * Konstruktor klasy
	 * 
	 * @param myAdapter GUIAdapter (klient)
	 * 
	 */
	FeaturesPanel(Client myAdapter){
		
		this.myAdapter = myAdapter;
		
		setSize(200,200);
		setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
		
		resignButton = new JButton("Zrezygnuj");
		resignButton.addActionListener(this.myAdapter);
		
		passButton = new JButton("Spasuj");
		passButton.addActionListener(this.myAdapter);
		
		message = new JLabel();
		
		add(resignButton);
		add(passButton);
		add(message);
		
	}

}
