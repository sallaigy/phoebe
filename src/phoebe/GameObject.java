package phoebe;

/**
 * Egy interfész, azon osztályok valósítják meg, amelyek nem játékosok, 
 * és interakcióba léphetnek egy játékossal. 
 * Ezek a foltok, és a kisrobotok. 
 */
public interface GameObject {

	/**
	 * A játékobjektum interakcióját kezelő metódus.
	 * @param player A Player, amelyre a játékobjektum hat.
	 */
	public void interact(Player player);
	/**
	 * Kör elején történő események megvalósításához.
	 */
	public void onTurnStart();
	/**
	 * Kör végén történő események megvalósításához.
	 */
	public void onTurnEnd();
	/**
	 * Stringgé alakítás.
	 * @return: Egy string
	 */
	public String toString();
	/**
	 * Cella beállítása.
	 * @param cell: Cella
	 */
	public void setCell(Cell cell);
	
}
