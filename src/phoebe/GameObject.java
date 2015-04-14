package phoebe;

/**
 * Egy nem játékos objektum, amely interakcióba léphet egy játékossal.
 * Ezek lehetnek például a játékosra ható effektek.
 * A bővíthetőség kedvéért alkalmazzuk, arra az esetre, ha esetleg a foltokon kívül egyéb nem játékos objektum is szükséges lenne.
 */
public interface GameObject {

	/**
	 * A játékobjektum interakcióját kezelő metódus.
	 * @param player A Player, amelyre a játékobjektum hat.
	 */
	public void interact(Player player);
	public void onTurnStart();
	public void onTurnEnd();
	public String toString();
	
}
