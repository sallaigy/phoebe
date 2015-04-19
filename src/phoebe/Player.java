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
    	this.initialPosition = init;    	
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
        this.idx = idx;        
    }
    
    /**
     * Átlépteti a játékost a paraméterként átvett cellára. Tehát átállítja a referenciát (currentCell).
     * @param cell Ezen a cellán fog tartózkodni a játékos.
     */
    public void move(Cell cell) {
        
        this.currentCell.setPlayer(null);
        
        this.currentCell = cell;
      
        this.currentCell.setPlayer(this);
               
    }
    
    /**
     * Elhelyez egy foltot az aktuális cellán, ha a foltkészlete megengedi.
     * Mindig tehet le foltot, ha valid cellán tartózkodik, hiszen ha egy cellán elhelyezkedő foltra rálép, akkor az megszűnik.
     * (Természetesen a cella nem lehet invalid.)
     * @param stainType A lerakandó folt típusa. Lehet "glue" vagy "stain".
     */
    public void putStain(String stainType) { 
        
        if(stainType.equals(GlueStain.class.getName())) {
        	GlueStain glueStain = new GlueStain();
        	
        	getCurrentCell().setGameObject(glueStain);
        	glueStain.setCell(currentCell);
        }
        
        if(stainType.equals(OilStain.class.getName())) {
        	OilStain oilStain = new OilStain();
        	
        	getCurrentCell().setGameObject(oilStain);
        	oilStain.setCell(currentCell);
        }      
        System.out.println(currentCell.toString());
    }
    
    /**
     * A player foltkészletének alapértelmezettre állítása. 
     */
    public void resetStainCount() {

        storedStains.put(OilStain.class.getName(), 3);      
        storedStains.put(GlueStain.class.getName(), 3);
             
    }
    
    /**
     * Reagál a kör eleje eseményre.
     * Meghívja annak a cellának az interact() metódusát, amelyiken tartózkodik.
     */
    public void onTurnStart() {
        this.getCurrentCell().interact();
                        
    }
    
    /**
     * Reagál a kör vége eseményre.
     * A player megfelelő attribútumait alaphelyzetbe állítja (például: sebesség).
     */
    public void onTurnEnd() {
        
        //alaphelyzetbe állítja a sebességét
        this.setSpeed(2);
        
        //alaphelyzetbe állítja a mozgathatóságágt
        this.setCanChangeDirection(true);
                        
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
        
        this.resetStainCount();
        
        this.setCanChangeDirection(true);
        
        this.setSpeed(2);
                        
    }
    
   
    public String toString() {
        return String.format("%s %d: %s (%d, %d) Speed: %d CanChangeDirection: %b", 
        		this.getClass().getSimpleName(), this.idx, this.getCurrentCell().getClass().getSimpleName(),
        		this .currentCell.getX(), this.currentCell.getY(), this.speed, this.canChangeDirection);
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
    	
    	this.canChangeDirection = canChangeDirection;
        
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
       
       this.speed = speed;
       
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
