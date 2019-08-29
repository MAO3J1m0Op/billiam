/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.sockettools;

/**
 * Represents a special type of message to send to the client or server.
 * @author willharris
 */
public class Message implements java.io.Serializable {
    
    /**
     * This constructor is internal to allow only the sendMessage() objects to
     * construct this class.
     */
    Message(Deliminator deliminator, String message) {
        this.deliminator = deliminator;
        this.message = message;
    }
    
    /**
     * Determines the type of message this is.
     */
    public final Deliminator deliminator;
    /**
     * The message body.
     */
    public final String message;
    
    /**
     * Messages are sent to and from with three indicator characters
     * separated by a space. The three characters indicate what kind of
     * message was sent by the client or server.
     * @author willharris
     */
    public static enum Deliminator {
        /**
         * Standard message to and from.
         */
        MSG,
        /**
         * Disconnection request.
         */
        DSC,
        /**
         * The client is trying to get a ping from the server.
         */
        PNG,
        /**
         * "You still there?" Check/response for a timeout request.
         */
        YST,
        /**
         * An error has occurred.
         */
        ERR
    }
}
