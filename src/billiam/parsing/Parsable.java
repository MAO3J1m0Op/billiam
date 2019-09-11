/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.parsing;

/**
 * Indicates the implementing type can be converted from a String, and allows it
 * to be used with the Parser class.
 * @author willharris
 */
public interface Parsable {
    
    /**
     * Determines if the String passed matches the implementing type.
     * @param test the String to test.
     * @return true if the String can be successfully parsed into the
     * implementing type, false otherwise.
     */
    public boolean convertable(String test);
    
    /**
     * Converts a String into the implementing type.
     * @param convert the String to convert.
     * @param parserUsed the Parser used to convert the String. This is used 
     * @return a new instance of the implementing type representing the passed
     * String.
     */
    public Parsable convert(String convert, Parser parserUsed);
}
