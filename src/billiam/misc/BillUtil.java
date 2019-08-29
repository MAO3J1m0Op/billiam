/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.misc;

import java.util.Random;

/**
 *
 * @author willharris
 */
public class BillUtil {
    
    /**
     * No instances should be made of a static class.
     */
    private BillUtil() {}
    
    public static boolean equalsOneOf (Object obj, Iterable list) {
        
        for (Object comparator : list) {
            if (obj.equals(comparator)) return true; 
        }
        
        return false;
    }
    
    public static <E> E randomChoice (Random random, 
            java.util.List<E> arr)
    {
        return arr.get(random.nextInt(arr.size()));
    }
    
    public static char randomChoice (Random random, String str) {
        return str.charAt(random.nextInt(str.length()));
    }
    
    public static <E> E randomChoice (Random random, E[] arr) {
        return arr[random.nextInt(arr.length)];
    }
}
