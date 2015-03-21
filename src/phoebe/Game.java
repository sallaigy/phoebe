package phoebe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Game {

	private int turnCount;

	private int maxTurns;

	private List<Player> players;

	private Map map;

	public void start() throws IOException {
		Logger.methodEntry(this);
		Logger.methodExit(this);
	}

	public void reset() {
		Logger.methodEntry(this);

		Logger.methodExit(this);		
	}

	public void quit() {
		Logger.methodEntry(this);
		Logger.methodExit(this);
		System.exit(0);
	}

	public void loadMap() throws IOException {
		Logger.methodEntry(this);

		Map tempMap = new Map(24, 19);
		//24 * 19
		FileReader fileReader = new FileReader(new File("src/mapSource/map.txt"));
		BufferedReader buffReader = new BufferedReader(fileReader);

		String line = new String();
		for(int i = 0; i<24; i++) {
			if ((line = buffReader.readLine()) != null) {
				String[] splitLine = line.split("\t");
				for(int j = 0; j<splitLine.length; j++) {
					
					int cellProperty = Integer.parseInt(splitLine[j]);
								
					switch(cellProperty) {
						case 0: tempMap.setCell(new Cell(i, j, CellType.CELL_INVALID)); break;
						case 1: tempMap.setCell(new Cell(i, j, CellType.CELL_VALID)); break;
						case 2: tempMap.setCell(new Cell(i, j, new OilStain())); break;
						case 3: tempMap.setCell(new Cell(i, j, new GlueStain())); break;
						case 4: tempMap.setCell(new Cell(i, j, new Player())); break;
						case 5: tempMap.setCell(new Cell(i, j, new Player())); break;
					default: tempMap.setCell(new Cell(i, j, CellType.CELL_VALID));
					
					}
				}
			}
		}

		buffReader.close();
		map = tempMap;
		Logger.methodExit(this);		
	}

	public void endTurn() {
		Logger.methodEntry(this);

		for (Player player : players) {
			Cell currCell = player.getCurrentCell();
			if(currCell.getCellType() == CellType.CELL_INVALID) {
				System.out.println("veszitel");
			}
		}

		if(turnCount == maxTurns) {
			for (Player player : players) {
				player.getDistance();
			}
		}

		Logger.methodExit(this);        
	}

	public void beginTurn() {
		Logger.methodEntry(this);

		Logger.methodExit(this);
	}

	public void handleInput() {
		Logger.methodEntry(this);

		Logger.methodExit(this);		
	}


}
