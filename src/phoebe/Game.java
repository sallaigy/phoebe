package phoebe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A játék logikáját tartalmazó osztály. Itt tartjuk számon a játékosokat, a
 * térképet, itt kezeljük a kapott bemeneteket.
 */
public class Game {

    public BufferedReader reader;
    
    private int turnCount = 0;

    private int maxTurns = 10;

    private List<Player> players = new ArrayList<Player>();

    private List<Stain> stains = new ArrayList<Stain>();

    private List<Robot> robots = new ArrayList<Robot>();

    private Map map;

    private int currentPlayerIdx;

    private boolean shouldQuit = false;

    private Random rand = new Random();

    private String mapFile;

    private boolean oilRandom = true;

    private boolean robotRandom = true;

    /**
     * Jelzi a játék indulását, hatására felépül a pálya és megkezdődik az első
     * kör. A játék ebben az állapotban marad újraindításig és kilépésig.
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
        Cell randomCell = this.map.getCell(rand.nextInt(this.map.getSize()[0]),
                rand.nextInt(this.map.getSize()[1]));
        Robot robot0 = new Robot(this.map, randomCell, 0);
        randomCell = this.map.getCell(rand.nextInt(this.map.getSize()[0]),
                rand.nextInt(this.map.getSize()[1]));
        Robot robot1 = new Robot(this.map, randomCell, 1);

        this.robots.add(robot0);
        this.robots.add(robot1);

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
    }

    /**
     * Újraindítja az aktuális játékot. Minden játékos visszakerül a
     * kezdőpozícióba, foltkészleteik feltöltődnek. A turnCount nulla értéket
     * kap.
     */
    public void reset() {
        setTurnCount(0);
        try {
            this.loadMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Player player : players) {
            player.reset();
        }
    }

    /**
     * Befejezi a játékot.
     */
    public void quit() {
        this.shouldQuit = true;
    }

