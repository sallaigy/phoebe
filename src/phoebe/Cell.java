package phoebe;

public class Cell {
	
	private int x;
	
	private int y;
	
	private CellType cellType;
	
	private Player player;
	
	private GameObject gameObject;
	
	/**
	 * A Cella oszt�ly konstruktora.
 	 * @param x Sorkoordin�ta
	 * @param y Oszlopkoordin�ta
	 * @param cellType Cell�nak a t�pusa
	 */
	public Cell(int x, int y, CellType cellType, Player player, GameObject gameObject) {
		this.x = x;
		this.y = y;
		this.cellType = cellType;
		this.player = player;
		this.gameObject = gameObject;
	}
	
	public Cell() {
	    // nop
    }

    /**
	 * A cella interakci�j�t v�gz� met�dus. Megh�vja a rajta elhelyezked� GameObject met�dus�t a rajta l�v�
	 * Player param�terrel.
	 */
	public void interact() {
        Logger.methodEntry(this);
        
        if (null != this.gameObject) {
            this.gameObject.interact(player);
        }
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
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "](i=" + this.x + ",j=" + this.y + ")";
    }

}