package phoebe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A rendszer központi osztálya. Nyilvántartja a játékosokat, 
 * a térképet,  és a további játékelemeket. Ő felelős a 
 * játék vezérléséért (játék indítás, újraindítás, kilépés). 
 * Továbbá felelős a játék mechanikájáért, tehát vezérli a körök 
 * lejátszását (kör indítása, befejezése, input kezelése).
 */
public class Game {

	public BufferedReader reader;

	private int turnCount = 0;

	private int maxTurns = 100;

	private List<Player> players = new ArrayList<Player>();

	private List<GameObject> stains = new ArrayList<GameObject>();

	private List<Robot> robots = new ArrayList<Robot>();

	private Map map;

	private int currentPlayerIdx;

	private boolean shouldQuit = false;

	private Random rand = new Random();

	private String mapFile;

	private boolean oilRandom = true;

	/**
	 * Jelzi a játék indulását, hatására felépül a pálya és megkezdődik az első kör.
	 * A játék ebben az állapotban marad újraindításig és kilépésig.
	 */

	public void start(String map) throws GameException {
		this.mapFile = map;

		// initialize the players
		Player player0 = new Player(0);
		Player player1 = new Player(1);

		this.players.add(player0);
		this.players.add(player1);

		// load the map
		try {
			this.loadMap();
		} catch (IOException e) {
			throw new GameException();
		}
		
		//Robotok létrehozása
		Robot robot0 = new Robot(this.map, this.map.getCell(0 + 2, 0 + 2), 0);
		Robot robot1 = new Robot(this.map, this.map.getCell(1 + 2, 1 + 2), 1);
		
		this.robots.add(robot0);
		this.robots.add(robot1);
		
		this.currentPlayerIdx = 0;

		//Ameddig nem kéne kilépni
		while (!this.shouldQuit) {
			//Kör kezdés
			this.beginTurn();
			//Input kezelés
			this.handleInput();
			//Kör vége
			this.endTurn();
			
			//Kiválasztja a következő játékost
			//Legelső játékos
			if (this.currentPlayerIdx == this.players.size() - 1) {
				this.currentPlayerIdx = 0;
			} 
			//Következő játékos
			else {
				this.currentPlayerIdx++;
			}
		}
	}
	/**
	 * Újraindítja az aktuális játékot.
	 * Minden játékos visszakerül a kezdőpozícióba, foltkészleteik feltöltődnek.
	 * A turnCount nulla értéket kap.
	 */
	public void reset() {		
		//Körök száma 0-ba állítása
		setTurnCount(0);
		
		//Pálya betöltése
		try {
			this.loadMap();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Játékosok resetelése
		for (Player player : players) {
			player.reset();
		}
		
		//Aktuális játékos a legelső
		this.currentPlayerIdx = 0;
	}

	/**
	 * Befejezi a játékot és kilép a programból.
	 */
	public void quit() {
        this.shouldQuit = true;
    }

	/**
	 * Ez a metódus tölti be a térképet.
	 * Létrehoz egy Map objektumot, majd betölti a pályát a map.txt fájlból.
	 * Az egyes számok egyes cellatípusoknak felelnek meg.
	 * @throws IOException
	 */
	protected void loadMap() throws IOException {
		//Létrehoz egy 25 sorból és 19 oszlopból álló pályát
		Map tempMap = new Map(25, 19);

		//Beolvassa a pályát
		FileReader fileReader = new FileReader(new File(this.mapFile));
		BufferedReader buffReader = new BufferedReader(fileReader);

		String line = new String();
		int i = 0;
		while ((line = buffReader.readLine()) != null) {
			String[] splitLine = line.split("\t");

			for (int j = 0; j < splitLine.length; j++) {                
				int cellProperty = Integer.parseInt(splitLine[j]);

				switch(cellProperty) {
				//0 - Invalid cella
				case 0:
					tempMap.setCell(new Cell(i, j, CellType.CELL_INVALID, null, null));
					break;
				//1 - Valid cella, ami üres	
				case 1:
					tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, null));
					break;
				//2 - Valid cella, olajfolttal	
				case 2:
					tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, new OilStain()));
					break;
				//3 - Valid cella, ragacsfolttal
				case 3:
					tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, new GlueStain()));
					break;
				//4 - Valid cella, első játékos	
				case 4:
					Cell cell = new Cell(i, j, CellType.CELL_VALID, players.get(0), null);
					tempMap.setCell(cell);
					players.get(0).setInitialPosition(cell);
					players.get(0).setCurrentCell(cell);
					break;
				//5 - Valid cella, második játékos	
				case 5: cell = new Cell(i, j, CellType.CELL_VALID, players.get(1), null);
					tempMap.setCell(cell);
					players.get(1).setInitialPosition(cell);
					players.get(1).setCurrentCell(cell);
					break;
				//Alapértelmezett: Valid, üres cella.	
				default:
					tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null, null));
					break;
				}
			}

			i++;
		}
		buffReader.close();
		map = tempMap;
	}

	/**
	 * Jelzi a kör végét az összes feliratkozott játékosnak.
	 * Tehát meghívja a játékosok onTurnEnd() metódusát.
	 */
	public void endTurn() {
		for (Player player : players) {
			player.onTurnEnd();

			//Megvizsgálja, hogy vége-e a játéknak.
			//És ha igen, akkor ki nyert.
			Cell currCell = player.getCurrentCell();
			if (currCell.getCellType() == CellType.CELL_INVALID || currCell.getX() < 0 || currCell.getY() < 0) {
				if (player == players.get(0)) 
					printOutcome(players.get(1).getClass().getSimpleName() + " " + players.get(1).getIdx());
				else printOutcome(players.get(0).getClass().getSimpleName() + " " + players.get(0).getIdx());
				this.quit();
			}
		}

		//Ha letelt a játékidő, győztest hirdet.
		if (turnCount == maxTurns) {
			Player winner = null;
			//Első játékos nyert
			if (players.get(0).getDistance() > players.get(1).getDistance()) {
				winner = players.get(0);
			} 
			//Második játékos nyert
			else {
				winner = players.get(1);  
			}
			//Döntetlen
			if(winner == null)
				printOutcome("Draw");
			//Győztes kiírása
			else {
				printOutcome(winner.getClass().getSimpleName() + " "+ winner.getIdx());
			}
		}
	}

	/**
	 * Jelzi a kör elejét az összes feliratkozott játékosnak.
	 * Tehát meghívja a játékosok onTurnStart() metódusát.
	 */
	public void beginTurn() {
	    this.turnCount++;
	    
		for (Player player : players) {
			player.onTurnStart();
			
		}
		for (Robot robot : robots) {
			robot.onTurnStart();
		}
		proccessGameObjectsFromMap();
		for (GameObject gameObject : stains) {
			gameObject.onTurnStart();
		}
	}

	 public void handleInput() {
	        Player current = this.players.get(currentPlayerIdx);
	        
	        System.out.println(current.toString());

	        String line;

	        Cell nextCell = null;

	        boolean next = false;

	        while (!next) {
	            try {
	            	//Tesztben kapott parancsok kezelése
	                if (null != (line = this.reader.readLine())) {
	                    String[] input = line.split(" ");
	                    String cmd = input[0];

	                    //Újraindítás
	                    if (cmd.equals("reset")) {
	                        this.reset();
	                        next = true;
	                    } 
	                    //Kilépés
	                    else if (cmd.equals("quit")) {
	                        this.quit();
	                        next = true;
	                    } 
	                    //Pálya mutatása
	                    else if (cmd.equals("showmap")) {
	                        map.printMap();
	                    } 
	                    //Mozgás
	                    else if (cmd.equals("move")) {
	                        if (nextCell == null) {
	                            // Ha még nem léptünk
	                            List<Cell> neighbours = this.map.getNeighbours(
	                                    current.getCurrentCell(),
	                                    current.getSpeed());
	                            int x = current.getCurrentCell().getX(), y = current
	                                    .getCurrentCell().getY(), distance = current.getSpeed();

	                            if (input.length >= 2) {
	                                String res = input[1];
	                                //Balra
	                                if (res.equals("W")) {
	                                    y -= distance;
	                                }
	                                //Balra-fel
	                                else if (res.equals("NW")) {
	                                    y -= distance;
	                                    x -= distance;
	                                } 
	                                //Fel
	                                else if (res.equals("N")) {
	                                    x -= distance;
	                                } 
	                                //Jobbra-fel
	                                else if (res.equals("NE")) {
	                                    y += distance;
	                                    x -= distance;
	                                } 
	                                //Jobbra
	                                else if (res.equals("E")) {
	                                    y += distance;
	                                } 
	                                //Jobbra-le
	                                else if (res.equals("SE")) {
	                                    y += distance;
	                                    x += distance;
	                                } 
	                                //Le
	                                else if (res.equals("S")) {
	                                    x += distance;
	                                } 
	                                //Balra-le
	                                else if (res.equals("SW")) {
	                                    y -= distance;
	                                    x += distance;
	                                } 
	                                //Érvénytelen irány
	                                else {
	                                    System.out.println("Invalid direction");
	                                }

	                                for (Cell cell : neighbours) {
	                                    if (cell.getX() == x && cell.getY() == y) {
	                                        nextCell = cell;
	                                        System.out
	                                                .println(String
	                                                        .format("%s %d: New Position: Cell(%d, %d)",
	                                                                current.getClass()
	                                                                        .getSimpleName(),
	                                                                current.getIdx(),
	                                                                nextCell.getX(),
	                                                                nextCell.getY()));

	                                        break;
	                                    }
	                                }
	                            }
	                        } 
	                        //Ha többször akarna mozogni egy körben
	                        else {
	                            System.out
	                                    .println("Only one move is allowed per turn.");
	                        }

	                    } 
	                    //Folt lerakása
	                    else if (cmd.equals("put-stain")) {

	                        //Ragacs
	                    	if (input[1].equals("G")) {
	                            current.putStain(GlueStain.class.getName());
	                        } 
	                    	//Olaj
	                    	else if (input[1].equals("O")) {
	                            current.putStain(OilStain.class.getName());
	                        } 
	                    	//Érvénytelen folt
	                    	else {
	                            System.out.println("Invalid stain");
	                        }

	                    } 
	                    //Kör vége
	                    else if (cmd.equals("end-turn")) {
	                        if (nextCell != null) {
	                            if (nextCell.getPlayer() != null) {
	                                this.printOutcome(current.getClass().getSimpleName() + " " + current.getIdx());
	                                this.quit();
	                            }
	                            current.move(nextCell);
	                            
	                        }
	                        next = true;

	                    } 
	                    //Olajfolt hatás
	                    else if (cmd.equals("oil-random")) {
	                        if (input.length >= 2) {
	                            String setting = input[1];

	                            //Random engedélyezése
	                            if (setting.equals("on")) {
	                                this.oilRandom = true;
	                            } 
	                            //Random tiltása
	                            else if (setting.equals("off")) {
	                                this.oilRandom = false;
	                            } 
	                            //Érvénytelen beállítás
	                            else {
	                                System.out.println("Invalid setting.");
	                            }
	                        } 	                        
	                        //Kisrobot használata
	                        else {
	                            System.out
	                                    .println("USAGE: hardworking-little-robot-random <setting>");
	                        }
	                    } 
	                    //Kisrobot
	                    else if (cmd.equals("hardworking-little-robot-random")) {
	                        if (input.length >= 2) {
	                            String setting = input[1];

	                            //Random engedélyezése
	                            if (setting.equals("on")) {
	                                Robot.randomStatus = true;
	                            } 
	                            //Random tiltása
	                            else if (setting.equals("off")) {
	                                Robot.randomStatus = false;
	                            } 
	                            //Érvénytelen beállítás
	                            else {
	                                System.out.println("Invalid setting.");
	                            }
	                        } 
	                        //Kisrobot használata
	                        else {
	                            System.out
	                                    .println("USAGE: hardworking-little-robot-random <setting>");
	                        }
	                    } 
	                    //Robot mozgatása
	                    else if (cmd.equals("robot-move")) {

	                    	//Nem megfelelő bemenet
	                    	if (input.length < 4) {
	    						System.out.println("Invalid command, not enough arguments.");
	    					} else {
	    						int id = Integer.parseInt(input[1]);
	    						int row = Integer.parseInt(input[2]);
	    						int col = Integer.parseInt(input[3]);

	    						//Nem megfelelő sor paraméter
	    						if (row > map.getSize()[0] || row < 0) {
	    							System.out.println("Invalid <row> parameter");
	    						} 
	    						//Nem megfelelő oszlop paraméter
	    						else if (col > map.getSize()[1] || col < 0) {
	    							System.out.println("Invalid <col> parameter");
	    						} else {
	    							for (Robot robot: robots) {
	    								if (robot.idx == id) {
	    									//robot.setCell(map.getCell(row, col));
                                            System.out.println(String.format("Hardworking-little-robot %d: New Position: Cell(%d, %d)", 
                                                    robot.idx, row, col));
	    								    robot.move(map.getCell(row, col));
	    								}
	    							}
	    						}
	                        }
	                    } 
	                    //Megadható ezzel, hogy hányadik körnél tartunk
	                    else if (cmd.equals("set-clock")) {
	                        if (input.length >= 2) {
	                            try {
	                                int tc = Integer.parseInt(input[1]);
	                                this.turnCount = tc;
	                            } catch (NumberFormatException e) {
	                                System.out.println(e.getMessage());
	                            }
	                        } 
	                        //Set-clock használata
	                        else {
	                            System.out.println("USAGE: set-clock <T>");
	                        }
	                    } 
	                    //Ismeretlen parancs
	                    else {
	                        System.out.println("Unknown command.");
	                    }

	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                next = false;
	            }
	        }
	    }
	/**
	 * Beállítja az eltelt körök számát.
	 * @param turnCount Az érték, amelyre be szeretnénk állítani az eltelt körök számát.
	 */
	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	/**
	 * Kiírja a kimenetre, hogy
	 * az első játékos
	 * milyen messze jutott;
	 * a második játékos
	 * milyen messze jutott.
	 * Győztes:
	 * melyik játékos.
	 * @param outcome: Játék kimenetele
	 */
	public void printOutcome(String outcome) {
		String output = String.format("%s %d: Distance - %d; %s %d: Distance - %d; Winner: %s",
				players.get(0).getClass().getSimpleName(), players.get(0).getIdx(),
				players.get(0).getDistance(),
				players.get(1).getClass().getSimpleName(), players.get(1).getIdx(),
				players.get(1).getDistance(),
				outcome);
		System.out.println(output);
	}
	
	/**
	 * Kimenti, hogy milyen GameObjectek vannak a cellákon.
	 */
	public void proccessGameObjectsFromMap() {
		List<GameObject> temp = new ArrayList<GameObject>();
		List<Cell> cells = map.getCellsWithStain();
		for (Cell cell : cells) {
			temp.add(cell.getGameObject());
		}
		stains = temp;
	}

}
