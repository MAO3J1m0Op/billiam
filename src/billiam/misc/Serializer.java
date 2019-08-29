/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.misc;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
       
/**
 * Static class that stores methods to quickly serialize an object.
 * @author willharris
 */
public class Serializer {
    
    /**
     * Serializes the provided object into a byte stream and writes the stream
     * to a given file.
     * @param obj the object to serialize.
     * @param path the path to the file where the byte stream will be stored.
     * @throws FileNotFoundException if the path provided leads to an invalid
     * or nonexistent file.
     * @throws IOException if an I/O error occurs when executing this method.
     */
    public void Serialize (Serializable obj, String path) 
            throws FileNotFoundException, IOException {
        
        // Declares streams 
        FileOutputStream fstream = new FileOutputStream(path);
        ObjectOutputStream objstream = new ObjectOutputStream(fstream); 

        // Method for serialization of object 
        objstream.writeObject(obj); 

        objstream.close(); 
        fstream.close(); 
    }
    
    /**
     * Deserializes an object from the provided path and returns the
     * deserialized object.
     * @param path the path to the serialized object.
     * @return the object after deserialization.
     * @throws FileNotFoundException if the path provided leads to an invalid
     * or nonexistent file.
     * @throws IOException if an I/O error occurs when executing this method.
     * @throws ClassNotFoundException if the type of the deserialized object
     * cannot be found in the dependencies of the application.
     */
    public Serializable Deserialize (String path) 
            throws FileNotFoundException, IOException, ClassNotFoundException {
        
        // Declares streams
        FileInputStream fstream = new FileInputStream(path); 
        ObjectInputStream objstream = new ObjectInputStream(fstream); 

        // Writes the object to return value
        Serializable value = (Serializable)objstream.readObject(); 

        objstream.close(); 
        fstream.close();

        return value;
    }
    
    // Prevents creating a new instance of this class, as it only has static
    // fields.
    private Serializer() {}
}
