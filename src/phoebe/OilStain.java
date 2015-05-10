package phoebe;

/**
 * Az olajfoltokat megtestesítő osztály. Erre lépve a játékos az adott körben nem képes irányítani a mozgását. 
 *
 */
public class OilStain extends Stain {

	/**
	 * Az olajfolt köreinek a száma, mielőtt elhasználódik: 4.
	 */
	public OilStain() {
		this.eventCount = 6;
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
	
	/**
	 * Az olajfolt minden körben eggyel elhasználódik.
	 */
	@Override
	public void onTurnStart() {
		eventCount--;
		if (eventCount == 0) {
			currentCell.setGameObject(null);
			System.out.println(currentCell.toString());
		}
	}
	
	/**
	 * Az olajfolt a kör végén nem csinál semmit.
	 */
	@Override
	public void onTurnEnd() {}

}
