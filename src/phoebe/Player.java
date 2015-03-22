package phoebe;

import java.util.HashMap;

public class Player {

    private Cell initialPosition;
    
    private boolean canChangeDirection = true;
    
    private int speed;
    
    private HashMap<Stain, Integer> storedStains = new HashMap<Stain, Integer>();
    
    private Cell currentCell;
    
    private static OilStain oilKey = new OilStain();
    private static GlueStain glueKey = new GlueStain();
    
    /*
     * Player konstruktora, amely egy cellát kap.
     * Az aktuális cellát átállítja a kapott cellára.
     * Létrehozza a HashMap-et, amiben tároljuk, hogy melyik foltból mennyi van.
     */
    public Player(Cell cell) {
    	currentCell = cell;
    	
    	storedStains.put(oilKey, 3);
    	
    	storedStains.put(glueKey, 3);
    }
    
    /*
     * Visszaadja a kezdõpozíciót.
     */
    public Cell getInitialPosition() {
    	Logger.methodEntry(this);
    	
    	Logger.methodExit(this);
    	
    	return initialPosition;
    }
    
    /*
     * Kezdõpozíció beállítása.
     */
    public void setInitialPosition(Cell init) {
    	Logger.methodEntry(this, init.toString());
    	
    	this.initialPosition = init;
    	
    	Logger.methodExit(this);
    }
    
    /*
     * Visszatér azzal a cellával, amin éppen áll a játékos.
     */
    public Cell getCurrentCell() {
    	Logger.methodEntry(this);
    	
    	Logger.methodExit(this);
    	
    	return currentCell;
    }
    
    /*
     * Átlépteti a játékost a paraméterként átvett cellára.
     */
    public void move(Cell cell) {
        Logger.methodEntry(this, cell.toString());
        
        currentCell = cell;
        
        currentCell.setPlayer(this);
                
        Logger.methodExit(this);
    }
    
    /*
     * Olaj- vagy ragacsfolt lerakása.
     */
    public void putStain(String stainType) {
        Logger.methodEntry(this, stainType);        
        
        if(stainType.equals("glue")) {
        	GlueStain glueStain = new GlueStain();
        	
        	getCurrentCell().setGameObject(glueStain);
        }
        
        if(stainType.equals("oil")) {
        	OilStain oilStain = new OilStain();
        	
        	getCurrentCell().setGameObject(oilStain);
        }                 
       
        Logger.methodExit(this);        
    }
    
    /*
     * Alaphelyzetbe állítja a foltkészletet.
     */
    public void resetStainCount() {
        Logger.methodEntry(this);
        
        storedStains.replace(oilKey, 3);
        
        storedStains.replace(glueKey, 3);
                
        Logger.methodExit(this);        
    }
    
    /*
     * Meghívja annak a cellának az interact() metódusát, amelyiken tartózkodik.
     */
    public void onTurnStart() {
        Logger.methodEntry(this);
        
        getCurrentCell().interact();
                
        Logger.methodExit(this);        
    }
    
    /*
     * A kör végén visszaállítja a mozgathatóságot, és a sebességet.
     * (Eltávolítja az elõzõ körben felvett foltok hatását.)
     */
    public void onTurnEnd() {
        Logger.methodEntry(this);
        
        //alaphelyzetbe állítja a sebességet
        this.setSpeed(2);
        
        //alaphelyzetbe állítja a mozgathatóságát
        this.setCanChangeDirection(true);
                
        Logger.methodExit(this);        
    }
    
    /*
     * Megadja a kezdõponttól megtett távolságot.
     */
    public int getDistance() {
    	Logger.methodEntry(this);
    	
    	Logger.methodExit(this); 
    	
        return 0;
    }
    
    /*
     * A játék újraindítása.
     * Alaphelyzetbe állítja a foltkészletet.
     * Engedélyezi az irányváltoztatását.
     * Alapértékre állítja a sebességet.
     */
    public void reset() {
        Logger.methodEntry(this);
        
        this.resetStainCount();
        
        this.setCanChangeDirection(true);
        
        this.setSpeed(2);
                
        Logger.methodExit(this);        
    }
    
    @Override
    public String toString() {
        return super.toString();
    }

    /*
     * Visszatér azzal, hogy a játékos mozgathatja-e a robot.
     */
    public boolean isCanChangeDirection() {
    	Logger.methodEntry(this);
    	
    	Logger.methodExit(this);
    	
        return canChangeDirection;
    }

    /*
     * Azt állítja be, hogy az adott játékos tudja-e irányítani a robotot.
     */
    public void setCanChangeDirection(boolean canChangeDirection) {
    	Logger.methodEntry(this);
    	
    	this.canChangeDirection = canChangeDirection;
        
        Logger.methodExit(this);
    }

    /*
     * Visszatér a játékos sebességével.
     */
    public int getSpeed() {
    	Logger.methodEntry(this);
    	
    	Logger.methodExit(this);
    	
        return speed;
    }

    /*
     * A paraméterként kapott értéket beállítja a játékos sebességére.
     */
    public void setSpeed(int speed) {
       Logger.methodEntry(this);
       
       this.speed = speed;
       
       Logger.methodExit(this);    	
    }
    
}
