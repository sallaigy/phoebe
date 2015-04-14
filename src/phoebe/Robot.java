package phoebe;

public class Robot implements GameObject {

	private Cell currentCell = new Cell();
	private Cell destinationCell = new Cell();
	private Map map;
	
	@Override
	public void interact(Player player) {
		
	}

	@Override
	public void onTurnStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTurnEnd() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "Robot";
	}
}
