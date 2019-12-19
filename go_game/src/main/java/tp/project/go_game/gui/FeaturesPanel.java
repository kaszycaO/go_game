package tp.project.go_game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

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
	 * Przycisk wyjdz
	 */
	protected JButton exitButton;
	
	/**
	 * Panel z wiadomościami dotyczącymi przebiegu gry
	 */
	protected JLabel message;
	
	/**
	 * Adapter (klient) 
	 */
	private GUIAdapter myAdapter;
	
	
	/**
	 * Konstruktor klasy
	 * 
	 * @param myAdapter GUIAdapter (klient)
	 * 
	 */
	FeaturesPanel(GUIAdapter myAdapter){
		
		this.myAdapter = myAdapter;
		
		setPreferredSize(new Dimension(300,880));
		setLayout(new BorderLayout());
		
		resignButton = new JButton("Zrezygnuj");
		resignButton.addActionListener(this.myAdapter);
		
		passButton = new JButton("Spasuj");
		passButton.addActionListener(this.myAdapter);
		
		exitButton = new JButton("Wyjdz");
		exitButton.addActionListener(this.myAdapter);
		
		message = new JLabel();
		Font font = new Font("Verdana", Font.BOLD, 15);
		message.setFont(font);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(300,200));
		buttonPanel.setLayout(new GridLayout(3,1));
		buttonPanel.add(resignButton, BorderLayout.NORTH);
		buttonPanel.add(passButton, BorderLayout.NORTH);
		buttonPanel.add(exitButton,BorderLayout.SOUTH);
	
		add(message, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}

}
