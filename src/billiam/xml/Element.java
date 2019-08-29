/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.xml;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an element, or data enclosed in a tag and attributes.
 * @author willharris
 */
public class Element implements Data {
    
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
    public final HashMap<String, String> attributes;
    
    /**
     * The child elements of this Element.
     */
    public final ArrayList<Data> children = new ArrayList<>();
    
    /**
     * Creates a new Element with the specified tag and attributes, and no 
     * namespace.
     * @param tag the tag of the new element.
     * @param attributes the attributes of the new element.
     */
    public Element (String tag, HashMap<String, String> attributes) {
        this.tag = tag;
        this.attributes = attributes;
        this.namespace = null;
    }
    
    /**
     * Creates a new Element with the specified tag, and no attributes.
     * Attributes can be added later by accessing the attributes field.
     * @param tag the tag of the new element.
     */
    public Element (String tag) {
        this.tag = tag;
        this.attributes = new HashMap<>();
        this.namespace = null;
    }
    
    /**
     * Creates a new Element with the specified tag and namespace, and no 
     * attributes. Attributes can be added later by accessing the attributes 
     * field.
     * @param tag the tag of the new element.
     * @param namespace the namespace of the new element.
     */
    public Element (String tag, String namespace) {
        this.tag = tag;
        this.attributes = new HashMap<>();
        this.namespace = namespace;
    }
    
    /**
     * Creates a new Element with the specified, tag attributes, and namespace.
     * @param tag the tag of the new element.
     * @param attributes the attributes of the new element.
     * @param namespace the namespace of the new element.
     */
    public Element (String tag, HashMap<String, String> attributes, String
            namespace) {
        this.tag = tag;
        this.attributes = attributes;
        this.namespace = namespace;
    }
    
    /**
     * Gets all the child elements, omitting the lines of raw data.
     * @return an ArrayList containing all the sub-elements of this Element.
     */
    public ArrayList<Element> getSubElements () {
        
        ArrayList<Element> value = new ArrayList<>();
        
        for (Data i : children) {
            if (i instanceof Element) value.add((Element)i);
        }
        
        return value;
    }
    
    /**
     * Gets all the data, omitting the child elements.
     * @return an ArrayList containing all the text. Each element is split at
     * the line.
     */
    public ArrayList<String> getText () {
        ArrayList<String> value = new ArrayList<>();
        
        for (Data i : children) {
            if (i instanceof Text) value.add(((Text)i).text);
        }
        
        return value;
    }
    
    /**
     * Gets all the data, omitting the child elements.
     * @return a String made up of all the text in this element. Each line is
     * separated by a newline (\n) deliminator.
     */
    public String getTextAsOne () {
        String value = "";
        
        for (String i : getText()) {
            value = value + i + "\n";
        }
        
        return value;
    }
    
    @Override
    public String format (int indentation) {
        
        String value = "";
        
        // Add opening tag
        value = value + namespace == null 
                ? Data.indent(indentation) + "<" + tag + " "
                : Data.indent(indentation) + "<" + namespace + ":" + tag + " ";
        
        // Add attributes
        for (String key : attributes.keySet()) {
            value = value + key + "=\"" + attributes.get(key) + "\" ";
        }
        
        // If there are no children, add the following syntax:
        /* <tag attributes="" />
         */
        if (children.isEmpty())
            return value + "/>";
        
        // Otherwise, use default format.
        /* <tag attributes="">
         *     <elements />
         * </tag>
         */
        else {
            for (Data i : children) {
                value = value + i.format(indentation + 1) + "\n";
            }
            
            return namespace == null 
                ? value + Data.indent(indentation) + "</" + tag + ">"
                : value + Data.indent(indentation) + "</" + namespace + ":" 
                    + tag + ">";
        }
    }
}
