package tp.project.go_game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import tp.project.go_game.server.AppServer;
/**
 * Glowne okienko
 * @author Oliwier Kaszyca & Dominika Szydlo
 *
 */
public class MainFrame extends JFrame {

	/**
	 * Adapter do przyciskow i panelu
	 */
	public GUIAdapter myAdapter;
	
	/**
	 * Rozmiar planszy, wybierany pozniej przez gracza
	 */
	private int boardSize = -1;
	
	/**
	 * Plansza do gry
	 */
	public Board myBoard;
	
	/**
	 * Panel z guzikami
	 */
	protected FeaturesPanel myFeaturesPanel;
	
	/**
	 * Wspolrzedne X klikniecia
	 */
	private int xclicked;
	
	/**
	 * Wspolrzedne Y klikniecia
	 */
	private int yclicked;
	
	/**
	 * Zmienna przechowujaca info o tym czy kliknieto myszka na panel
	 */
	private boolean mousePressed = false;
	
	/**
	 * Zmienna przechowujaca info o tym czy kliknieto guzik 
	 */
	private String buttonClicked;

	public MainFrame(int boardSize) {
		
		this.boardSize = boardSize;
		initializeWindow();
	}
	
	/**
	 * inicjalizacja okna
	 */
	private void initializeWindow() {
		setTitle("Go game");
		setPreferredSize(new Dimension(1140, 880));
		setLayout(new BorderLayout());
		myAdapter = new GUIAdapter(this);
		myBoard = new Board(myAdapter, boardSize);
		myFeaturesPanel = new FeaturesPanel(myAdapter);
	
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, myFeaturesPanel, myBoard);
		splitPane.setEnabled(false);
		add(splitPane);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
	
	public void setPanelMessage(String message) {
		
		myFeaturesPanel.message.setText(message);
	}
	public String getPanelMessage() {
		
		return myFeaturesPanel.message.getText();
		
	}

	
}
