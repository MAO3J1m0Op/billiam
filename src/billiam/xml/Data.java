/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.xml;

/**
 * Represents an element of data that can be placed into an XML file.
 * @author willharris
 */
public interface Data {
    /**
     * Returns a String representation of this piece of Data that can be placed
     * into an XML file and parsed back out.
     * @param indentation the level of indentation at formatting.
     * @return 
     */
    public String format (int indentation);
    
    public static final int INDENTATION_AMOUNT = 4;
    public static String indent (int indentation) {
        
        String value = "";
        
        for (int n = 0; n < indentation; ++n) {
            for (int o = 0; o < INDENTATION_AMOUNT; ++o) {
                value = value + " ";
            }
        }
        
        return value;
    }
}
