package phoebe;

import java.util.ArrayList;
import java.util.List;

/**
 * Itt tárolunk minden olyan információt, amely a térképpel kapcsolatos, azaz itt tároljuk a Cellekből álló kétdimenziós tömbünket is, illetve az adott pályára vonatkozó maximális játékidőt.
 *
 */
public class Map {

    private Cell[][] cells;
    
    /**
     * A Map osztály konstruktora
     * @param k A mátrix sorainak a száma
     * @param n A mátrix oszlopainak a száma
     */
    public Map(int k, int n) {
        Logger.methodEntry(this, Integer.toString(k), Integer.toString(n));
    	cells = new Cell[k][n];    	
    	Logger.methodExit(this);
    }
    
	/**
	 * Megadja egy adott cellának a szomszédait adott távolságban.
	 * @param i Cella sorkoordinátája
	 * @param j Cella oszlopkoordinátája
	 * @param distance A cellától való  távolság
	 * @return Egy lista az adott távolságban lévő cellákkal
	 */
	public List<Cell> getNeighbours(int i, int j, int distance) {
        Logger.methodEntry(this, Integer.toString(i), Integer.toString(j), Integer.toString(distance));
	    if (distance < 0 || distance > 2) {
	    	throw new IllegalArgumentException();
	    }
		List<Cell> results = new ArrayList<Cell>();
	    for (int k = -distance; k <= distance; ++k) {
	    	for (int l = -distance; l <= distance; ++l) {
	    		if (k == 0 || l == 0 || k == l || k + l == 0) {
	    		    results.add(cells[i + k][j + l]);
	    		}
	    	}
	    }		
	    results.remove(cells[i][j]);
	    
        Logger.methodExit(this);
		return results;
	}
	
	/**
	 * Megadja egy adott cellának a szomszédait adott távolságban.
	 * @param cell A cella, amelynek a szomszédait szeretnénk lekérni
	 * @param distance A cellától való távolság
	 * @return Egy lista az adott távolságban lévő cellákkal
	 */
	public List<Cell> getNeighbours(Cell cell, int distance) {
	    Logger.methodEntry(this, cell.toString(), Integer.toString(distance));

		List<Cell> results = new ArrayList<Cell>();
		results = getNeighbours(cell.getX(), cell.getY(), distance);
		
		Logger.methodExit(this);
		return results;
	}
	
	/**
	 * Térkép kirajzolása
	 * Cella printCell() metódusa visszaadja a megfelelő Stringet
	 */
	public void printMap() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				System.out.print(cells[i][j].printCell() + "\t");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Visszaadja a megadott koordinátákkal rendelkező cellát
	 * @param i Sorkoordináta
	 * @param j Oszlopkoordináta
	 * @return A megadott koordinátákkal rendelkező cella
	 */
	public Cell getCell(int i, int j) {
		return this.cells[i][j];
	}
	
	/**
	 * Beállítja az adott cellát.
	 * @param cell Erre a cellára változtatjuk az adott cellák.
	 */
	public void setCell(Cell cell) {
		cells[cell.getX()][cell.getY()] = cell;
	}
	
}