package tp.project.go_game.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FeaturesPanel extends JPanel {
	
	
	protected JButton resignButton;
	protected JButton passButton;
	private GUIAdapter myAdapter;
	
	
	FeaturesPanel(GUIAdapter myAdapter){
		
		this.myAdapter = myAdapter;
		
		setSize(200,200);

		setLayout(new FlowLayout(FlowLayout.CENTER,10,810));
		
		resignButton = new JButton("Zrezygnuj");
		resignButton.addActionListener(this.myAdapter);
		
		passButton = new JButton("Spasuj");
		
		passButton.addActionListener(this.myAdapter);
		
		
		add(resignButton);
		add(passButton);
		
		
	}

}
