package tp.project.go_game.logic;

public class ConcreteStoneFactory implements StoneFactory {

	@Override
	public Stone getStone(String stoneType) {
		
		if(stoneType == "White") {
			
			return new WhiteStone();
					
		}
		
		else if(stoneType == "Black") {
			
			return new BlackStone();
			
		}
		
		return null;
	}

}
