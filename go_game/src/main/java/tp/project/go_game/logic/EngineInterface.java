package tp.project.go_game.logic;

import tp.project.go_game.exceptions.*;

public interface EngineInterface {
	
	public void handleMove(int X, int Y) throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException, IntersectionTakenException;
	
	public void handleButtons(String button);
}
