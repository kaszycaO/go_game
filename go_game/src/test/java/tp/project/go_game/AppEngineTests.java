package tp.project.go_game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import tp.project.go_game.exceptions.CoordinatesOutOfBoundsException;
import tp.project.go_game.exceptions.IntersectionTakenException;
import tp.project.go_game.exceptions.KoRuleViolatedException;
import tp.project.go_game.exceptions.SuicidalMoveException;
import tp.project.go_game.logic.AppEngine;

public class AppEngineTests {
	
	/**
	 * Podstawowy silnik o planszy w rozmiarze 9x9
	 */
	private AppEngine engine;
	/**
	 * Silnik o planszy w rozmiarze 13x13
	 */
	private AppEngine engineT;
	/**
	 * S--ilnik o planszy w rozmiarze 19x19
	 */
	private AppEngine engineN;
	
	@Before
	public void prepareTests() {
		
		engine = new AppEngine(9);
		engineT = new AppEngine(13);
		engineN = new AppEngine(19);
	
	}
	
	@Test
	public void ChainRemovalTest() {
		
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
		
		
		
		assertTrue(engine.checkIfStrangles(2, 2));
		assertTrue(engine.checkIfStrangledDomki(1, 1));
		assertTrue(engine.checkIfStrangledDomki(1, 2));
		assertTrue(engine.checkIfStrangledDomki(2, 1));
		assertTrue(engine.checkIfStrangledDomki(3, 1));
		assertTrue(engine.checkIfStrangledDomki(3, 2));
		assertTrue(engine.checkIfStrangledDomki(2, 3));
	
		
	}
	
	@Test
	public void IfInBoundsTest() {
		
		assertFalse(engine.checkIfInBounds(-1, -1));
		assertFalse(engine.checkIfInBounds(-1, 10));
		assertFalse(engine.checkIfInBounds(9, 9));
		assertFalse(engine.checkIfInBounds(0, 9));
		assertTrue(engine.checkIfInBounds(2, 1));
		
		assertFalse(engineT.checkIfInBounds(-1, -1));
		assertFalse(engineT.checkIfInBounds(1, 13));
		assertTrue(engineT.checkIfInBounds(9, 9));
		assertTrue(engineT.checkIfInBounds(0, 9));
		assertTrue(engineT.checkIfInBounds(2, 1));
		
		assertFalse(engineN.checkIfInBounds(-1, -1));
		assertFalse(engineN.checkIfInBounds(1, 20));
		assertFalse(engineN.checkIfInBounds(19, 9));
		assertTrue(engineN.checkIfInBounds(0, 9));
		assertTrue(engineN.checkIfInBounds(2, 1));
		

	}
	
	@Test
	public void IfKoTest() {
		
		engine.addStone(1,1);
		engine.addStone(2,0);
		engine.addStone(2,2);
		
		engine.changeTurn();
		engine.addStone(3,0);
		engine.addStone(4,1);
		engine.addStone(3,2);
		engine.addStone(2,1);
		engine.prepareArrays();
	
		
		engine.changeTurn();
		engine.prepareArrays();
		engine.addStone(3, 1);
		
		assertTrue(engine.checkIfStrangles(3,1));
		assertTrue(engine.checkIfStrangledDomki(2,1));
		
		engine.removeStone(2, 1);
		
		
		engine.changeTurn();
		engine.prepareArrays();
		engine.addStone(2, 1);
		
		
		assertTrue(engine.checkIfKo(2,1));
		

	}
	
	@Test
	public void SuicidalTest() {
		
		engine.addStone(1, 0);
		engine.addStone(0, 1);
		
		engine.changeTurn();
		engine.addStone(0, 0);
		
		assertTrue(engine.checkIfSuicidal(0,0));
		

	}
	
	
	@Test
	public void Suicidal2Test() {
		
		engine.addStone(1, 0);
		engine.addStone(0, 1);
		
		engine.changeTurn();
		engine.addStone(2, 0);
		
		assertFalse(engine.checkIfSuicidal(2,0));
		

	}
	
	@Test
	public void checkIfNeighbourTest() {
		
		assertTrue(engine.checkIfNeighbour(0,0,0,1));
		assertTrue(engineT.checkIfNeighbour(12,0,12,1));
		assertTrue(engineN.checkIfNeighbour(0,18,1,18));
		
		assertFalse(engine.checkIfNeighbour(0, 0, 2, 3));
		
	}
	
	@Test
	public void getDomkaChainTest() {
		
		ArrayList<Integer> testList = new ArrayList<>();
		
		engine.addStone(0,0);
		engine.addStone(0,1);
		engine.addStone(0,2);
		engine.addStone(0,3);
		engine.addStone(1,0);
		engine.addStone(2,0);
		engine.addStone(3,0);
		
		testList.add(0);
		testList.add(0);
		
		testList.add(1);
		testList.add(0);
		
		testList.add(2);
		testList.add(0);
		
		testList.add(3);
		testList.add(0);
		
		testList.add(0);
		testList.add(1);
		
		testList.add(0);
		testList.add(2);
		
		testList.add(0);
		testList.add(3);
		
		assertEquals(engine.getDomkaChain(0, 0), testList);
		
	}
	
	
	@Test
	public void checkIfStrangledDomkiTest() {
		
		engine.addStone(1, 0);
		
		engine.changeTurn();
		engine.addStone(0,0);
		
		engine.changeTurn();
		engine.addStone(0,1);
		
		assertTrue(engine.checkIfStrangledDomki(0, 0));
		assertFalse(engine.checkIfStrangledDomki(1, 0));
		
		
	}
	
