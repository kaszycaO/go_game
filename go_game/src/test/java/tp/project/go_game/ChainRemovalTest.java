package tp.project.go_game;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tp.project.go_game.logic.AppEngine;

public class ChainRemovalTest {

	
	@Test
	public void RemovalTest() {
		
		AppEngine engine = new AppEngine(9);
		
	
		engine.addStone(1, 0);
		engine.addStone(2, 0);
		engine.addStone(3, 0);
		engine.addStone(4, 0);
		engine.addStone(4, 1);
		engine.addStone(4, 2);
		engine.addStone(4, 3);
		engine.addStone(3, 4);
		engine.addStone(2, 4);
		engine.addStone(1, 4);
		engine.addStone(0, 3);
		engine.addStone(0, 2);
		engine.addStone(0, 1);
		engine.changeTurn();
		
		engine.addStone(1, 1);
		engine.addStone(1, 2);
		engine.addStone(1, 3);
		engine.addStone(2, 1);
		engine.addStone(3, 1);
		engine.addStone(3, 2);
		engine.addStone(3, 3);
		engine.addStone(2, 3);
		
		engine.changeTurn();
		engine.addStone(2, 2);
		
		
		
		//assertTrue(engine.checkIfStrangles(2, 2));
		engine.getChain(1, 1);
		//assertTrue(engine.checkIfStrangled(0, 0));
	
		
		
		//assertFalse(engine.checkIfSuicidal(3, 3));
		//assertFalse(engine.checkIfSuicidal(8, 8));
	}
	
	
	
	
	
}
