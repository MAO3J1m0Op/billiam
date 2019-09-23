/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.misc;

import java.util.Scanner;

/**
 * This class stores collections of methods allowing for easier handling and
 * implementation of console input.
 * @author willharris
 */
public final class Input implements java.io.Serializable {
    
    /**
     * The prompt used for getBool(). By default, it is "(y/n) > ".
     */
    public String booleanPrompt = "(y/n) > ";
    
    public java.io.PrintStream out = System.out;
    public java.io.InputStream in = System.in;
    
    /**
     * Gets a boolean from the user using a y/n format.
     * @return true if the user answered yes, and false if they answered no.
     */
    public final boolean getBoolean () {
        
        Scanner input = new Scanner(in);
        
        // Continues to try to get console input until it succeeds.
        while (true) {
            // Prompt
            out.print(intPrompt);
            
            String value = input.nextLine();
                
            // Returns the y/n if valid.
            if (value.equals("y")) return true;
            if (value.equals("n")) return false;
            
            // If not valid, print error message.
            out.println("[ INV ] Please enter either \"y\" or \"n\".");
        }
    }
    
    /**
     * The prompt used for getInt(). By default, it is "> ".
     */
    public String intPrompt = "> ";
    
    /**
     * Gets an int via user input.
     * @return the int that the user types in.
     */
    public final int getInt () {
        
        Scanner input = new Scanner(in);
        
        // Continues to try to get console input until it succeeds.
        while (true) {
            // Prompt
            out.print(intPrompt);
            
            // Gets prompt
            String value = input.nextLine();

            // Catches any formatting errors
            if (!value.matches("/\\d+/")) {
                out.println("[ INV ] Please enter a valid integer.");
            }

            // Get input and return it if all ends well.
            return Integer.parseInt(value);
        }
    }
    
    /**
     * Gets an int via user input that is between min and max. 
     * @param min the inclusive lowest number allowed.
     * @param max the inclusive highest number allowed.
     * @return the int that the user types in.
     */
    public final int getInt (int min, int max) {
        
        // The code will run indefiniely until a valid input is received.
        while (true) {
            
            // Uses previous overload.
            int value = getInt();
            
            // Checks if it is between min and max.
            if (value > max) out.println("[ INV ] Value entered is too "
                    + "large. Must be at most " + max + ".");
            else if (value > max) out.println("[ INV ] Value entered is "
                    + "too small. Must be at least " + min + ".");
        
            // If it makes it through all the checks, return it.
            else return value;
        }
    }
    
    /**
     * The prompt used for getString(). By default, it is ">>> ".
     */
    public String stringPrompt = ">>> ";
    
    /**
     * Returns whatever line is inputted into the console. No restrictions
     * apply.
     * @return whatever line is inputted into the console as a String.
     */
    public final String getString () {
        
        Scanner input = new Scanner(in);
        
        out.print(stringPrompt);
        
        return input.nextLine();
    }
    
    /**
     * The prompt used for burn(). By default, it is "Press Enter to 
     * continue...".
     */
    public String burnPrompt = "Press Enter to continue...";
    
    /**
     * Awaits an enter press.
     */
    public final void burn () {
        Scanner input = new Scanner(in);
        
        out.print(burnPrompt);
        
        input.nextLine();
    }
    
    public Input (String booleanPrompt, String intPrompt, String stringPrompt,
            String burnPrompt) {
        this.booleanPrompt = booleanPrompt;
        this.intPrompt = intPrompt;
        this.stringPrompt = stringPrompt;
        this.burnPrompt = burnPrompt;
    }
    
    public Input () {}
}
