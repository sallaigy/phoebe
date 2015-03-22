package phoebe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

	private int turnCount = 0;
	
	private int maxTurns = 10;
	
	private List<Player> players = new ArrayList<Player>();
	
	private Map map;

	private int currentPlayerIdx;
	
	private boolean shouldQuit = false;
	
	private Random rand = new Random();
	
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
		    
		    if (this.currentPlayerIdx == this.players.size() - 1) {
		        this.currentPlayerIdx = 0;
		    } else {
		        this.currentPlayerIdx++;
		    }
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
	protected void loadMap() throws IOException {
		Logger.methodEntry(this);

		Map tempMap = new Map(25, 19);

		FileReader fileReader = new FileReader(new File("src/mapSource/map.txt"));
		BufferedReader buffReader = new BufferedReader(fileReader);

		String line = new String();
		int i = 0;
		while ((line = buffReader.readLine()) != null) {
		    String[] splitLine = line.split("\t");
            
            for (int j = 0; j < splitLine.length; j++) {                
                int cellProperty = Integer.parseInt(splitLine[j]);
                            
                switch(cellProperty) {
                    case 0:
                        tempMap.setCell(new Cell(i, j, CellType.CELL_INVALID, null, null));
                        break;
                    case 1:
                        tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, null));
                        break;
                    case 2:
                        tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, new OilStain()));
                        break;
                    case 3:
                        tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, new GlueStain()));
                        break;
                    case 4:
                        Cell cell = new Cell(i, j, CellType.CELL_VALID, players.get(0), null);
                        tempMap.setCell(cell);
                        players.get(0).setInitialPosition(cell);
                        players.get(0).setCurrentCell(cell);
                        break;
                    case 5: cell = new Cell(i, j, CellType.CELL_VALID, players.get(1), null);
                        tempMap.setCell(cell);
                        players.get(1).setInitialPosition(cell);
                        players.get(1).setCurrentCell(cell);
                        break;
                    default:
                        tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, null));
                        break;
                }
            }
            
            i++;
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
			if (currCell.getCellType() == CellType.CELL_INVALID || currCell.getX() < 0 || currCell.getY() < 0) {
				System.out.println(player + " lost the game.");
				System.exit(0);
			}
		}
		
		if (turnCount == maxTurns) {
			if (players.get(0).getDistance() > players.get(1).getDistance()) {
				System.out.println("Player1 won the game.");
			} else {
			    System.out.println("Player2 won the game.");
			}
		}
		
		System.out.println("Újraindítja a játékot?(Y/N)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
            String s = br.readLine();
            if (s.equals("Y")) {
                reset();
            }
        } catch (IOException e) {
            
            e.printStackTrace();
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
        
        Cell nextCell = new Cell();
        int y = 0, x = 0, distance = 0;
        
        if (!current.isCanChangeDirection()) { // Nem léphet
            int index = this.rand.nextInt(neighbours.size());
            Cell cell = neighbours.get(index);
            
            System.out.println(String.format("Olajfolt, az új pozíciód: (%d, %d)", cell.getX(), cell.getY()));
            
            current.move(cell);
            
            return;
        }
       
        y = current.getCurrentCell().getY();
        x = current.getCurrentCell().getX();
        distance = current.getSpeed();
        
        while (!next) {
            try {
                System.out.println(String.format("%d: Jelenleg a (%d, %d) cellán állsz. Sebességed: %d", current.getIdx(), x, y, current.getSpeed()));
                System.out.println("Hova lép? (W/NW/N/NE/E/SE/S/SW)");
                res = reader.readLine();
                
                if (res.equals("W")) {
                    y += distance;
                } else if (res.equals("NW")) {
                    y += distance;
                    x -= distance;
                } else if (res.equals("N")) {
                    x -= distance;
                } else if (res.equals("NE")) {
                    y -= distance;
                    x -= distance;
                } else if (res.equals("E")) {
                    y -= distance;
                } else if (res.equals("SE")) {
                    y -= distance;
                    x += distance;
                } else if (res.equals("S")) {
                    x += distance;
                } else if (res.equals("SW")) {
                    y += distance;
                    x += distance;
                } else {
                    System.out.println("Érvénytelen irány");
                }                
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            
            for (Cell cell : neighbours) {
                if (cell.getX() == x && cell.getY() == y && cell.getPlayer() == null) {
                    next = true;
                    nextCell = cell;
                }
            }
       }
        
        System.out.println(String.format("Az új pozíció: (%d, %d) lesz.", nextCell.getX(), nextCell.getY()));
        
        next = false;
        
        while (!next) {
            try {
                System.out.println("Mit rak le? (O/G/N)");
                res = reader.readLine();
                
                next = true;
                if (res.equals("O")) {
                    this.players.get(this.currentPlayerIdx).putStain(OilStain.class.getName());
                } else if (res.equals("G")) {
                    this.players.get(this.currentPlayerIdx).putStain(GlueStain.class.getName());                    
                } else if (res.equals("N")) {
                    // nope
                } else {
                    System.out.println("Érvénytelen folt");
                    next = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                next = false;
            }
        }
        
        current.move(nextCell);
        
        Logger.methodExit(this);
	}

	public void setTurnCount(int turnCount) {
		Logger.methodEntry(this);
		this.turnCount = turnCount;
		Logger.methodExit(this);
	}


}
