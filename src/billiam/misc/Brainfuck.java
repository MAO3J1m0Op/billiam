/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.misc;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Scanner;

/**
 * A sequence of Brainfuck code.
 * @author willharris
 */
public class Brainfuck implements Runnable {
    
    private Brainfuck(String code) {
        this.code = code;
    }
    
    public static Brainfuck parseNewFile (String path) 
            throws java.io.FileNotFoundException {
        return parseNewFile(new java.io.File(path));
    }
    
    public static Brainfuck parseNewFile (java.io.File path) 
            throws java.io.FileNotFoundException {
        Scanner sc = new Scanner(path);
        String contents = "";
        
        while (sc.hasNextLine()) {
            contents = contents + sc.nextLine();
        }
        
        return parseNew(contents);
    }
    
    /**
     * Parses a new shorthand sequence. A number preceding a character
     * duplicates that character a given number of times, just like VIM.
     * @param shorthand the sequence to convert to Brainfuck.
     * @return a Brainfuck object representing a shorthand sequence.
     */
    public static Brainfuck parseNewShorthand (String shorthand) {
        
        String seq = "";
        String numStr;
        int num;
        
        for (int n = 0; n < shorthand.length(); ++n) {
            
            num = 1;
            numStr = "";
            
            // Duplicates at numbers
            while (shorthand.substring(n, n+1).matches("/\\d/")) {
                numStr = numStr + shorthand.substring(n, n+1);
            }
            
            // Adds the chars.
            for (int o = 0; o < num; ++o) {
                seq = seq + shorthand.charAt(n);
            }
        }
        
        return parseNew(seq);
    }
    
    /**
     * Parses a String into Brainfuck code.
     * @param code the code to parse.
     * @return the Brainfuck object containing the parsed and now runnable code.
     */
    public static Brainfuck parseNew (String code) {
        
        String codeStr = "";
        
        // Each time a bracket is found, a new bracket match is made.
        ArrayList<Integer> openBrackets = new ArrayList<>();
        int openBracketsIndex = -1; // The index to get
        ArrayList<BracketMatch> brackets = new ArrayList<>();
        
        for (int n = 0; n < code.length(); ++n) {
            
            // Bracket check
            if (code.substring(n, n+1).equals("[")) {
                openBrackets.add(n);
                ++openBracketsIndex;
            }
            // Bracket check
            if (code.substring(n, n+1).equals("]")) {
                
                if (openBracketsIndex < 0) throw new IllegalArgumentException(
                        "Mismatched [ and ]."
                );
                
                BracketMatch add = new BracketMatch();
                add.closing = n;
                add.opening = openBrackets.get(openBracketsIndex);
                openBrackets.remove(openBracketsIndex);
                brackets.add(add);
            }
            
            // If the character at pos matches a BF char
            if (code.substring(n, n+1).matches("[<>\\-\\+\\.,\\[\\]]")) {
                codeStr += code.substring(n, n+1);
            }
        }
        
        Brainfuck value = new Brainfuck(codeStr);
        value.brackets = brackets.toArray(new BracketMatch[0]);
        return value;
    }
    
    /**
     * True if this object's run method is running, false otherwise.
     */
    private boolean isRunning;
   
    /**
     * The buffer that , gets characters from.
     */
    private CopyOnWriteArrayList<Character> inBuffer = new
            CopyOnWriteArrayList<>();
    
    /**
     * The PrintStream the Brainfuck object uses to output.
     */
    public java.io.PrintStream out = System.out;
    /**
     * The InputStream the Brainfuck object uses to receive input.
     */
    public java.io.InputStream in = System.in;
    
    private final Thread listenForInput = new Thread(() -> {
        
        Scanner sc = new Scanner(in);
        String next;
        
        while (isRunning) {
            
            next = sc.nextLine();
            
            ArrayList<Character> nextChars = new ArrayList<>();
            
            // Collects chars from next
            for (char i : next.toCharArray()) {
                nextChars.add(i);
            }
            
            inBuffer.addAll(nextChars);
        }
    });
    
    /**
     * The actual code of the sequence.
     */
    private final String code;
    
    /**
     * The sets of matching brackets.
     */
    private BracketMatch[] brackets;
    
    private int findMatch (int index) {
        
        for (int n = 0; n < brackets.length; ++n) {
            
            // Returns the opposite if it exists.
            if (brackets[n].opening == index) return brackets[n].closing;
            if (brackets[n].closing == index) return brackets[n].opening;
        }
        
        throw new IllegalArgumentException("No matching bracket at index " 
                + index + ".");
    }
    
    /**
     * A set of two matching brackets.
     */
    private static class BracketMatch {
        /**
         * The location of the opening bracket.
         */
        public int opening;
        /**
         * The location of the closing bracket.
         */
        public int closing;
    }
    
    @Override
    public void run () {
        
        isRunning = true;
        int pos = 0; // The current index the runner is at.
        int ptr = 0; // The current memory the runner is at.
        ArrayList<Byte> memory = new ArrayList<>(); // The memory.
        memory.add((byte)0); // First item
        ArrayList<Byte> inputs = new ArrayList<>(); // The used input stream.
        
        // Starts buffer loop.
        listenForInput.start();
        
        while (true) {
            
            // < = decrement pointer
            if (code.charAt(pos) == '<') --ptr;
            
            // > = increment pointer
            if (code.charAt(pos) == '>') {
                ++ptr;
                if (ptr == memory.size()) memory.add((byte)0);
            }
            
            // - = decrement value
            if (code.charAt(pos) == '-') {
                memory.set(ptr, (byte)(memory.get(ptr) - 1));
            }
            
            // + = increment value
            if (code.charAt(pos) == '+') {
                memory.set(ptr, (byte)(memory.get(ptr) + 1));
            }
            
            // . = output value
            if (code.charAt(pos) == '.') {
                out.print((char)(memory.get(ptr) & 0xFF));
            }
            
            // , = set value to input
            if (code.charAt(pos) == ',') {
                
                // Blocks code until a byte is found.
                while (inBuffer.isEmpty()) {}
                memory.set(ptr, (byte)inBuffer.get(0).charValue());
                inBuffer.remove(0);
            }
            
            // [ = move to corresponding ] if value is 0
            if (code.charAt(pos) == '[') {
                if (memory.get(ptr) == 0) pos = findMatch(pos);
            }
            
            // ] = move to corresponding [ if value is not 0
            if (code.charAt(pos) == ']') {
                if (memory.get(ptr) != 0) pos = findMatch(pos);
            }
            
            // Next position
            ++pos;
            
            // Break check
            if (pos == code.length()) break;
        }
        
        isRunning = false;
    }
    
    @Override
    public String toString() {
        return code;
    }
}
