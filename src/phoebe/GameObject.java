package phoebe;

/**
 * Az összes játékban szereplõ játékobjektum által implementált interfész.
 * @author Graics Bence
 *
 */
public interface GameObject {

	/**
	 * A játékobjektum interakcióját kezelõ metódus.
	 * @param player A Player, amelyre a játékobjektum hat.
	 */
	public void interact(Player player);
	
}
