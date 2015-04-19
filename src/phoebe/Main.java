package phoebe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

	public static void main(String[] args)  {
	    Game game = new Game();
	    game.reader = new BufferedReader(new InputStreamReader(System.in));
	    
	    boolean next = false;
	    	    
	    while (!next) {
	        try {
	            String line;
	   
	            if (null != (line = game.reader.readLine())) {
	                String[] input = line.split(" ");
	                String cmd = input[0];
	                
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
