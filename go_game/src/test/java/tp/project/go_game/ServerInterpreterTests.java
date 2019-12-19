package tp.project.go_game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tp.project.go_game.logic.AppEngine;
import tp.project.go_game.server.ServerInterpreter;

public class ServerInterpreterTests {
	
	ServerInterpreter interpreter;
	
	@Before
	public void prepareTest() {
		
		AppEngine engine = new AppEngine(9);
		interpreter = new ServerInterpreter(engine);
		
	}
	
	@Test
	public void handleMessageTest() {
		
		
		String msg1 = "button resign";
		String result = "5 remis 6";
		assertEquals(interpreter.handleMessage(msg1),result);
		
		 msg1 = "coordinates 1 2 black";
	     result = "0 1 2 black 4";
		 assertEquals(interpreter.handleMessage(msg1),result);
		
		
		
	}
	
	@Test
	public void interpretMessageTest() {
		
		//coords
		
		String message = "1 2 black";
		String[] interpretedMsg = new String[3];
		
		interpretedMsg[0] = "1";
		interpretedMsg[1] = "2";
		interpretedMsg[2] = "black";
		
		assertEquals(interpreter.interpretMessage(message)[0], interpretedMsg[0]);
		assertEquals(interpreter.interpretMessage(message)[1], interpretedMsg[1]);
		assertEquals(interpreter.interpretMessage(message)[2], interpretedMsg[2]);
		
		//buttons
		
		String message1 = "button resign";
		String[] interpretedMsg1 = new String[2];
		
		interpretedMsg1[0] = "button";
		interpretedMsg1[1] = "resign";
		
		assertEquals(interpreter.interpretMessage(message1)[0], interpretedMsg1[0]);
		assertEquals(interpreter.interpretMessage(message1)[1], interpretedMsg1[1]);
		
		
		
		
	}
	
	@Test
	public void getBotMoveTest() {
		
		String msg = "0 8 0 black 4";
		assertEquals(interpreter.getBotMove(), msg);
		
		
	}

}
