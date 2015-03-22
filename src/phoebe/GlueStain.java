package phoebe;

public class GlueStain extends Stain {

	/**
	 * A GlueStain interakci�j�t megval�s�t� met�dus. 
	 * A param�terk�nt �tadott Player sebess�g�t be�ll�tja 1-re.
	 * @param player A Player, amelyre a cella hat.
	 */
	@Override
	public void interact(Player player) {
        Logger.methodEntry(this);
        player.setSpeed(1);
        Logger.methodExit(this);
	}

}
