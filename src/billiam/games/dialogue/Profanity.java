/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.games.dialogue;

/**
 * Protecting children (not programmers) from Big Naughty since 2019.
 * @author willharris
 */
public class Profanity {
    
    /**
     * Determines whether or not to display the naughty words.
     */
    public boolean display;
    
    private char naughtyChar () {
        return billiam.misc.BillUtil.randomChoice(new java.util.Random(), 
                "!@#$%^&*?.,<>\\~-+"
        );
    }
    
    /**
     * Returns "ass" if accepted, and random censoring characters if not
     * accepted.
     * @return a string that may contain crude language.
     */
    public String ass () {
        
        if (display) return "ass";
        
        else {
            String value = "";
            for (int n = 0; n < 3; ++n) value = value + naughtyChar();
            return value;
        }
    }
    
    /**
     * Returns "bitch" if accepted, and random censoring characters if not
     * accepted.
     * @return a string that may contain crude language.
     */
    public String bitch () {
        
        if (display) return "bitch";
        
        else {
            String value = "";
            for (int n = 0; n < 5; ++n) value = value + naughtyChar();
            return value;
        }
    }
    
    /**
     * Returns "cunt" if accepted, and random censoring characters if not
     * accepted.
     * @return a string that may contain crude language.
     */
    public String cunt () {
        
        if (display) return "cunt";
        
        else {
            String value = "";
            for (int n = 0; n < 4; ++n) value = value + naughtyChar();
            return value;
        }
    }
    
    /**
     * Returns "damn" if accepted, and random censoring characters if not
     * accepted.
     * @return a string that may contain crude language.
     */
    public String damn () {
        
        if (display) return "damn";
        
        else {
            String value = "";
            for (int n = 0; n < 4; ++n) value = value + naughtyChar();
            return value;
        }
    }
    
    /**
     * Returns "fuck" if accepted, and random censoring characters if not
     * accepted.
     * @return a string that may contain crude language.
     */
    public String fuck () {
        
        if (display) return "fuck";
        
        else {
            String value = "";
            for (int n = 0; n < 4; ++n) value = value + naughtyChar();
            return value;
        }
    }
    
    /**
     * Returns "shit" if accepted, and random censoring characters if not
     * accepted.
     * @return a string that may contain crude language.
     */
    public String shit () {
        
        if (display) return "shit";
        
        else {
            String value = "";
            for (int n = 0; n < 4; ++n) value = value + naughtyChar();
            return value;
        }
    }
}
