package tp.project.go_game.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	
	public MainFrame() {
		
		setBounds(500,500,100,100);
		setLayout(new BorderLayout());
		Board myBoard = new Board();
		
		
		
		
		
		
		add(myBoard);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
}
