package phoebe;

public class OilStain extends Stain {

	public OilStain() {
		this.eventCount = 4;
	}
	
	/**
	* Az OilStain interakcióját megvalósító metódus. 
	* A paraméterként átadott Playernek letiltja a cellaválasztó-képességét.
	*/
	@Override
	public void interact(Player player) {
        player.setCanChangeDirection(false);
	}

	@Override
	public String toString() {
		return "OilStain";
	}

	@Override
	public void onTurnStart() {
		eventCount--;
		if (eventCount == 0) {
			currentCell.setGameObject(null);
			System.out.println(currentCell.toString());
		}
	}

	@Override
	public void onTurnEnd() {}

}
