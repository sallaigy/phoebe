package phoebe;

/**
 * A játéktér egy celláját megtestesítő osztály. 
 * Egy cellán tartózkodhat egy játékos vagy egy kisrobot, továbbá egy folt is. 
 * Amennyiben egy játékos olyan cellára ugrik, amelyen egy másik játékos 
 * tartózkodik, akkor az veszít, amelyiknek kisebb a sebessége 
 * (azonos sebesség esetén az győz aki ugrott). Ha olyan cellára ugrik, 
 * amelyen kisrobot található, akkor a kisrobot megsemmisül, és olajfolt lesz a helyén. 
 * Ha kisrobot ugrik olyan cellára, amelyen az egyik játékos, vagy egy másik 
 * kisrobot tartózkodik, akkor a kisrobot (amelyik ugrott) megváltoztatja irányát. 
 * Amennyiben egy játékos a cellára lép, a cellán tartózkodó folt interakcióba 
 * lép a játékossal, tehát kifejti hatását a játékosra a következő kör elején.
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
		if (gameObject != null) {
			this.gameObject.setCell(this);
		}
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

		if (null != this.gameObject) {
			this.gameObject.interact(player);
		}
	}	

	/**
	 * Visszaadja a cella típusát.
	 * @return: Cella típusa
	 */
	public CellType getCellType() {
		return cellType;
	}

	/**
	 * Beállítja a cella típusát, a paraméterben
	 * átvett cellatípusra.
	 * @param cellType: Cella típusa
	 */
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	/**
	 * Visszatér egy játékossal
	 * @return: Játékos
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Beállítja a játékost a paraméterben
	 * átvett játékosra.
	 * @param player: Játékos
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Visszatér a játékelem típusával.
	 * @return: Játékelem típusa
	 */
	public GameObject getGameObject() {
		return gameObject;
	}

	/**
	 * Beállítja a játékelemet a paraméterben
	 * átvett játékelemre.
	 * @param gameObject: Játékelem
	 */
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	public boolean containsRobot() {
		return gameObject.toString().equals("Hardworking-little-robot");
	}
	
	public boolean containsStain() {
		return (gameObject.toString().equals("OilStain") || gameObject.toString().equals("GlueStain"));
	}

	/**
	 * Visszatér az x koordináta értékével.
	 * @return: x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Visszatér az y koordináta értékével.
	 * @return: y
	 */
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
			} else if (gameObject.toString().equals("Hardworking-little-robot")) {
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
		gameObjectString = gameObject == null ? "null": gameObject.toString();

		return String.format("%s(%d,%d) GameObject: %s", this.getClass().getSimpleName(), this.x, this.y, gameObjectString);
	}

}