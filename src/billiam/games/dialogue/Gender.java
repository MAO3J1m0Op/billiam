/*
 * Oh boi. It's Final Boss.
 * Now it's in Java. Ain't that cool?
 * Maybe I'll finish it this time.
 */
package billiam.games.dialogue;

/**
 * Because it is 2019, this needs to be customizable.
 * @author willharris
 */
public class Gender {
    /**
     * The pronoun for when the antecedent is the subject. (example: HE drives)
     */
    public final String subject;
    /**
     * The possessive pronoun for when the antecedent is the subject. 
     * (example: HIS car)
     */
    public final String possessiveSubject;
    /**
     * The pronoun for when the antecedent is the object. 
     * (example: gift from HIM)
     */
    public final String object;
    /**
     * The possessive pronoun for when the antecedent is the object. 
     * (example: car is HIS)
     */
    public final String possessiveObject;
    
    public Gender (String subject, String possessiveSubject,
            String object, String possessiveObject) {
        this.subject = subject;
        this.possessiveSubject = possessiveSubject;
        this.object = object;
        this.possessiveObject = possessiveObject;
    }
    
    /**
     * Gender instance for a normal sane male.
     */
    public static final Gender MALE = new Gender("he", "his", "him", "his");
    /**
     * Gender instance for a normal sane female.
     */
    public static final Gender FEMALE = new Gender("she", "her", "her", "hers");
    /**
     * Gender instance for a someone who would prefer to remain gender-neutral.
     */
    public static final Gender NEUTRAL = new Gender(
            "they", "their", "them", "theirs"
    );
}
