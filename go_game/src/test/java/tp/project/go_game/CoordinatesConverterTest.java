package tp.project.go_game;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static org.junit.Assert.*;

import tp.project.go_game.logic.AppEngine;

public class CoordinatesConverterTest {
	
	@Test
	public void testCoordinates() {
		
		AppEngine engine1 = new AppEngine(9);
		AppEngine engine2 = new AppEngine(13);
		AppEngine engine3 = new AppEngine(19);
		
		engine1.doMove(84, 84);
		engine2.doMove(70, 70);
		engine3.doMove(60,60);
		
		assertEquals(engine1.getSquareX(), 1);
		assertEquals(engine1.getSquareY(), 1);
		
		assertEquals(engine2.getSquareX(), 1);
		assertEquals(engine2.getSquareY(), 1);
		
		assertEquals(engine3.getSquareX(), 1);
		assertEquals(engine3.getSquareY(), 1);
		
		
		
	}


}

