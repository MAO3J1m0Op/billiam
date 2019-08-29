/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.xml;

/**
 * Represents a piece of raw string data. This class is immutable.
 * @author willharris
 */
public class Text implements Data {
    
    /**
     * The text of this instance.
     */
    public final String text;
    
    public Text (String text) {
        this.text = text;
    }
    
    @Override
    public String format (int indentation) {
        
        // Replaces &<>"' with their respective escapes.
        String newText = text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("\'", "&apos;"); 
        
        return Data.indent(indentation) + newText;
    }
}
