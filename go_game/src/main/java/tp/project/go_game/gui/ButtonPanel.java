package tp.project.go_game.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	
	
	private JButton resignButton;
	private JButton passButton;
	private GUIAdapter myAdapter;
	
	
	ButtonPanel(GUIAdapter myAdapter){
		
		this.myAdapter = myAdapter;
		setLayout(new FlowLayout());
		
		resignButton = new JButton("Zrezygnuj");
		resignButton.addActionListener(myAdapter);
		
		
		
		
		
		
	}

}