	@Test
	public void checkIfGotBreathsTest() {
		
		engine.addStone(1, 0);
		
		engine.changeTurn();
		engine.addStone(0,0);
		
		engine.changeTurn();
		engine.addStone(0,1);
		
		assertFalse(engine.checkIfGotBreaths(0, 0));
		assertTrue(engine.checkIfGotBreaths(1, 0));
		
	}
	
	@Test
	public void getCoordsToCheckTest() {
		
		ArrayList<Integer> testList = new ArrayList<>();
		
		testList.add(0);
		testList.add(1);
		
		testList.add(2);
		testList.add(1);
		
		testList.add(1);
		testList.add(0);
		
		testList.add(1);
		testList.add(2);
		
		assertEquals(engine.getCoordsToCheck(1, 1), testList);
		
		
	}
	
	@Test
	public void getCoordsToRemoveTest() {
		
		engine.addStone(1, 0);
		
		engine.changeTurn();
		
		engine.addStone(0, 0);
		engine.changeTurn();
	
		engine.addStone(0, 1);
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(0);
		coords.add(0);
		
		assertEquals(engine.getCoordsToRemove(0, 1), coords);
		assertTrue(engine.checkIfStrangledDomki(0, 0));
		
		engine.removeStrangledStones(0,1);
		assertEquals(engine.currentBoard[0][0], null);
	
	}
	
	@Test
	public void checkIfStranglesTest() {
		
		engine.addStone(1, 0);
		
		engine.changeTurn();
		engine.addStone(0,0);
		
		engine.changeTurn();
		engine.addStone(0,1);
		
		assertTrue(engine.checkIfStrangles(0, 1));
		assertFalse(engine.checkIfStrangles(0, 0));
		
	}
	
	@Test
	public void checkIfTakenTest() {
		
		engine.addStone(1, 1);
		
		assertTrue(engine.checkIfTaken(1, 1));
		assertFalse(engine.checkIfTaken(7, 6));
		
	}

	
	@Test
	public void handleMoveTest() throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException, IntersectionTakenException {
		
		
		engine.addStone(1, 0);
		engine.changeTurn();
		
		engine.addStone(0, 0);
		engine.changeTurn();
		
			
		engine.handleMove(0, 1);
		engine.handleMove(2, 3);
	
	
	}
	
	
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test 
	public void handleMoveCoordinatesOutOfBoundsExceptionTest() throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException, IntersectionTakenException{
		
		    thrown.expect(CoordinatesOutOfBoundsException.class);
			engine.handleMove(-1,-1);
		
	} 
	
	@Test 
	public void handleMoveSuicidalMoveExceptionTest() throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException, IntersectionTakenException{
		
			engine.addStone(0, 1);
			engine.addStone(1, 0);
			
			engine.changeTurn();
			
			thrown.expect(SuicidalMoveException.class);
			engine.handleMove(0,0);
			
	
		

	} 
	@Test 
	public void handleMoveIntersectionTakenExceptionTest() throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException, IntersectionTakenException {
		
		    engine.addStone(0, 1);
			engine.addStone(1, 0);
			
			
			thrown.expect(IntersectionTakenException.class);
			engine.handleMove(1,0);
			
	} 
	
	@Test 
	public void handleMoveKoRuleViolatedExceptionTest() throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException, IntersectionTakenException {
		
		engine.addStone(1,1);
		engine.addStone(2,0);
		engine.addStone(2,2);
		
		engine.changeTurn();
		engine.addStone(3,0);
		engine.addStone(4,1);
		engine.addStone(3,2);
		engine.addStone(2,1);
		engine.prepareArrays();
	
		
		engine.changeTurn();
		engine.prepareArrays();
		engine.addStone(3, 1);
		
		assertTrue(engine.checkIfStrangles(3,1));
		assertTrue(engine.checkIfStrangledDomki(2,1));
		
		engine.removeStone(2, 1);
		
		
		engine.changeTurn();
		
		thrown.expect(KoRuleViolatedException.class);
		engine.handleMove(2,1);
		
		
	} 
	
	@Test
	public void getChangesTest() {
		
		engine.addStone(1, 1);
		engine.changeTurn();
		engine.addStone(1, 2);
		
		String testChanges = "1 1 black 1 2 white ";
		assertEquals(engine.getChanges(), testChanges);
		
		engine.resetChanges();
		engine.prepareArrays();

		
		engine.changeTurn();
		engine.addStone(1, 3);
		
		testChanges = "1 3 black ";
		assertEquals(engine.getChanges(), testChanges);
		
		
		
	}
	@Test
	public void handleButtonsTest() {
		
		engine.addStone(1, 0);
		engine.handleButtons("resign");
		assertEquals(engine.getFinalScore(), 1);
		
		
		
	}
	

}

