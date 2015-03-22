package phoebe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game {

	private int turnCount = 0;
	
	private int maxTurns = 10;
	
	private List<Player> players = new ArrayList<Player>();
	
	private Map map;

	private int currentPlayerIdx;
	
	private boolean shouldQuit = false;
	
	public void start() {
		Logger.methodEntry(this);
		
		// initialize the players
		Player player0 = new Player(0);
		Player player1 = new Player(1);

		this.players.add(player0);
		this.players.add(player1);
		
        // load the map
		try {
		    this.loadMap();
		} catch (IOException e) {
		    e.printStackTrace();
		}
        
		this.currentPlayerIdx = 0;
		
		while (!this.shouldQuit) {
		    this.beginTurn();
		    this.handleInput();
		    this.endTurn();
		}
		
		Logger.methodExit(this);
	}
	/**
	 * - Start again the game
	 * 		-- setting the turns count to 0
	 * 		-- reloading the map
	 * 		-- reset player attributes.
	 */
	public void reset() {
		Logger.methodEntry(this);
		
		setTurnCount(0);
		try {
			this.loadMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Player player : players) {
			player.reset();
		}
		
		Logger.methodExit(this);		
	}

	/**
	 * - Close the game
	 */
	public void quit() {
		Logger.methodEntry(this);
		Logger.methodExit(this);
		System.exit(0);
	}

	/**
	 * - Loading the map from file
	 * - Based on the single string values(0,1,2,etc..), we create the corresponding cells
	 * - Size of the map is hard coded
	 * @throws IOException
	 */
	public void loadMap() throws IOException {
		Logger.methodEntry(this);

		Map tempMap = new Map(24, 19);

		FileReader fileReader = new FileReader(new File("src/mapSource/map.txt"));
		BufferedReader buffReader = new BufferedReader(fileReader);

		String line = new String();
		for(int i = 0; i<24; i++) {
			if ((line = buffReader.readLine()) != null) {
				
				String[] splitLine = line.split("\t");
				
				for(int j = 0; j<splitLine.length; j++) {
					
					int cellProperty = Integer.parseInt(splitLine[j]);
								
					switch(cellProperty) {
						case 0: tempMap.setCell(new Cell(i, j, CellType.CELL_INVALID, null, null)); break;
						case 1: tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, null)); break;
						case 2: tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, new OilStain())); break;
						case 3: tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, new GlueStain())); break;
						case 4: Cell cell = new Cell(i, j, CellType.CELL_VALID, players.get(0), null);
								tempMap.setCell(cell);
								players.get(0).setInitialPosition(cell);
								break;
						case 5: cell = new Cell(i, j, CellType.CELL_VALID, players.get(1), null);
								tempMap.setCell(cell);
								players.get(1).setInitialPosition(cell);
								break;
					default: tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, null));
					
					}
				}
			}
		}

		buffReader.close();
		map = tempMap;
		Logger.methodExit(this);
	}

	/**
	 * - Calling players' onTurnEnd() method
	 * - Check if they're on an invalid cell
	 * - Check if the turns reached maxTurns
	 * 		-- yes: get the distances, and declare a winner
	 */
	public void endTurn() {
		Logger.methodEntry(this);

		for (Player player : players) {
			
			player.onTurnEnd();
			
			Cell currCell = player.getCurrentCell();
			if(currCell.getCellType() == CellType.CELL_INVALID) {
				System.out.println(player + " lost the game.");
			}
		}
		
		if(turnCount == maxTurns) {
			if(players.get(0).getDistance() > players.get(1).getDistance()) {
				System.out.println("Player1 won the game.");
			} else System.out.println("Player2 won the game.");
		}
		
		Logger.methodExit(this);        
	}

	/**
	 * - Calling players' onTurnStart() method
	 * - Increment number of turns 
	 */
	public void beginTurn() {
		Logger.methodEntry(this);

		for (Player player : players) {
			player.onTurnStart();
		}
		
		turnCount++;
		
		Logger.methodExit(this);
	}

	public void handleInput() {

        Logger.methodEntry(this);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       
        boolean next = false;
        String res;        

        Player current = this.players.get(this.currentPlayerIdx);
        
        List<Cell> neighbours = this.map.getNeighbours(current.getCurrentCell(), current.getSpeed());
        
        int i = 0, j = 0, distance = 0;
        
        i = current.getCurrentCell().getI();
        j = current.getCurrentCell().getJ();
        distance = current.getSpeed();
        
        while (!next) {            
            try {
                System.out.println("Hova lép? (W/NW/N/NE/E/SE/S/SW)");
                res = reader.readLine();
                next = true;
                
                if (res.equals("W")) {
                    i -= distance;
                } else if (res.equals("NW")) {
                    i -= distance;
                    j += distance;
                } else if (res.equals("N")) {
                    j += distance;
                } else if (res.equals("NE")) {
                    i += distance;
                    j += distance;
                } else if (res.equals("E")) {
                    i += distance;
                } else if (res.equals("SE")) {
                    i += distance;
                    j -= distance;
                } else if (res.equals("S")) {
                    j -= distance;
                } else if (res.equals("SW")) {
                    i -= distance;
                    j -= distance;
                } else {
                    System.out.println("Érvénytelen irány");
                }                
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            
            for (Cell cell : neighbours) {
                if (cell.getI() == i && cell.getJ() == j && cell.getPlayer() == null) {
                    current.move(cell);
                    next = true;
                }
            }

        }
        
        next = false;
        
        while (!next) {
            try {
                System.out.println("Mit rak le? (O/G/N)");
                res = reader.readLine();
                
                next = true;
                if (res.equals("O")) {
                    this.players.get(this.currentPlayerIdx).putStain("O");
                } else if (res.equals("G")) {
                    this.players.get(this.currentPlayerIdx).putStain("G");                    
                } else if (res.equals("N")) {
                    this.players.get(this.currentPlayerIdx).putStain("N");                    
                } else {
                    System.out.println("Érvénytelen folt");
                    next = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                next = false;
            }
        }
        
        Logger.methodExit(this);
	}

	public void setTurnCount(int turnCount) {
		Logger.methodEntry(this);
		this.turnCount = turnCount;
		Logger.methodExit(this);
	}


}
