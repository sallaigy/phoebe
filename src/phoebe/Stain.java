package phoebe;

public abstract class Stain implements GameObject {

	/**
	 * Absztrakt metódus az egyes Stain-ek interakcióira.
	 * @param A Player, amelyre a cella hat.
	 */
	public abstract void interact(Player player);

}
