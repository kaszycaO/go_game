package tp.project.go_game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import tp.project.go_game.exceptions.CoordinatesOutOfBoundsException;
import tp.project.go_game.exceptions.IntersectionTakenException;
import tp.project.go_game.exceptions.KoRuleViolatedException;
import tp.project.go_game.exceptions.SuicidalMoveException;
import tp.project.go_game.logic.AppEngine;

public class RemoveStoneTest {
	
	
	
	
	
	@Test
	public void RemovalTest() {
		
		AppEngine engine = new AppEngine(9);
		
		engine.addStone(1, 0);
		
		engine.changeTurn();
		
		engine.addStone(0, 0);
		engine.changeTurn();
		try {
			engine.handleMove(0,1);
		} catch (KoRuleViolatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoordinatesOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SuicidalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntersectionTakenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(0);
		coords.add(0);
		
		assertEquals(engine.getCoordsToRemove(0, 1), coords);
		assertTrue(engine.checkIfStrangledDomki(0, 0));
		assertEquals(engine.currentBoard[0][0], null);
	
	}

}
