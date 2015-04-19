package phoebe;

public class GlueStain extends Stain {

	
	/**
	 * A GlueStain interakcióját megvalósító metódus. 
	 * A paraméterként átadott Player sebességét beállítja 1-re.
	 * @param player A Player, amelyre a cella hat.
	 */
	@Override
	public void interact(Player player) {
        player.setSpeed(1);
        eventCount--;
        if (eventCount == 0) {
        	currentCell.setGameObject(null);
        	System.out.println(currentCell.toString());
        }
	}
	
	@Override
	public String toString() {
		return "GlueStain";
	}
	
	/**
	 * GlueStain nem csinál semmit kör elején
	 */
	@Override
	public void onTurnStart() {}
	
	/**
	 * GlueStain nem csinál semmit kör végén
	 */
	@Override
	public void onTurnEnd() {}

}
