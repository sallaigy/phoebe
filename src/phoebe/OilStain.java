package phoebe;

public class OilStain extends Stain {

	/**
	* Az OilStain interakcióját megvalósító metódus. 
	* A paraméterként átadott Playernek letiltja a cellaválasztó-képességét.
	*/
	@Override
	public void interact(Player player) {
        Logger.methodEntry(this, player.toString());

        player.setCanChangeDirection(false);

        Logger.methodExit(this);
	}

	@Override
	public String toString() {
		return "OilStain";
	}

	@Override
	public void onTurnStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTurnEnd() {
		// TODO Auto-generated method stub
		
	}

}
