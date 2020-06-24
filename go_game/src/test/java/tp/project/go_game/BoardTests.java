package tp.project.go_game;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tp.project.go_game.gui.Board;
import tp.project.go_game.gui.MainFrame;

public class BoardTests {
	
	Board board; 
	MainFrame frame; 

	@Test
	public void drawConverterTest() {
		
		frame = new MainFrame(13);
		board = new Board(null, frame.getBoardSize());
		board.drawConverter(1, 1);
		assertEquals(board.getDrawX(), 120);
		assertEquals(board.getDrawY(), 120);
		
		frame = new MainFrame(19);
		board = new Board(null, frame.getBoardSize());
		board.drawConverter(1, 1);
		assertEquals(board.getDrawX(), 84);
		assertEquals(board.getDrawY(), 84);
		
		frame = new MainFrame(9);
		board = new Board(null, frame.getBoardSize());
		board.drawConverter(1, 1);
		assertEquals(board.getDrawX(), 168);
		assertEquals(board.getDrawY(), 168);
		
	}

}
