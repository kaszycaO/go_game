package tp.project.go_game;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import tp.project.go_game.logic.AppEngine;

public class doMoveTest {
	
	@Test
	public void coordsAndSuicidalTest() {
		AppEngine engine = new AppEngine(9);
		engine.addStone(1, 0);
		engine.addStone(0, 1);
		engine.addStone(1, 2);
		engine.addStone(2, 1);
		engine.changeTurn();
		ArrayList<Integer> coords = engine.getCoordsToCheck(1,1);
		ArrayList<Integer> expectedCoords = new ArrayList<Integer>();
		
		Integer[] sample1 = {0,1};
		Integer[] sample2 = {2,1};
		Integer[] sample3 = {1,0};
		Integer[] sample4 = {1,2};
		
		expectedCoords.add(0);
		expectedCoords.add(1);
		expectedCoords.add(2);
		expectedCoords.add(1);
		expectedCoords.add(1);
		expectedCoords.add(0);
		expectedCoords.add(1);
		expectedCoords.add(2);
		
		for(int i=0;i<4;i++) {
			assertEquals(coords.get(2*i), expectedCoords.get(2*i));
			assertEquals(coords.get(2*i+1), expectedCoords.get(2*i+1));
		}
		assertFalse(engine.checkIfGotBreaths(1, 1));
		assertTrue(engine.checkIfStrangled(1, 1));
		assertTrue(engine.checkIfSuicidal(1, 1));
	}
	

}