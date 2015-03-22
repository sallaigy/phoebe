package phoebe;

public class Cell {
	
	private int i;
	
	private int j;
	
	private CellType cellType;
	
	private Player player;
	
	private GameObject gameObject;
	
	/**
	 * A Cella oszt�ly konstruktora.
 	 * @param i Sorkoordin�ta
	 * @param j Oszlopkoordin�ta
	 * @param cellType Cell�nak a t�pusa
	 */
	public Cell(int i, int j, CellType cellType, Player player, GameObject gameObject) {
		this.i = i;
		this.j = j;
		this.cellType = cellType;
		this.player = player;
		this.gameObject = gameObject;
	}
	
	/**
	 * A cella interakci�j�t v�gz� met�dus. Megh�vja a rajta elhelyezked� GameObject met�dus�t a rajta l�v�
	 * Player param�terrel.
	 */
	public void interact() {
        Logger.methodEntry(this);
        gameObject.interact(player);
        Logger.methodExit(this);
	}

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
    
    public int getI() {
    	return i;
    }
    
    public int getJ() {
    	return j;
    }

}