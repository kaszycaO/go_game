package tp.project.go_game;
import org.junit.Test;
import static org.junit.Assert.*;

import tp.project.go_game.gui.MainFrame;
import tp.project.go_game.logic.AppEngine;
import tp.projekt.go_game.client.ClientInterpreter;

public class CoordinatesConverterTest {
	
	

	@Test
	public void testCoordinates() {
		
		ClientInterpreter interpreter = new ClientInterpreter(9);
		
		interpreter.frame.myAdapter.setYourTurn(true);
		
		interpreter.frame.setXclicked(180);
		interpreter.frame.setYclicked(180);
		interpreter.frame.setMousePressed(true);
		assertEquals(interpreter.sendMessage(), "coordinates 1 1");
		
		interpreter.frame.setXclicked(84);
		interpreter.frame.setYclicked(84);
		interpreter.frame.setMousePressed(true);
		assertEquals(interpreter.sendMessage(), "coordinates 0 0");
		
		interpreter.frame.setXclicked(760);
		interpreter.frame.setYclicked(84);
		interpreter.frame.setMousePressed(true);
		assertEquals(interpreter.sendMessage(), "coordinates 8 0");
		
		interpreter.frame.setButtonClicked("button resign");
		interpreter.frame.setMousePressed(false);
		assertEquals(interpreter.sendMessage(), "button resign");
		
		interpreter.frame.setButtonClicked("button pass");
		interpreter.frame.setMousePressed(false);
		assertEquals(interpreter.sendMessage(), "button pass");
		
		
		
	}
	
}

