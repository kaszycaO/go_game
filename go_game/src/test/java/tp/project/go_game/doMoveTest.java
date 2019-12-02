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
		engine.addStone(2, 2);
		engine.changeTurn();
		ArrayList<Integer[]> coords = engine.getCoordsToCheck(1,1);
		ArrayList<Integer[]> expectedCoords = new ArrayList<Integer[]>();
		Integer[] sample = {0,1};
		expectedCoords.add(sample);
		sample[0] = 1;
		sample[1] = 2;
		expectedCoords.add(sample);
		sample[0] = 1;
		sample[1] = 0;
		expectedCoords.add(sample);
		sample[0] = 1;
		sample[1] = 2;
		expectedCoords.add(sample);
		for(int i=0;i<4;i++) {
			assertEquals(coords.get(i)[0], expectedCoords.get(i)[0]);
			assertEquals(coords.get(i)[1], expectedCoords.get(i)[1]);
		}
		assertFalse(engine.checkIfGotBreaths(1, 1));
		assertTrue(engine.checkIfStrangled(1, 1));
		assertTrue(engine.checkIfSuicidal(1, 1));
	}
	

}