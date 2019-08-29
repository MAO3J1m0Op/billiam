/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.games;

/**
 * Immutable class used to represent a card in a standard 52-card deck.
 * @author willharris
 */
public class Card implements java.io.Serializable, Comparable<Card> {
    
    /**
     * The rank of the card. 1 means ace, 2-10 are number cards, 11 is Jack, 12
     * is queen, and 13 is king.
     */
    private final byte rank;
    /**
     * The suit of the card. 0 is clubs, 1 is diamonds, 2 is hearts, 3 is 
     * spades.
     */
    private final byte suit;
    
    /**
     * Creates a new card.
     * @param rank the rank of the card.
     * @param suit the suit of the card.
     * @throws IllegalArgumentException if an argument passed does not match the
     * rules of initializing a card.
     */
    public Card (int rank, int suit) throws IllegalArgumentException {
        
        // Rank check.
        if (rank > 13 || rank < 1) throw new IllegalArgumentException(
                "Argument 'rank' was invalid. Must be between 1 and 13.");
        
        // Suit check.
        if (suit > 3 || suit < 0) throw new IllegalArgumentException("Argument "
                + "'suit' was invalid. Must be between 0 and 3.");
        
        this.rank = (byte)rank;
        this.suit = (byte)suit;
    }
    
    /**
     * Creates a new card using the Rank and Suit enums.
     * @param rank the rank of the card.
     * @param suit the suit of the card.
     */
    public Card (Rank rank, Suit suit) {
        this(rank.ordinal() + 1, suit.ordinal());
    }
    
    /**
     * Gets the rank of this card.
     * @return the Rank constant corresponding to this Card's rank.
     */
    public Rank getRank () {
        switch (rank) {
            case 1: return Rank.Ace;
            case 2: return Rank.Two;
            case 3: return Rank.Three;
            case 4: return Rank.Four;
            case 5: return Rank.Five;
            case 6: return Rank.Six;
            case 7: return Rank.Seven;
            case 8: return Rank.Eight;
            case 9: return Rank.Nine;
            case 10: return Rank.Ten;
            case 11: return Rank.Jack;
            case 12: return Rank.Queen;
            case 13: return Rank.King;
            
            // This should never run.
            default: return null;
        }
    }
    
    /**
     * Gets the suit of this card.
     * @return the Suit constant corresponding to this Card's suit.
     */
    public Suit getSuit () {
        switch (suit) {
            case 0: return Suit.Clubs;
            case 1: return Suit.Diamonds;
            case 2: return Suit.Hearts;
            case 3: return Suit.Spades;
            
            // This should never run.
            default: return null;
        }
    }
    
    @Override
    public String toString () {
        return getRank().name() + " of " + getSuit().name();
    }
    
    @Override
    public int hashCode () {
        return (rank - 1) * 4 + suit;
    }

    @Override
    public boolean equals(Object obj) {
        
        // Equal operator?
        if (this == obj) {
            return true;
        }
        
        // Null check
        if (obj == null) {
            return false;
        }
        
        // Are they the same type?
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        // Compares the fields
        final Card castedObj = (Card)obj;
        return rank == castedObj.rank && suit == castedObj.suit;
    }
    
    public static Card[] getDeck() {
        return getDeck(1);
    }
    
    public static Card[] getDeck(int qty) {
        
        java.util.ArrayList<Card> valuelist = new java.util.ArrayList<>();
        
        for (int deck = 0; deck < qty; ++deck) {
            for (int rank = 1; rank <= 13; ++rank) {
                for (int suit = 0; suit < 4; ++suit) {
                   valuelist.add(new Card(rank, suit));
                }
            }
        }
        
        return valuelist.toArray(new Card[1]);
    }
    
    /**
     * Compares this card to another card and determines which card comes before
     * the other in a deck.
     * @param o the card being compared to this instance.
     * @return -1 if this card should be placed before the other card; 0 if the
     * two cards are equal, and 1 if this card should be placed after the other
     * card in a deck.
     */
    @Override
    public int compareTo (Card o) throws NullPointerException {
        
        // Suit trumps rank
        if (this.suit < o.suit) return -1;
        else if (this.suit > o.suit) return 1;
        
        // Suits have been deemed equal. Check rank.
        if (this.rank < o.rank) return -1;
        else if (this.rank > o.rank) return 1;
        
        // The cards are equal at this point.
        return 0;
    }
}
