package tp.project.go_game.exceptions;

public class IntersectionTakenException extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param e
	 */
	public IntersectionTakenException(Exception e) {
		super(e);
	}

	public IntersectionTakenException() {
	}
	

}
