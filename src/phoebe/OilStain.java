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
	public String ToString() {
		return "OilStain";
	}

}
