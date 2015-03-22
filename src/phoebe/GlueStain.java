package phoebe;

public class GlueStain extends Stain {

	/**
	 * A GlueStain interakcióját megvalósító metódus. 
	 * A paraméterként átadott Player sebességét beállítja 1-re.
	 * @param player A Player, amelyre a cella hat.
	 */
	@Override
	public void interact(Player player) {
        Logger.methodEntry(this);
        player.setSpeed(1);
        Logger.methodExit(this);
	}

}
