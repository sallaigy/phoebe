package phoebe;

public class OilStain extends Stain {

	/**
	 * Az OilStain interakci�j�t megval�s�t� met�dus. 
	 * A param�terk�nt �tadott Playernek letiltja a cellav�laszt�-k�pess�g�t.
	 * @param player A Player, amelyre a cella hat.
	 */
	@Override
	public void interact(Player player) {
        Logger.methodEntry(this, player.toString());

        player.setCanChangeDirection(false);

        Logger.methodExit(this);
	}

}
