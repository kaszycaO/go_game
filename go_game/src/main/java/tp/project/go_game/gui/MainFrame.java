package tp.project.go_game.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame {

	
	private GUIAdapter myAdapter;
	private int boardSize = 9;
	
	public MainFrame() {
		
		
		myAdapter = new GUIAdapter();
		
		initializeWindow();
		
	
	}
	
	
	private void initializeWindow() {
		
		setSize(1040, 880);
		setLayout(new BorderLayout());


		Board myBoard = new Board(myAdapter, boardSize);
		FeaturesPanel myButtonPanel = new FeaturesPanel(myAdapter);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, myButtonPanel, myBoard);
		splitPane.setEnabled(false);


		
		
		add(splitPane);
		//add(myBoard);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
	}
	
	
	
}
