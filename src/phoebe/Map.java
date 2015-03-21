package phoebe;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Cell[][] cells;
	
    public Map(int n, int m) {
    	cells = new Cell[n][m];
    }
    
	public List<Cell> getNeighbours(int i, int j, int distance) {
	    ArrayList<Cell> results = new ArrayList<Cell>();

		return results;
	}
	
	public List<Cell> getNeighbours(Cell cell, int distance) {
		ArrayList<Cell> results = new ArrayList<Cell>();

		return results;
	}
	
	public Cell getCell(int i, int j) {
		return this.cells[i][j];
	}
	
	public void setCell(Cell cell) {
		cells[cell.getI()][cell.getJ()] = cell;
	}
	
}
