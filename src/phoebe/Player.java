package phoebe;

import java.util.HashMap;

public class Player {

    private Cell initialPosition;
    
    private boolean canChangeDirection = true;
    
    private int speed;
    
    private HashMap<Stain, Integer> storedStains = new HashMap<Stain, Integer>();
    
    private Cell currentCell;
    
    public void move(Cell cell) {
        Logger.methodEntry(this, cell.toString());
                
        Logger.methodExit(this);
    }
    
    public void putStain(String stainType) {
        Logger.methodEntry(this, stainType);
                
        Logger.methodExit(this);        
    }
    
    public void resetStainCount() {
        Logger.methodEntry(this);
                
        Logger.methodExit(this);        
    }
    
    public void onTurnStart() {
        Logger.methodEntry(this);
                
        Logger.methodExit(this);        
    }
    
    public void onTurnEnd() {
        Logger.methodEntry(this);
                
        Logger.methodExit(this);        
    }
    
    public int getDistance() {
        return 0;
    }
    
    public void reset() {
        Logger.methodEntry(this);
                
        Logger.methodExit(this);        
    }
    
    @Override
    public String toString() {
        return super.toString();
    }

    public boolean isCanChangeDirection() {
        return canChangeDirection;
    }

    public void setCanChangeDirection(boolean canChangeDirection) {
        this.canChangeDirection = canChangeDirection;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public Cell getCurrentCell() {
    	return currentCell;
    }
    
    public void setCurrentCell(Cell cell) {
    	currentCell = cell;
    }

	public Cell getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Cell initialPosition) {
		this.initialPosition = initialPosition;
	}
    
}
