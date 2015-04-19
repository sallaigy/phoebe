package phoebe;

import java.util.List;
import java.util.Random;

public class Robot implements GameObject {

	private Cell currentCell = new Cell();
	private Cell destinationCell = null;
	private Map map;
	public int idx;

	public Robot(Map map, Cell initialPosition, int id) {
		this.idx = id;
		this.map = map;
		this.currentCell = initialPosition;
		this.currentCell.setGameObject(this);
	}

	@Override
	public void interact(Player player) {
		Cell cell = currentCell;

		OilStain oilStain = new OilStain();
		oilStain.setCell(cell);
		cell.setGameObject(oilStain);

		int newPosX = (new Random()).nextInt(map.getSize()[0]);
		int newPosY = (new Random()).nextInt(map.getSize()[1]);

		currentCell = map.getCell(newPosX, newPosY);
	}

	@Override
	public void onTurnStart() {

		if (destinationCell == null) {

			List<Cell> destinationCells = map.getCellsWithStain();

			if (destinationCells.size() > 0) {
				destinationCell = destinationCells.get(0);

				for (Cell cell : destinationCells) {
					if (getDistance(cell) < getDistance(destinationCell)) {
						destinationCell = cell;
					}
				}
			}

		} else {
			int nextPosX = 0;
			int nextPosY = 0;
			Cell cell = new Cell();

			if (destinationCell.equals(currentCell)) {
				destinationCell = null;
				return;
			}

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
			
			if (cell.getGameObject()!=null && cell.getGameObject().toString().equals("Robot")) {
				List<Cell> neighbours = map.getNeighbours(currentCell, 1);
				currentCell.setGameObject(null);
				currentCell = neighbours.get((new Random()).nextInt(neighbours.size()));
			} else {
				currentCell.setGameObject(null);
				currentCell = cell;
				currentCell.setGameObject(this);
			}
		}

	}

	public int getDistance(Cell cell) {
		return 
				Math.abs(this.currentCell.getX() - cell.getX())
				+
				Math.abs(this.currentCell.getY() - cell.getY());
	}

	@Override
	public void onTurnEnd() { }

	@Override
	public String toString() {
		return "Robot";
	}

	@Override
	public void setCell(Cell cell) {
		this.currentCell = cell;
	}
}
