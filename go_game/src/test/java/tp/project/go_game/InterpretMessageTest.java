package tp.project.go_game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tp.project.go_game.logic.AppEngine;

public class InterpretMessageTest {


	
	
	@Test
	public void testCoordinates() {
		
		AppEngine engine1 = new AppEngine(9);
	    String testMessage = "button resign";
		
		engine1.doMove(testMessage);
		
		
		assertEquals(engine1.getConvertedMessage()[0], "button");
		assertEquals(engine1.getConvertedMessage()[1], "resign");
		assertEquals(engine1.getConvertedMessage()[2], "");

		
	
		
		
		
	}

	
	
	
	
}
