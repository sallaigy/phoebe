package phoebe;

public class OilStain extends Stain {

	@Override
	public void interact(Player player) {
        Logger.methodEntry(this, player.toString());
        
        Logger.methodExit(this);
	}

}
