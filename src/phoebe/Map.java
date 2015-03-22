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
        Logger.methodEntry(this, Integer.toString(k), Integer.toString(n));
    	cells = new Cell[k][n];    	
    	Logger.methodExit(this);
    }
    
	/**
	 * Megadja egy adott cellának a szomszédait adott távolságban.
	 * @param i Cella sorkoordinátája
	 * @param j Cella oszlopkoordinátája
	 * @param distance A cell�t�l val� t�vols�g
	 * @return Egy lista az adott t�vols�gban l�v� cell�kkal
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
	 * Megadja egy adott cell�nak a szomsz�dait adott t�vols�gban.
	 * @param cell A cella, amelynek a szomsz�dait szeretn�nk lek�rni
	 * @param distance A cell�t�l val� t�vols�g
	 * @return Egy lista az adott t�vols�gban l�v� cell�kkal
	 */
	public List<Cell> getNeighbours(Cell cell, int distance) {
	    Logger.methodEntry(this, cell.toString(), Integer.toString(distance));

		List<Cell> results = new ArrayList<Cell>();
		results = getNeighbours(cell.getX(), cell.getY(), distance);
		
		Logger.methodExit(this);
		return results;
	}
	
	/**
	 * Visszaadja a megadott koordin�t�kkal rendelkez� cell�t
	 * @param i Sorkoordin�ta
	 * @param j Oszlopkoordin�ta
	 * @return A megadott koordin�t�kkal rendelkez� cella
	 */
	public Cell getCell(int i, int j) {
		return this.cells[i][j];
	}
	
	public void setCell(Cell cell) {
		cells[cell.getX()][cell.getY()] = cell;
	}
	
}