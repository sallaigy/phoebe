package phoebe;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékteret megtestesítő osztály. 
 * Itt tárolunk minden olyan információt, amely a térképpel kapcsolatos, 
 * azaz itt tároljuk a Cellekből álló kétdimenziós tömbünket is, illetve 
 * az adott pályára vonatkozó maximális játékidőt.
 * Innen kérhetők le egy-egy cella szomszédai, és azok a cellák, amelyeken van folt.
 */
public class Map {

	private Cell[][] cells;

	/**
	 * A Map osztály konstruktora
	 * @param k A mátrix sorainak a száma
	 * @param n A mátrix oszlopainak a száma
	 */
	public Map(int k, int n) {
		cells = new Cell[k][n];    	
	}

	/**
	 * Megadja egy adott cellának a szomszédait adott távolságban.
	 * @param i Cella sorkoordinátája
	 * @param j Cella oszlopkoordinátája
	 * @param distance A cellától való  távolság
	 * @return Egy lista az adott távolságban lévő cellákkal
	 */
	public List<Cell> getNeighbours(int i, int j, int distance) {
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

		return results;
	}

	/**
	 * Megadja egy adott cellának a szomszédait adott távolságban.
	 * @param cell A cella, amelynek a szomszédait szeretnénk lekérni
	 * @param distance A cellától való távolság
	 * @return Egy lista az adott távolságban lévő cellákkal
	 */
	public List<Cell> getNeighbours(Cell cell, int distance) {

		List<Cell> results = new ArrayList<Cell>();
		results = getNeighbours(cell.getX(), cell.getY(), distance);

		return results;
	}

	/**
	 * Térkép kirajzolása
	 * Cella printCell() metódusa visszaadja a megfelelő Stringet
	 */
	public void printMap() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				System.out.print(cells[i][j].printCell() + ' ');
			}
			System.out.print("\n");
		}
	}

	/**
	 * Pályán található olyan cellák visszaadása egy listában, amelyeken folt van
	 * 
	 * @return: Egy lista a foltot tartalmazó cellákról
	 */
	public List<Cell> getCellsWithStain() {

		List<Cell> result = new ArrayList<Cell>();
		for (int i = 0; i < cells.length; i++) {

			for (int j = 0; j < cells[i].length; j++) {

				if (cells[i][j].getGameObject() !=  null) {
					if (!cells[i][j].getGameObject().toString().equals("Hardworking-little-robot")) {
						result.add(cells[i][j]);
					}
				}
			}
		}
		return result;
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

	public int[] getSize() {
		int[] result = {cells.length, cells[0].length};
		return result;
	}

}