package phoebe;

import java.util.List;

public class Robot implements GameObject {

	private Cell currentCell = new Cell();
	private Cell destinationCell = null;
	private Map map;

	public Robot(Map map, Cell initialPosition) {
		this.map = map;
		this.currentCell = initialPosition;
	}

	@Override
	public void interact(Player player) {
		
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
				currentCell = map.getCell(nextPosX, currentCell.getY());
				currentCell.setGameObject(this);
				
			} else if (destinationCell.getY() != currentCell.getY()) {
				
				if (destinationCell.getY() > currentCell.getY()) {
					nextPosY = currentCell.getY() + 1;
				} else {
					nextPosY = currentCell.getY() - 1;
				}
				currentCell = map.getCell(currentCell.getX(), nextPosY);
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
	public void onTurnEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Robot";
	}
}
