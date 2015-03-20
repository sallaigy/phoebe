package phoebe;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Cell[][] cells;
	
	public List<Cell> getNeighbours(int i, int j, int distance) {
	    ArrayList<Cell> results = new ArrayList<Cell>();

		return results;
	}
	
	public List<Cell> getNeighbours(Cell cell, int distance) {
		ArrayList<Cell> results = new ArrayList<Cell>();

		return results;
	}
	
	public Cell getCell(int i, int j) {
		return this.cells[0][0];
	}
	
}
