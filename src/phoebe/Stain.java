package phoebe;

/**
 * A foltokat megvalósító osztályok. Minden folt szerepelhet pontosan egy cellán.
 * Ha egy játékos olyan cellára lép, amelyen folt is van, a folt valamilyen negatív hatást fejt ki a játékosra.
 */
public abstract class Stain implements GameObject {

	private boolean visible = false;
	protected int eventCount;
	protected Cell currentCell;
	
	
		
	/**
	 * Absztrakt metódus az egyes Stain-ek interakcióira.
	 * @param A Player, amelyre a cella hat.
	 */
	public abstract void interact(Player player);
	public abstract void onTurnStart();
	public abstract void onTurnEnd();
	public abstract String toString();
	public void setCell(Cell cell) {
		this.currentCell = cell;
	}

}
