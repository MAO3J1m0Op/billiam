/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.cmd;

/**
 * Thrown when there is no matching command in a CommandLine.
 * @author willharris
 */
public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super(message);
    }
}
