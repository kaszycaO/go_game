package tp.project.go_game.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	
	private GUIAdapter myAdapter;
	
	public MainFrame() {
		
		myAdapter = new GUIAdapter();
		
		Board myBoard = new Board(myAdapter);
		
		
		
		
		
		add(myBoard);
	
	}
	
	
	private void initializeWindow() {
		
		setSize(1366, 768);
		setLayout(new BorderLayout());
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
	}
	
	
	
}
