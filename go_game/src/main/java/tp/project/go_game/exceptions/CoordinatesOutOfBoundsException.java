package tp.project.go_game.exceptions;

public class CoordinatesOutOfBoundsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param e
	 */
	public CoordinatesOutOfBoundsException(Exception e) {
		super(e);
	}

	public CoordinatesOutOfBoundsException() {
	}
	
}
