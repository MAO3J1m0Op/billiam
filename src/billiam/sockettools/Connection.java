/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.sockettools;

/**
 * Stores the socket connection to the server, as well as the in and out
 * streams. Can also be run in a thread, which will cause the thread to
 * listen at this socket for a message. This class can be overridden to
 * implement custom data into connections.
 * @param <C> the type of cache to use.
 * @version 1.0
 * @author willharris
 */
public class Connection<C extends Cache> implements java.io.Closeable {
    /**
     * The connection's socket.
     */
    private final java.net.Socket socket;
    /**
     * The input stream that collects data from the socket.
     */
    public final java.io.InputStream in;
    /**
     * The output stream that sends data through the socket.
     */
    public final java.io.OutputStream out;
    
    public Connection (java.net.Socket socket, C cache) throws 
            java.io.IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.cache = cache;
    }
    
    /**
     * The cache of data to use.
     */
    public C cache;
    
    @Override
    public void close () throws java.io.IOException {
        cache.close();
        in.close();
        out.close();
        socket.close();
    }
}
