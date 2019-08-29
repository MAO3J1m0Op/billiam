/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.cmd;

/**
 *
 * @author willharris
 */
@FunctionalInterface
public interface ExecutionProtocol {
    public void execute (Object[] args);
}
