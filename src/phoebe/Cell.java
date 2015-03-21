package phoebe;

public class Cell {

	private int i;

	private int j;

	private CellType cellType;

	private Player player;

	private GameObject gameObject;

	public Cell(int i, int j, CellType cellType) {
		this.i = i;
		this.j = j;
		this.cellType = cellType;
	}

	public Cell(int i, int j, Player player) {
		this.i = i;
		this.j = j;
		this.cellType = CellType.CELL_VALID;
		this.player = player;
	}

	public Cell(int i, int j, GameObject gameObject) {
		this.i = i;
		this.j = j;
		this.cellType = CellType.CELL_VALID;
		this.gameObject = gameObject;
	}

	public void interact() {
		Logger.methodEntry(this);

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

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

}
