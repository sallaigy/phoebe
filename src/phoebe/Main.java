package phoebe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

/**
 * A programunk belépési pontját definiáló osztály. 
 * Létrehoz egy Game-t és egy Game.readert.
 */
public class Main {

	public static void main(String[] args)  {

	    Game game = new Game();
	    
	    game.reader = new BufferedReader(new InputStreamReader(System.in));
	    
	    boolean next = false;
	    	    
	    while (!next) {
	        try {
	            String line;
	            
	            // Beolvassuk a parancsot
	            if (null != (line = game.reader.readLine())) {
	                String[] input = line.split(" ");
	                String cmd = input[0];
	                
	                // Ha parancs egy indító parancs, akkor elindít egy játékot a beolvasott térképpel
	                if (cmd.equals("start")) {
	                    if (input.length >= 2) {
	                        String map = input[1];
	                        game.start(map);
	                        next = true;
	                    } else {
	                        System.out.println("USAGE: start <mapFileName>");
	                    }
	                }
	            }
	        } catch (GameException e) {
	            System.out.println("Game error. Are you sure the map file is correct?");
	        } catch (IOException e) {
                e.printStackTrace();
            }
	    }
	}

}
