package tp.project.go_game.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FeaturesPanel extends JPanel {
	
	
	protected JButton resignButton;
	protected JButton passButton;
	protected JLabel message;
	private GUIAdapter myAdapter;
	
	
	FeaturesPanel(GUIAdapter myAdapter){
		
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
