/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.cmd;

import billiam.misc.Input;
import java.util.stream.Collectors;

/**
 * Executes commands passed by the user.
 * @author willharris
 */
public class CommandLine {
    /**
     * The input object to use for this CommandLine.
     */
    public final Input in;
    
    /**
     * The commands to search through.
     */
    private Command[] library;
    
    /**
     * Adds a command to the internal library of this CommandLine.
     * @param command the command object to add.
     */
    public void addCommand (Command command) {
        java.util.ArrayList<Command> newLib = new java.util.ArrayList<>(
                java.util.Arrays.asList(library));
        newLib.add(command);
        library = newLib.toArray(new Command[1]);
    }
    
    /**
     * Gets a command from the user.
     * @return the command.
     */
    public String[] getNext () {
        return in.getString().split(" ");
    }
    
    /**
     * Executes a command passed into this argument. The method will search this
     * object's library for a method with the right overload and the matching
     * keyword.
     * @param command the command to execute.
     * @throws CommandNotFoundException if the passed command does not have a
     * matching keyword or if an invalid amount of parameters were passed.
     */
    public void execute (String[] command) throws CommandNotFoundException {
        
        // It's all unfinished.
        throw new UnsupportedOperationException();
        
        // First is the keyword
     /* String keyword = command[0];
        
        // Then we find all the parameters that match this keyword.
        java.util.ArrayList<Command> matches = new java.util.ArrayList<>();
        for (int n = 0; n < library.length; ++n) {
            if (library[n].keyword.equals(keyword)) {
                matches.add(library[n]);
            }
        }
        
        // We start at 1 to skip the keyword.
        for (int n = 1; n < command.length; ++n) {
            
            // Checks if any of the values are an array type.
            for (Command match : matches) {
                if (match.params[n].type().matches("/array<[^>]+>/")) {
                    
                }
            }
            
            // Removes all matches where this argument would be invalid.
              // TODO: use functional operators
            for (Command match : matches) {
                if (!(match.params[n].isType(command[n]))) {
                    matches.remove(match);
                }
            }
        }
        
        if (matches.isEmpty()) throw new CommandNotFoundException("The "
                + "passed command does not match any commands in this "
                + "CommandLine.");
        
        // Find the best suited command.
        else if (matches.size() > 1) {
            
        }
        
        // One matching command? Just run it!
        else {
            for (int n = 0; n < command.length - 1; ++n) {
                matches.get(0).params[n].pass(command[n + 1]);
                matches.get(0).run();
            }
        } */
    }
    
    /**
     * Gets a command from the user and executes it.
     * @throws CommandNotFoundException if the passed command does not have a
     * matching keyword or if an invalid amount of parameters were passed.
     */
    public void executeNext() throws CommandNotFoundException {
        execute(getNext());
    }
    
    /**
     * Creates a new instance with an empty command library and the default
     * Input object.
     */
    public CommandLine () {
        this(new Input(), new Command[0]);
    }
    
    /**
     * Creates a new instance with a pre-made command library and the specified
     * Input object.
     * @param in the Input object to use for this command line.
     * @param library the array of commands to use in this library.
     */
    public CommandLine (Input in, Command[] library) {
        this.in = in;
        this.library = library;
    }
}
