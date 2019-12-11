package tp.project.go_game.logic;

import tp.project.go_game.exceptions.*;

public interface EngineInterface {
	
	public void handleMove() throws KoRuleViolatedException, CoordinatesOutOfBoundsException, SuicidalMoveException;
	
	public void handleButtons();
}
