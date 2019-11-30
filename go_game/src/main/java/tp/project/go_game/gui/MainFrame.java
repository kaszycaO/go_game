package tp.project.go_game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame {

	
	private GUIAdapter myAdapter;
	private int boardSize = 13;
	
	protected Board myBoard;
	protected FeaturesPanel myFeaturesPanel;
	
	public MainFrame() {
		
	
		
		initializeWindow();
		
	
	}
	
	
	private void initializeWindow() {
		
		//setSize(1040, 880);
		setTitle("Go game");
		setPreferredSize(new Dimension(1040, 880));
		setLayout(new BorderLayout());

		myAdapter = new GUIAdapter(this);
		myBoard = new Board(myAdapter, boardSize);
		myFeaturesPanel = new FeaturesPanel(myAdapter);
		
		//myBoard.addActionListener(myAdapter);
	
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, myFeaturesPanel, myBoard);
		splitPane.setEnabled(false);


		
		add(splitPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		pack();
	}
	
	public int getBoardSize() {
		
		return boardSize;
		
	}
	
	
}
