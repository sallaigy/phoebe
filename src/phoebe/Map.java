package phoebe;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Cell[][] cells;
    
    /**
     * A Map osztály konstruktora
     * @param k A mátrix sorainak a száma
     * @param n A mátrix oszlopainak a száma
     */
    public Map(int k, int n) {
    	for (int i = 0; i < k; ++i) {
    		for (int j = 0; j < n; ++j) {
    			cells[i][j] = new Cell(i, j, CellType.CELL_VALID, null, null);
    		}
    	}
    }
    
	/**
	 * Megadja egy adott cellának a szomszédait adott távolságban.
	 * @param i Cella sorkoordinátája
	 * @param j Cella oszlopkoordinátája
	 * @param distance A cellától való távolság
	 * @return Egy lista az adott távolságban lévõ cellákkal
	 */
	public List<Cell> getNeighbours(int i, int j, int distance) {
	    if (distance < 0 || distance > 2) {
	    	throw new IllegalArgumentException();
	    }
		List<Cell> results = new ArrayList<Cell>();
	    for (int k = -distance; k <= distance; ++k) {
	    	for (int l = -distance; l <= distance; ++l) {
	    		if (k == 0 || l == 0 || k == l || k + l == 0) 
	    		results.add(cells[i + k][j + l]);
	    	}
	    }		
	    results.remove(cells[i][j]);
		return results;
	}
	
	/**
	 * Megadja egy adott cellának a szomszédait adott távolságban.
	 * @param cell A cella, amelynek a szomszédait szeretnénk lekérni
	 * @param distance A cellától való távolság
	 * @return Egy lista az adott távolságban lévõ cellákkal
	 */
	public List<Cell> getNeighbours(Cell cell, int distance) {
		List<Cell> results = new ArrayList<Cell>();
		results = getNeighbours(cell.getI(), cell.getJ(), distance);
		return results;
	}
	
	/**
	 * Visszaadja a megadott koordinátákkal rendelkezõ cellát
	 * @param i Sorkoordináta
	 * @param j Oszlopkoordináta
	 * @return A megadott koordinátákkal rendelkezõ cella
	 */
	public Cell getCell(int i, int j) {
		return this.cells[i][j];
	}
	
}
