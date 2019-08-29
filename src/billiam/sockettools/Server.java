/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.sockettools;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Inherit this class to allow implementation of a Server.
 * @version 1.0
 * @author willharris
 */
public class Server implements java.io.Closeable {
    /**
     * The ServerSocket instance the server is based around.
     */
    private ServerSocket serverSocket;
    
    private final CopyOnWriteArrayList<Connection> connections = new
        CopyOnWriteArrayList<>();
    
    // Additional settings
    /**
     * Indicates if this server is in debug mode.
     */
    public final boolean debugMode;
    
    /**
     * The standard information stream.
     */
    protected PrintStream stdLog = System.out;
    
    /**
     * Logs a message to the server's stdLog. This also calls debugLog with the
     * same message.
     * @param message the message to log.
     */
    protected void stdLog (String message) {
        stdLog.println("[" + java.time.LocalTime.now() + ": INFO] "
                + message);
        debugLog(message);
    }
    
    /**
     * The PrintStream that additional debugging information is sent to.
     */
    protected PrintStream debugLog = System.out;
    
    /**
     * Logs a message to the server's debugger stream. If the server is not
     * in debug mode, this method does nothing.
     * @param message the message to log.
     */
    protected void debugLog (String message) {
        if (debugMode)
            stdLog.println("[" + java.time.LocalTime.now() + ": DEBUG] "
                + message);
    }
    
    public Server (boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    @Override
    public void close () throws IOException {
        for (Connection n : connections) n.close();
        
        serverSocket.close();
        isClosed = true;
    }
    
    public void start (int port) throws IOException {
        
        isClosed = false;
        
    }
    
    /**
     * True if the server is closed, false if it isn't.
     */
    private boolean isClosed = true;
}
