package tp.project.go_game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import tp.project.go_game.server.AppServer;

public class MainFrame extends JFrame {

	
	private Client myAdapter;
	private int boardSize = -1;
	public AppServer server;
	protected Board myBoard;
	protected FeaturesPanel myFeaturesPanel;
	private String[] sizes = {"9x9","13x13","19x19"};
	

	public MainFrame() {
		
		initializeWindow();
	}
	
	
	private void initializeWindow() {
		String size = (String)JOptionPane.showInputDialog(this, "Wybierz rozmiar planszy", "Nowa gra", JOptionPane.QUESTION_MESSAGE,null, sizes,sizes[0]);
		
		
		if (size == null) {
			
			System.exit(1);
		}
		
		else if (size.equals(sizes[0])) {
			boardSize = 9;
		}
		else if(size.equals(sizes[1])) {
			boardSize = 13;
		}
		else if (size.equals(sizes[2])) {
			
			boardSize = 19;
		}
		
		
			
	
		
		//setSize(1040, 880);
		setTitle("Go game");
		setPreferredSize(new Dimension(1040, 880));
		setLayout(new BorderLayout());
		myAdapter = new Client(this);
		myBoard = new Board(myAdapter, boardSize);
		myFeaturesPanel = new FeaturesPanel(myAdapter);
		this.server = new AppServer(myBoard);
		
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
