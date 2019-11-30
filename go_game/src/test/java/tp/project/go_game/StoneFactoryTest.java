package tp.project.go_game;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import static org.junit.Assert.*;
import org.junit.Test;

import tp.project.go_game.logic.Stone;
import tp.project.go_game.logic.ConcreteStoneFactory;
import tp.project.go_game.logic.StoneFactory;

/**
 * Unit test for simple App.
 * 
 */
public class StoneFactoryTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void createWhiteStoneTest()
    {
    	StoneFactory factory = new ConcreteStoneFactory();
    	Stone whiteStone = factory.getStone("White");
    	
    	assertEquals(whiteStone.getColor(), Color.white);
    	assertEquals(whiteStone.getChecked(), false);
    	
    	
    	
    }
}
