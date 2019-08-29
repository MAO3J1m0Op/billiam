/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.games;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread-safe class storing references to players. This class controls game
 * flow and turn order.
 * @author willharris
 * @param <P> the type of Players to store.
 */
public class Table<P> {
    
    /**
     * The list of players waiting to join.
     */
    private CopyOnWriteArrayList<P> queue;
    /**
     * The list of players active at the table.
     */
    private CopyOnWriteArrayList<P> active;
    
    /**
     * The maximum number of players that can sit at this table.
     */
    public final int maxPlayers;
    
    /**
     * The index of the player who is taking their turn. If there is no active
     * game, this player will be up next once the game starts.
     */ 
    private int up;
    
    /**
     * Starts a game and moves all queued players into the game until the
     * specified max players amount is reached.
     */
    public void newGame () {
        
        // Moves each queued player to the active
        for (int n = 0; n < queue.size();) {
            active.add(queue.get(0));
            queue.remove(0);
            
            // If we have now reached maxPlayers, stop.
            if (active.size() == maxPlayers) break;
        }
    }
    
    /**
     * Sends flow to the next player.
     * @return the reference to the player up next.
     */
    public P next () {
        
        ++up;
        if (up >= active.size()) {
            up = 0;
        }
        
        return active.get(up);
    }
    
    /**
     * Immediately moves the flow to a specified player. If there is no such
     * player, nothing will happen.
     * @param player the player to move flow to.
     * @return the player reference that flow moved to.
     */
    public P set (P player) {
        
        // Index the player
        int newUp = active.indexOf(player);
        
        // If no match was found, do nothing.
        if (newUp == -1) return active.get(up);
        
        // Move flow to the player
        up = newUp;
        return active.get(up);
    }
    
    /**
     * Immediately moves the flow to a specified player.
     * @param index the index to move flow to.
     * @return the player reference that flow moved to.
     */
    public P set (int index) throws IndexOutOfBoundsException {
        up = index;
        
        return active.get(up);
    }
    
    /**
     * Adds a new player. If there is no active game, the player will join the
     * table, but if the player joins in the middle of a game, they join the
     * queue.
     * @param player the player joining the game.
     */
    public void add (P player) {
        queue.add(player);
    }
    
    /**
     * Eliminates a player and moves them into the queue.
     * @param player the player to eliminate.
     */
    public void eliminate (P player) {
        active.remove(player);
    }
    
    /**
     * Entirely removes the player from the table.
     * @param player the player that is exiting the game.
     */
    public void quit (P player) {
        
        // If it fails to find a reference in the active, search the queue.
        if (!active.remove(player)) {
            queue.remove(player);
        }
    }
    
    /**
     * Returns the index of the current player up.
     * @return the index of the current player up.
     */
    public int indexOfUp () {
        return up;
    }
    
    public P[] players () {
        return (P[])active.toArray();
    }
    
    public P[] queue () {
        return (P[])queue.toArray();
    }
    
    public Table (int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
