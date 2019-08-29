/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.xml;

/**
 * Represents an element with a singular text component.
 * @author willharris
 */
public class TextElement implements Data {
    
    /**
     * The tag of this element.
     */
    public final String tag;
    
    /**
     * The namespace, if there is one.
     */
    public final String namespace;
    
    /**
     * The attributes attached to the tag.
     */
    public final java.util.HashMap<String, String> attributes = new
        java.util.HashMap<>();
    
    /**
     * The text component of this element.
     */
    public final String text;
    
    public TextElement (String tag, String text) {
        this.tag = tag;
        this.text = text;
        this.namespace = null;
    }
    
    public TextElement (String tag, String text, String namespace) {
        this.tag = tag;
        this.text = text;
        this.namespace = namespace;
    }
    
    @Override
    public String format (int indentation) {
        String value = "";
        
        value = value + namespace == null 
                ? Data.indent(indentation) + "<" + tag + " "
                : Data.indent(indentation) + "<" + namespace + ":" + tag + " ";
            
        for (String key : attributes.keySet()) {
            value = value + key + "=\"" + attributes.get(key) + "\" ";
        }

        value = value + ">" + text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("\'", "&apos;");
        
        return namespace == null 
                ? value + "</" + tag + ">"
                : value + "</" + namespace + ":" + tag + ">";
    }
}
