/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.xml;

import java.util.ArrayList;
import java.util.HashMap;
import billiam.misc.Mutable;

/**
 *
 * @author willharris
 */
public class Tree {
    
    /**
     * The attributes in the header.
     */
    public final HashMap<String, String> attributes;
    /**
     * The root element of this XML tree.
     */
    public final Element root;
    
    /**
     * Creates a new empty tree with the specified Element root, and the
     * specified attributes.
     * @param root the root element of this tree.
     * @param attributes the attributes of the new Tree's header.
     */
    public Tree (Element root, HashMap<String, String> attributes) {
        this.root = root;
        this.attributes = attributes;
    }
    
    /**
     * Creates a new empty tree with the specified Element root, and initializes
     * the attributes: version="1.0" encoding="utf-8".
     * @param root 
     */
    public Tree (Element root) {
        this.root = root;
        this.attributes = new HashMap<>();
        attributes.put("version", "1.0");
        attributes.put("encoding", "utf-8");
    }
    
    /**
     * Exports this XML tree through a given PrintWriter.The stream will 
     * not be closed by this method.
     * @param out the PrintWriter to export this Tree through.
     * @throws java.io.IOException if an I/O error occurs while exporting the
     * Tree.
     */
    public void export (java.io.PrintWriter out) throws java.io.IOException {
        
        String header = "<?xml ";
        
        for (String key : attributes.keySet()) {
            header = header + key + "=\"" + attributes.get(key) + "\" ";
        }
        
        header = header + "?>";
        
        out.println(header);
        out.println();
        out.println(root.format(0));
    }
    
    /**
     * Parses an XML tree out of the given file.
     * @param filePath the path to the parsed XML file.
     * @return the given XML file as a Tree.
     * @throws java.io.FileNotFoundException if the given file path points to a
     * nonexistent file.
     */
    public static Tree parse (String filePath) throws 
            java.io.FileNotFoundException {
        
        // Declaring file and scanner objects
        java.io.File file = new java.io.File(filePath);
        java.util.Scanner sc = new java.util.Scanner(file);
        
        String contents = "";
        
        // Pulls text into contents.
        while (sc.hasNextLine()) {
            contents = contents + sc.nextLine() + "\n";
        }
        
        // Converts the string to tokens
        ArrayList<String> tokens = tokenize(contents, "/[^a-z]/i");
        
        // Now for real parsing.
        Mutable<Integer> pos = new Mutable<>(0);
        
        // Finds the header.
        while (!(tokens.get(pos.get()).equals("<"))) {
            pos.set(pos.get() + 1);
            
            // We're not allowing IndexOutOfBoundsException here.
            if (pos.get() >= tokens.size()) throw new IllegalArgumentException(
                    "Invalid token. Expected '<', got 'EOF'"
            );
        }
        
        // Checks if the following tokens are valid.
        if (tokens.get(pos.get()+1).equals("?") && 
                tokens.get(pos.get()+2).equals("xml"))
            throw new IllegalArgumentException(
                    "Invalid token. Expected '?xml', got '" + 
                            tokens.get(pos.get()+1) + "'"
            );
        
        // Parses attributes for the header.
        HashMap<String, String> headerAttribs = parseAttribs("?", pos, tokens);
        
        // Skips the ?>\n
        pos.set(pos.get() + 2);
        
        
        // We're not done yet.
        // TODO
        throw new UnsupportedOperationException();
    }
    
    /**
     * Converts a String into a set of tokens for easier parsing.
     * @param v the String to convert.
     * @param splitAt the character set that should be it's own token.
     * @return an ArrayList containing the tokens.
     */
    private static ArrayList<String> tokenize (String v, String splitAt) {
        
        ArrayList<String> value = new ArrayList<>();
        
        String token = "";
        
        for (int n = 0; n < v.length(); ++n) {
            
            // If the char we reach is a splitter char
            if (v.substring(n, n+1).matches(splitAt)) {
                
                // Resets the token
                value.add(token);
                token = "";
                token = token + v.substring(n, n+1);
            } 
            
            // If it isn't
            else {
                token = token + v.substring(n, n+1);
            }
        }
        
        return value;
    }
    
    /**
     * Parses attributes until the specified ending token.
     * @param until the method will return once the token at the position
     * matches the string.
     * @param pos the position the method starts at.
     * @param tokens the tokens to parse through.
     * @return a HashMap with all the attributes.
     */
    private static HashMap<String, String> parseAttribs (String until, 
            Mutable<Integer> pos, ArrayList<String> tokens) {
        
        HashMap<String, String> value = new HashMap<>();
        
        while (true) {
            if (tokens.get(pos.get()).equals(until)) return value;
            
            String key = "";
            
            while (!(tokens.get(pos.get()).equals("="))) {
                key = key + tokens.get(pos.get());
                pos.set(pos.get() + 1);
            }
            
            // Value time!
            String keyValue = "";
            
            
            
            // Skips the opening quotes.
            pos.set(pos.get() + 1);
            
            while (!(tokens.get(pos.get()).equals("\""))) {
                keyValue = keyValue + tokens.get(pos.get());
                pos.set(pos.get() + 1);
            }
            
            // Skips the whitespace
            pos.set(pos.get() + 1);
            
            // Adds the attribute in.
            value.put(key, keyValue);
        }
    }
}
