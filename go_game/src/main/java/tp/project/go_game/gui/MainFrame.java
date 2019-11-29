package tp.project.go_game.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	
	private GUIAdapter myAdapter;
	
	public MainFrame() {
		
		myAdapter = new GUIAdapter();
		
		initializeWindow();
		
	
	}
	
	
	private void initializeWindow() {
		
		setSize(1366, 768);
		setLayout(new BorderLayout());


		Board myBoard = new Board(myAdapter);
		
		
		
		add(myBoard);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
	}
	
	
	
}
