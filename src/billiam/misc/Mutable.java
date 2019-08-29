/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.misc;

/**
 * Wraps any given object as a Mutable object, or able to be changed without
 * changing the reference. This is not recommended unless you really need
 * a mutable version of a class.
 * @param <T> the type of object to wrap.
 * @author willharris
 */
public class Mutable<T> {
    
    private T mutable;
    
    /**
     * Gets the current value of this instance and returns it.
     * @return the current value of this instance.
     */
    public T get () {
        return mutable;
    }
    
    /**
     * Sets the current value of this instance. Setting a new value will change
     * the value of this instance everywhere it is used.
     * @param value 
     */
    public void set (T value) {
        mutable = value;
    }
    
    /**
     * Creates a new Mutable wrapper and assigns the internal value to the 
     * encased object's default value.
     */
    public Mutable () {}
    
    /**
     * Creates a new Mutable wrap and assigns the internal value to the
     * specified value.
     * @param value the value to set upon initialization.
     */
    public Mutable (T value) {
        mutable = value;
    } 
}
