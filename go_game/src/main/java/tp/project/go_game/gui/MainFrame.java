package tp.project.go_game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import tp.project.go_game.server.AppServer;

public class MainFrame extends JFrame {

	
	public GUIAdapter myAdapter;
	private int boardSize = -1;
	protected Board myBoard;
	protected FeaturesPanel myFeaturesPanel;
	private int xclicked;
	private int yclicked;
	private boolean mousePressed = false;
	private String buttonClicked = "XD";
	
	
	
	public MainFrame(int boardSize) {
		
		this.boardSize = boardSize;
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


	public int getXclicked() {
		return xclicked;
	}


	public void setXclicked(int xclicked) {
		this.xclicked = xclicked;
	}


	public int getYclicked() {
		return yclicked;
	}


	public void setYclicked(int yclicked) {
		this.yclicked = yclicked;
	}


	public String getButtonClicked() {
		return buttonClicked;
	}


	public void setButtonClicked(String buttonClicked) {
		this.buttonClicked = buttonClicked;
	}


	public boolean isMousePressed() {
		return mousePressed;
	}


	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	
}
