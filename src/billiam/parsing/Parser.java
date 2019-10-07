/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.parsing;

/**
 * Parses a String into a Parsable object.
 * @author willharris
 */
public final class Parser {
    
    public final java.util.Collection<Parsable> parserObjs;
    
    public Parser (Parsable... parserObjs) {
        this.parserObjs = java.util.Arrays.asList(parserObjs);
    }
    public Parser () {
        this.parserObjs = new java.util.ArrayList<>();
    }
    
    public Parsable parseNew(String value) {
        for (Parsable obj : parserObjs) {
            if (obj.convertable(value)) return obj.convert(value, this);
        }
        
        throw new IllegalArgumentException("The provided String '" + value
                + "' did not match any conversion rules provided.");
    }
}
