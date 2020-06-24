package tp.project.go_game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import tp.projekt.go_game.client.ClientInterpreter;

public class ClientInterpreterTest {

	ClientInterpreter interpreter; 
	
	@Before
	public void prepareObject() {
		interpreter = new ClientInterpreter(9);
	
	}
	
	@Test
	public void InterpretMessageTest() {
		
		String message = "0 black 1 1 4";
		
		String[] converted = new String[4];
		converted[0] = "0";
		converted[1] = "black";
		converted[2] = "1";
		converted[3] = "1";
		
		assertEquals(interpreter.interpretMessage(message)[0], converted[0]);
		assertEquals(interpreter.interpretMessage(message)[1], converted[1]);
		assertEquals(interpreter.interpretMessage(message)[2], converted[2]);
		assertEquals(interpreter.interpretMessage(message)[3], converted[3]);
		
		String message2 = "0 black 1 1 null 2 2 null 3 0 10";
		String[] converted2 = new String[10];
		converted2[0] = "0";
		converted2[1] = "black";
		converted2[7] = "null";
		converted2[9] = "0";
		
		assertEquals(interpreter.interpretMessage(message2)[0], converted2[0]);
		assertEquals(interpreter.interpretMessage(message2)[1], converted2[1]);
		assertEquals(interpreter.interpretMessage(message2)[7], converted2[7]);
		assertEquals(interpreter.interpretMessage(message2)[9], converted2[9]);
		
	}

	@Test
	public void handleMoveTest() {
		
		
		interpreter.handleMessage("0 1 1 black 4");
		assertEquals(interpreter.frame.getPanelMessage(), "");
		
		interpreter.handleMessage("1 ");
		assertEquals(interpreter.frame.getPanelMessage(), "Naruszona zasada KO");
		
		interpreter.handleMessage("2 ");
		assertEquals(interpreter.frame.getPanelMessage(), "Uzywaj planszy!");
		
		interpreter.handleMessage("3 ");
		assertEquals(interpreter.frame.getPanelMessage(), "Ruch samobojczy!");
		
		interpreter.handleMessage("4 ");
		assertEquals(interpreter.frame.getPanelMessage(), "Pole zajete!");
		
		interpreter.handleMessage("5 black 3");
		assertEquals(interpreter.frame.getPanelMessage(), "Wygral: black o: 3 pkt");
		
		interpreter.handleMessage("6 ");
		assertEquals(interpreter.frame.getPanelMessage(), "Twoj przeciwnik uciekl :c");
		
		
		
	}
	
	
	
	
}
