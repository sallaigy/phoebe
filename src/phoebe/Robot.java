package phoebe;

import java.util.List;
import java.util.Random;

/**
 * A kisrobotokat megtestesítő osztály. A kisrobotok egyik foltról (miután azt
 * feltakarították) mennek a hozzájuk legközelebb található folthoz. *
 */
public class Robot implements GameObject {

	public static boolean randomStatus = true;

	public int idx;

	private Cell currentCell = new Cell();

	private Cell destinationCell = null;

	private Map map;

	private Random rand = new Random();

	private Cell initialPosition;

	/**
	 * A keményen dolgozó kisrobot konstruktora.	 * 
	 * @param map A térkép, amelyre lehelyezzük.
	 * @param initialPosition A kisrobot kezdőpoziciója.
	 * @param id A kisrobot azonosítója.
	 */
	public Robot(Map map, Cell initialPosition, int id) {
		this.idx = id;
		this.map = map;
		this.initialPosition = initialPosition;
		this.currentCell = this.initialPosition;
		this.currentCell.setGameObject(this);
	}

	/**
	 * A kisrobot játékossal történő interakcióját megvalósító metódus. Ekkor a
	 * kisrobot megsemmisül, és olajfoltot hagy maga mögött.	 * 
	 * @param Player A játékos, amellyel a kisrobot ütközik.
	 */
	@Override
	public void interact(Player player) {
		Cell cell = currentCell;

		OilStain oilStain = new OilStain();
		oilStain.setCell(cell);
		cell.setGameObject(oilStain);
		System.out.println(cell.toString());
		int newPosX = this.randomStatus ? this.rand.nextInt(map.getSize()[0])
				: this.initialPosition.getX();
		int newPosY = this.randomStatus ? this.rand.nextInt(map.getSize()[1])
				: this.initialPosition.getY();

		currentCell = map.getCell(newPosX, newPosY);
	}

	/**
	 * A kisrobot kör eleji tevékenységét megvalósító metódus. Ha a robotnak
	 * nincs kiválasztott foltja, akkor kiválaszt egy foltot, amelyet
	 * feltakaríthat. Itt történik meg az útkeresés és a lépés implementációja
	 * is.
	 */
	@Override
	public void onTurnStart() {

		if (destinationCell == null) {
			// Választunk új cellát
			List<Cell> destinationCells = map.getCellsWithStain();

			if (destinationCells.size() > 0) {
				destinationCell = destinationCells.get(0);

				for (Cell cell : destinationCells) {
					if (getDistance(cell) < getDistance(destinationCell)) {
						destinationCell = cell;
					}
				}
			}
		}
		if (destinationCell == null) {
			System.out.println("No stain on the map.");
			return;
		}
		if (destinationCell.equals(currentCell)) {
			destinationCell = null;
			System.out.println(currentCell.toString());

			return;
		}

		Cell cell = this.currentCell;

		// Ha az automatikus mozgás be van kapcsolva, akkor mozgunk
		if (this.randomStatus) { //
			// Útkeresés
			int nextPosX = 0;
			int nextPosY = 0;

			if (destinationCell.getX() != currentCell.getX()) {

				if (destinationCell.getX() > currentCell.getX()) {
					nextPosX = currentCell.getX() + 1;
				} else {
					nextPosX = currentCell.getX() - 1;
				}
				cell = map.getCell(nextPosX, currentCell.getY());

			} else if (destinationCell.getY() != currentCell.getY()) {

				if (destinationCell.getY() > currentCell.getY()) {
					nextPosY = currentCell.getY() + 1;
				} else {
					nextPosY = currentCell.getY() - 1;
				}
				cell = map.getCell(currentCell.getX(), nextPosY);
			}

			this.move(cell);
		}

	}

	/**
	 * A kisrobot mozgását megvalósító metódus. Ebben a metódusban történik a
	 * kisrobotok ütközésének, és a foltok feltakarításának az implementációja.	 * 
	 * @param cell A cella, amelyre a kisrobot rálép.
	 */
	public void move(Cell cell) {
		GameObject currentObj = cell.getGameObject();

		if ((cell.containsRobot() && !currentObj.equals(this))
				|| cell.getPlayer() != null) {
			// Ütközés!
			
			Cell nextCell = null;
			while((nextCell = map.getCell(currentCell.getX()
					+ (int) (Math.random() * 3) - 1, currentCell.getY()
					+ (int) (Math.random() * 3) - 1)).containsRobot()){
				
			}
			cell = nextCell;
			System.out
					.println(String
							.format("Hardworking-little-robot %d: Collision; New Position: Cell(%d, %d)",
									this.idx, this.currentCell.getX(),
									this.currentCell.getY()));
		}

		if (cell.containsStain()) {
			// Takarítás, csak a kiírás miatt...
			currentCell.setGameObject(null);
			System.out.println(currentCell.toString());
		}

		currentCell.setGameObject(null);
		currentCell = cell;
		currentCell.setGameObject(this);
	}

	/**
	 * Ez a metódus megadja a kisrobot távolságát a megadott cellától.
	 * 
	 * @param cell A cella, amelytől a kisrobot távolságát vizsgáljuk.
	 * @return A kisrobot távolsága a cellától.
	 */
	public int getDistance(Cell cell) {
		return Math.abs(this.currentCell.getX() - cell.getX())
				+ Math.abs(this.currentCell.getY() - cell.getY());
	}

	/**
	 * Kisrobot a kör végén nem csinál semmit.
	 */
	@Override
	public void onTurnEnd() {
	}

	/**
	 * Visszatér azzal, hogy "Hardworking-little-robot"
	 */
	@Override
	public String toString() {
		return "Hardworking-little-robot";
	}

	/**
	 * Beállítja a kisrobot pozicióját.
	 */
	@Override
	public void setCell(Cell cell) {
		this.currentCell = cell;
	}
}
