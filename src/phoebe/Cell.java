package phoebe;

/**
 * A négyzetrácsos pálya egy-egy négyzetét jelenti egy Cell.
 * Egy cellán tartózkodhat legfeljebb egy játékos, illetve legfeljebb egy folt.
 */
public class Cell {
	
	private int x;
	
	private int y;
	
	private CellType cellType;
	
	private Player player;
	
	private GameObject gameObject;
	
	/**
	 * A Cella osztály konstruktora.
 	 * @param x Sorkoordináta
	 * @param y Oszlopkoordináta
	 * @param cellType Cellának a típusa
	 */
	public Cell(int x, int y, CellType cellType, Player player, GameObject gameObject) {
		this.x = x;
		this.y = y;
		this.cellType = cellType;
		this.player = player;
		this.gameObject = gameObject;
	}
	
	/**
	 * Tartalék Cell konstruktor, amely paraméter nélkül hívható.
	 */
	public Cell() {
	    // nop
    }

    /**
	 * Ha a játékos az adott cellára lépett, ezen metódus hívódik meg.
	 * A metódus pedig a cellán elhelyezkedő gameObjectnek hívja meg a megfelelő metódusát, amely a paraméterként átvett Player objektumra kifejti a hatását.
	 * Ha a cellán nincsen folt, természetesen nem történik semmi.
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
    /**
     * Visszaadja a cella típusát, a beolvasott fájl formátuma szerint
     * @return: Cella típusa
     */
    public String printCell() {
    	if (player != null && player.getIdx() == 0) {
    		return "4";
    	} else if (player != null && player.getIdx() == 1) {
    		return "5";
    	}
    	
    	if (gameObject != null) {
    		if (gameObject.toString().equals("OilStain")) {
    			return "2";
    		} else if (gameObject.toString().equals("GlueStain")) {
    			return "3";
    		} else if (gameObject.toString().equals("Robot")) {
    			return "6";
    		}
    	} else if (this.cellType == CellType.CELL_INVALID) {
    	    return "0";
    	} else {
    	    return "1";
    	}
    	
    	return "X";
    	 
    }
    
    @Override
    public String toString() { 
    	String gameObjectString = new String();
    	gameObjectString = gameObject == null ? "NULL": gameObject.toString();
        
    	return String.format("%s (%d, %d) GameObject: %s", this.getClass().getSimpleName(), this.x, this.y, gameObjectString);
    }

}