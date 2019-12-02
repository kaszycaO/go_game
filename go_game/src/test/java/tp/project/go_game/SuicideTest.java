package tp.project.go_game;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tp.project.go_game.logic.AppEngine;

public class SuicideTest {

	
	@Test
	public void SuicidalTest() {
		
		AppEngine engine = new AppEngine(9);
		
		engine.addStone(1, 0);
		engine.addStone(1, 1);
		engine.addStone(0, 1);
		engine.addStone(1, 2);
		engine.addStone(2, 1);
		
		engine.changeTurn();
		
	
	
	
		assertTrue(engine.checkIfSuicidal(0, 0));
		assertTrue(engine.checkIfSuicidal(1, 1));
		assertFalse(engine.checkIfSuicidal(3, 3));
		assertFalse(engine.checkIfSuicidal(8, 8));
	}
	
}
