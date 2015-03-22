package phoebe;

public class OilStain extends Stain {

	/**
	 * Az OilStain interakcióját megvalósító metódus. 
	 * A paraméterként átadott Playernek letiltja a cellaválasztó-képességét.
	 * @param player A Player, amelyre a cella hat.
	 */
	@Override
	public void interact(Player player) {
        Logger.methodEntry(this, player.toString());
        player.setCanChangeDirection(false);
        Logger.methodExit(this);
	}

}