    /**
     * Ez a metódus tölti be a térképet. Létrehoz egy Map objektumot, majd
     * betölti a pályát a map.txt fájlból. Az egyes számok egyes cellatípusoknak
     * felelnek meg.
     * 
     * @throws IOException
     */
    protected void loadMap() throws IOException {
        Map tempMap = new Map(25, 19);

        FileReader fileReader = new FileReader(new File(this.mapFile));
        BufferedReader buffReader = new BufferedReader(fileReader);

        String line = new String();
        int i = 0;
        while ((line = buffReader.readLine()) != null) {
            String[] splitLine = line.split("\t");

            for (int j = 0; j < splitLine.length; j++) {
                int cellProperty = Integer.parseInt(splitLine[j]);

                switch (cellProperty) {
                case 0:
                    tempMap.setCell(new Cell(i, j, CellType.CELL_INVALID, null,
                            null));
                    break;
                case 1:
                    tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null,
                            null));
                    break;
                case 2:
                    tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null,
                            new OilStain()));
                    break;
                case 3:
                    tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null,
                            new GlueStain()));
                    break;
                case 4:
                    Cell cell = new Cell(i, j, CellType.CELL_VALID,
                            players.get(0), null);
                    tempMap.setCell(cell);
                    players.get(0).setInitialPosition(cell);
                    players.get(0).setCurrentCell(cell);
                    break;
                case 5:
                    cell = new Cell(i, j, CellType.CELL_VALID, players.get(1),
                            null);
                    tempMap.setCell(cell);
                    players.get(1).setInitialPosition(cell);
                    players.get(1).setCurrentCell(cell);
                    break;
                default:
                    tempMap.setCell(new Cell(i, j, CellType.CELL_VALID, null,
                            null));
                    break;
                }
            }

            i++;
        }
        buffReader.close();
        map = tempMap;
    }

    /**
     * Jelzi a kör végét az összes feliratkozott játékosnak. Tehát meghívja a
     * játékosok onTurnEnd() metódusát.
     */
    public void endTurn() {
        for (Player player : players) {
            player.onTurnEnd();

            Cell currCell = player.getCurrentCell();
            if (currCell.getCellType() == CellType.CELL_INVALID
                    || currCell.getX() < 0 || currCell.getY() < 0) {
                if (player == players.get(0))
                    printOutcome(players.get(1).getClass().getSimpleName()
                            + " " + players.get(1).getIdx());
                else
                    printOutcome(players.get(0).getClass().getSimpleName()
                            + " " + players.get(0).getIdx());
                System.exit(0);
            }
        }

        if (turnCount == maxTurns) {
            Player winner = null;
            if (players.get(0).getDistance() > players.get(1).getDistance()) {
                winner = players.get(0);
            } else {
                winner = players.get(1);
            }
            if (winner == null)
                printOutcome("Draw");
            else {
                printOutcome(winner.getClass().getSimpleName() + " "
                        + winner.getIdx());
            }
        }
    }

    /**
     * Jelzi a kör elejét az összes feliratkozott játékosnak. Tehát meghívja a
     * játékosok onTurnStart() metódusát.
     */
    public void beginTurn() {
        for (Player player : players) {
            player.onTurnStart();

        }
        for (Robot robot : robots) {
            robot.onTurnStart();
        }

        turnCount++;
    }

    /**
     * Kezeli a konzolról kapott bemenetet.
     */
    public void handleInput() {
        Player current = this.players.get(currentPlayerIdx);
        System.out.println(current.toString());

        String line;

        Cell nextCell = null;

        boolean next = false;

        while (!next) {
            try {
                if (null != (line = this.reader.readLine())) {
                    String[] input = line.split(" ");
                    String cmd = input[0];

                    if (cmd.equals("reset")) {
                        this.reset();
                        next = true;
                    } else if (cmd.equals("quit")) {
                        this.quit();
                        next = true;
                    } else if (cmd.equals("showmap")) {
                        map.printMap();
                    } else if (cmd.equals("move")) {
                        if (nextCell == null) {
                            // Ha még nem léptünk
                            List<Cell> neighbours = this.map.getNeighbours(
                                    current.getCurrentCell(),
                                    current.getSpeed());
                            int x = current.getCurrentCell().getX(), y = current
                                    .getCurrentCell().getY(), distance = 2;

                            if (input.length >= 2) {
                                String res = input[1];
                                if (res.equals("W")) {
                                    y -= distance;
                                } else if (res.equals("NW")) {
                                    y -= distance;
                                    x -= distance;
                                } else if (res.equals("N")) {
                                    x -= distance;
                                } else if (res.equals("NE")) {
                                    y += distance;
                                    x -= distance;
                                } else if (res.equals("E")) {
                                    y += distance;
                                } else if (res.equals("SE")) {
                                    y += distance;
                                    x += distance;
                                } else if (res.equals("S")) {
                                    x += distance;
                                } else if (res.equals("SW")) {
                                    y -= distance;
                                    x += distance;
                                } else {
                                    System.out.println("Invalid direction");
                                }

                                for (Cell cell : neighbours) {
                                    if (cell.getX() == x && cell.getY() == y
                                            && cell.getPlayer() == null) {
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
                        } else {
                            System.out
                                    .println("Only one move is allowed per turn.");
                        }

                    } else if (cmd.equals("put-stain")) {

                        if (input[1].equals("G")) {
                            current.putStain(GlueStain.class.getName());
                        } else if (input[1].equals("O")) {
                            current.putStain(OilStain.class.getName());
                        } else {
                            System.out.println("Invalid stain");
                        }

                    } else if (cmd.equals("end-turn")) {
                        if (nextCell != null) {
                            current.move(nextCell);
                        }
                        next = true;

                    } else if (cmd.equals("oil-random")) {
                        if (input.length >= 2) {
                            String setting = input[1];

                            if (setting.equals("on")) {
                                this.oilRandom = true;
                            } else if (setting.equals("off")) {
                                this.oilRandom = false;
                            } else {
                                System.out.println("Invalid setting.");
                            }
                        } else {
                            System.out
                                    .println("USAGE: hardworking-little-robot-random <setting>");
                        }
                    } else if (cmd.equals("hardworking-little-robot-random")) {
                        if (input.length >= 2) {
                            String setting = input[1];

                            if (setting.equals("on")) {
                                this.robotRandom = true;
                            } else if (setting.equals("off")) {
                                this.robotRandom = false;
                            } else {
                                System.out.println("Invalid setting.");
                            }
                        } else {
                            System.out
                                    .println("USAGE: hardworking-little-robot-random <setting>");
                        }
                    } else if (cmd.equals("robot-move")) {

                        if (input.length < 4) {
                            System.out
                                    .println("Invalid command, not enough arguments.");
                        } else {
                            int id = Integer.parseInt(input[1]);
                            int row = Integer.parseInt(input[2]);
                            int col = Integer.parseInt(input[3]);

                            if (row > map.getSize()[0] || row < 0) {
                                System.out.println("Invalid <row> parameter");
                            } else if (col > map.getSize()[1] || col < 0) {
                                System.out.println("Invalid <col> parameter");
                            } else {
                                for (Robot robot : robots) {
                                    if (robot.idx == id) {
                                        robot.setCell(map.getCell(row, col));
                                    }
                                }
                            }
                        }
                    } else if (cmd.equals("set-clock")) {
                        if (input.length >= 2) {
                            try {
                                int tc = Integer.parseInt(input[1]);
                                this.turnCount = tc;
                            } catch (NumberFormatException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("USAGE: set-clock <T>");
                        }
                    } else {
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
     * 
     * @param turnCount
     *            Az érték, amelyre be szeretnénk állítani az eltelt körök
     *            számát.
     */
    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void printOutcome(String outcome) {
        String output = String.format(
                "%s %d: Distance - %d; %s %d: Distance - %d; Winner: %s",
                players.get(0).getClass().getSimpleName(), players.get(0)
                        .getIdx(), players.get(0).getDistance(), players.get(1)
                        .getClass().getSimpleName(), players.get(1).getIdx(),
                players.get(1).getDistance(), outcome);
        System.out.println(output);
    }

}
