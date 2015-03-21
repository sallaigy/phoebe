package phoebe;

public class Cell {
	
	private int i;
	
	private int j;
	
	private CellType cellType;
	
	private Player player;
	
	private GameObject gameObject;
	
	/**
	 * A Cella osztály konstruktora.
 	 * @param i Sorkoordináta
	 * @param j Oszlopkoordináta
	 * @param cellType Cellának a típusa
	 */
	public Cell(int i, int j, CellType cellType) {
		this.i = i;
		this.j = j;
		this.cellType = cellType;
	}
	
	/**
	 * A cella interakcióját végzõ metódus. Meghívja a rajta elhelyezkedõ GameObject metódusát a rajta lévõ
	 * Player paraméterrel.
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
