package tp.project.go_game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tp.project.go_game.logic.AppEngine;

public class RemoveStoneTest {
	
	
	
	
	
	@Test
	public void RemovalTest() {
		
		AppEngine engine = new AppEngine(9);
		
	
		engine.addStone(0, 1);
		engine.changeTurn();
		
		engine.addStone(0, 0);
		
		engine.changeTurn();
		
		//engine.addStone(1, 0);
		
		//assertTrue(engine.checkIfStrangles(1, 0));
	

		//assertTrue(engine.checkIfStrangled(0, 0));
	     assertFalse(engine.checkIfStrangled2(0, 0));

	
		
		
		//assertFalse(engine.checkIfSuicidal(3, 3));
		//assertFalse(engine.checkIfSuicidal(8, 8));
	}

}
