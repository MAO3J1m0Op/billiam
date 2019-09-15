/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.cmd;

/**
 * A Command object is added to a CommandLine object and tells the CommandLine
 * what commands to accept and what to do when a given command is received.
 * @author willharris
 */
public class Command implements Runnable {
    /**
     * The CommandLine calls this object based on the keyword.
     */
    public final String keyword;
    /**
     * The expected types of the parameters. The length of this array determines
     * the amount of allowed parameters.
     */
    public final Parameter[] params;
    /**
     * The method in this interface is run with the given arguments on 
     * execution of this command object.
     */
    public final ExecutionProtocol onExecution;
    
    /**
     * Creates a new Command object with the specified keyword, parameters and
     * execution protocol.
     * @param keyword the keyword the command identifies with.
     * @param params the parameters in this command.
     * @param onExecution the protocol for when this command is executed.
     */
    public Command (String keyword, Parameter[] params, 
            ExecutionProtocol onExecution) {
        this.keyword = keyword;
        this.params = params;
        this.onExecution = onExecution;
    }
    
    /**
     * Runs the command. Assumes values have already been passed to the
     * parameters.
     */
    @Override
    public void run () {
        java.util.ArrayList<Object> args = new java.util.ArrayList<>();
        
        for (Parameter param : params) {
            args.add(param.getPassedValue());
        }
        
        onExecution.execute(args.toArray());
    }
}
