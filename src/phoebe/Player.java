package phoebe;

import java.util.HashMap;

/**
 * A játékosokat megtestesítő objektum. Minden játékos egy-egy Playernek feleltethető meg.
 * Minden játékos pontosan egy cellán tartózkodik.
 */
public class Player {

    private int idx = -1;
    
    private Cell initialPosition = new Cell();
    
    private boolean canChangeDirection = true;
    
    private int speed = 2;
    
    private HashMap<String, Integer> storedStains = new HashMap<String, Integer>();
    
    private Cell currentCell = new Cell();
        
    /**
     * Player konstruktora, amely egy cellát kap.
     * Az aktuális cellát átállítja a kapott cellára.
     * Létrehozza a HashMapet, amelyben tároljuk, hogy melyik foltból mennyi van.
     * @param cell A játékos kezdőpoziciója.
     */
    public Player(Cell cell) {
    	currentCell = cell;
    	
    	storedStains.put(OilStain.class.getName(), 3);    	
    	storedStains.put(GlueStain.class.getName(), 3);
    }
    
    /**
     * Visszaadja a kezdőpoziciót.
     * @return A kezdőpozició.
     */
    public Cell getInitialPosition() {    	
    	return initialPosition;
    }
    
    /**
     * Beállítja a játékos kezdőpozicióját.
     * @param init A játékos kezdőcellája.
     */
    public void setInitialPosition(Cell init) {
    	Logger.methodEntry(this, init.toString());
    	
    	this.initialPosition = init;
    	
    	Logger.methodExit(this);
    }
    
    /**
     * Visszaadja a cellát, amelyen a játékos tartózkodik.
     * @return A játékos alatt lévő cella.
     */
    public Cell getCurrentCell() {    	
    	return currentCell;
    }
    
    /**
     * Egy Player konstruktor. 
     * @param idx A játékos azonosítója.
     */
    public Player(int idx) {
        Logger.methodEntry(this, Integer.toString(idx));
        this.idx = idx;        
        Logger.methodExit(this);
    }
    
    /**
     * Átlépteti a játékost a paraméterként átvett cellára. Tehát átállítja a referenciát (currentCell).
     * @param cell Ezen a cellán fog tartózkodni a játékos.
     */
    public void move(Cell cell) {
        Logger.methodEntry(this, cell.toString());
        
        this.currentCell.setPlayer(null);
        
        this.currentCell = cell;
        this.currentCell.setPlayer(this);
                
        Logger.methodExit(this);
    }
    
    /**
     * Elhelyez egy foltot az aktuális cellán, ha a foltkészlete megengedi.
     * Mindig tehet le foltot, ha valid cellán tartózkodik, hiszen ha egy cellán elhelyezkedő foltra rálép, akkor az megszűnik.
     * (Természetesen a cella nem lehet invalid.)
     * @param stainType A lerakandó folt típusa. Lehet "glue" vagy "stain".
     */
    public void putStain(String stainType) {
        Logger.methodEntry(this, stainType);        
        
        if(stainType.equals(GlueStain.class.getName())) {
        	GlueStain glueStain = new GlueStain();
        	
        	getCurrentCell().setGameObject(glueStain);
        }
        
        if(stainType.equals(OilStain.class.getName())) {
        	OilStain oilStain = new OilStain();
        	
        	getCurrentCell().setGameObject(oilStain);
        }                 
       
        Logger.methodExit(this);        
    }
    
    /**
     * A player foltkészletének alapértelmezettre állítása. 
     */
    public void resetStainCount() {
        Logger.methodEntry(this);

        storedStains.put(OilStain.class.getName(), 3);      
        storedStains.put(GlueStain.class.getName(), 3);
        
        Logger.methodExit(this);        
    }
    
    /**
     * Reagál a kör eleje eseményre.
     * Meghívja annak a cellának az interact() metódusát, amelyiken tartózkodik.
     */
    public void onTurnStart() {
        Logger.methodEntry(this);
        
        this.getCurrentCell().interact();
                
        Logger.methodExit(this);        
    }
    
    /**
     * Reagál a kör vége eseményre.
     * A player megfelelő attribútumait alaphelyzetbe állítja (például: sebesség).
     */
    public void onTurnEnd() {
        Logger.methodEntry(this);
        
        //alaphelyzetbe állítja a sebességét
        this.setSpeed(2);
        
        //alaphelyzetbe állítja a mozgathatóságágt
        this.setCanChangeDirection(true);
                
        Logger.methodExit(this);        
    }
    
    /**
     * Visszaadja a currentCell és initialPosition közti távot.
     * A játék megnyerése esetén a játékosok megtett távját hasonlítjuk össze.
     * @return A currentCell és initialPosition közti táv.
     */
    public int getDistance() {
        return 
                Math.abs(this.initialPosition.getX() - this.currentCell.getX())
                    +
                Math.abs(this.initialPosition.getY() - this.currentCell.getY());
    }
    
    /**
     * A játékos egyes attribútumainak alapértékre állítása a játék újraindítása esetén.
     * Itt állítjuk vissza a sebességét, a foltkészletét illetve hogy tud-e irányt váltani.
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
        return "[" + this.getClass().getName() + "](id=" + this.idx + ")";
    }

    /**
     * Megadja, hogy a játékos tud-e irányt váltani. Azaz, hogy olajba lépett-e.
     * @return Tud-e irányt váltani vagy nem.
     */
    public boolean isCanChangeDirection() {    	
        return canChangeDirection;
    }

    /**
     * Beállítja, hogy a játékos tudjon-e irányt váltani.
     * @param canChangeDirection Tudjon-e irányt váltani vagy nem.
     */
    public void setCanChangeDirection(boolean canChangeDirection) {
    	Logger.methodEntry(this, Boolean.toString(canChangeDirection));
    	
    	this.canChangeDirection = canChangeDirection;
        
        Logger.methodExit(this);
    }

    /**
     * Visszatér a játékos sebességével.
     * @return A játékos sebessége.
     */
    public int getSpeed() {    	
        return this.speed;
    }

    /**
     * Beállítja a játékos sebességét.
     * @param speed A játékos következő sebessége.
     */
    public void setSpeed(int speed) {
       Logger.methodEntry(this, Integer.toString(speed));
       
       this.speed = speed;
       
       Logger.methodExit(this);    	
    }

    /**
     * Beállítja a játékos celláját.
     * @param cell A cella, amelyen a játékos tartózkodni fog.
     */
    public void setCurrentCell(Cell cell) {
    	currentCell = cell;
    }
    
    public int getIdx() {
        return this.idx;
    }
}
