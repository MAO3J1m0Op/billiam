/*
 * Oh boi. It's Final Boss.
 * Now it's in Java. Ain't that cool?
 * Maybe I'll finish it this time.
 */
package billiam.games.dialouge;

/**
 * Stores static methods that allow me to be lazy when writing dialogue.
 * @author willharris
 */
public final class Dialogue {
    
    /**
     * Prints a prompt that is created based on the specified options.
     * @param options the options that are provided in this prompt.
     */
    public static void prompt (String[] options) {
        for (int n = 0; n < options.length; ++n) {
            System.out.println("[" + (n + 1) + "] " + options[n]);
        }
    }
    
    /**
     * Prints a random one of the given lines via the String array.
     * @param options the String array that the method picks from.
     */
    public static void randomResponse (String[] options) {
        
        int choice = new java.util.Random().nextInt(options.length);
        
        System.out.println(options[choice]);
    }
    
    /**
     * A simple press Enter to continue prompt.
     */
    public static void advance () {
        billiam.misc.Input prompt = new billiam.misc.Input();
        
        prompt.stringPrompt = "Press Enter to continue...";
        prompt.getString();
    }
    
    private Dialogue () {}
}
