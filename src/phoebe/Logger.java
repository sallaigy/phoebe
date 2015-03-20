package phoebe;


/**
 * Console logger utility with automatic hiearchial numbering
 * 
 * @author Sallai Gyula <salla@sch.bme.hu> 
 */
public class Logger {

    private static int currentDepth = 0;
    
    private static int[] numbering = new int[20]; // A maximális mélység 20
    
    /**
     * Outputs and logs method entry. Call this in the first line of every method you wish to log.
     * 
     * @param sender
     * @param params
     */
    public static void methodEntry(Object sender, String... params) {
        
        Logger.numbering[Logger.currentDepth]++; // increment the numbering
        
        StringBuilder paramsMsg = new StringBuilder();
        
        if (params.length != 0) {
            for (int i = 0; i < params.length - 1; i++) {
                paramsMsg.append(params[i]);
                paramsMsg.append(", ");
            }
            
            paramsMsg.append(params[params.length - 1]);
        }

        
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StringBuilder message = new StringBuilder();
        message.append(String.format(
            "%s: %s.%s(%s)",
            Logger.getNumberingString(),
            sender.getClass().getName(),
            stackTrace[2].getMethodName(), // 0: getStackTrace(), 1: methodEntry(), thus we need the 2nd element
            paramsMsg
        ));

        System.out.println(message.toString());

        Logger.currentDepth++; // increment the depth so our children won't have to
    }
    
    /**
     * Outputs and logs method exit. Call this in every method you wish to log.
     * 
     * @param sender
     */
    public static void methodExit(Object sender) {
        Logger.numbering[Logger.currentDepth] = 0; // reset the child numberings
        Logger.currentDepth--;
    }
    
    /**
     * Increments the hiearchial numbering and prints a message
     * 
     * @param message
     */
    public static void printNumberedMessage(String message) {
        Logger.numbering[Logger.currentDepth]++; 
        
        System.out.println(String.format(
            "%s: %s",
            Logger.getNumberingString(),
            message
        ));
    }
        
    protected static String getNumberingString() {
        StringBuilder message = new StringBuilder();
                
        int i = 0;        
        while (Logger.numbering[i] != 0) {
            message.append(Logger.numbering[i]);
            message.append(".");
            i++;
        }
        
        message.deleteCharAt(message.length() - 1); // delete the last '.' char
        
        return message.toString();
    }
}
