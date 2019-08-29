/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.misc;

/**
 * Represents a collection of data and contains several methods allowing for
 * operations with this data.
 * @author willharris
 */
public class DataSet implements java.io.Serializable {
    
    /**
     * The data of this instance.
     */
    private final Double[] data;
    
    /**
     * Initializes a DataSet using an array of doubles. The array is sorted
     * before it is applied to the instance.
     * @param data the data of this instance.
     */
    public DataSet (double[] data) {
        
        // Converts primitive data into wrapper data
        java.util.ArrayList<Double> newData = new java.util.ArrayList<>();
        for (int n = 0; n < data.length; ++n) newData.add(data[n]);
        
        // Rewrite Collection constructor.
        this.data = newData.toArray(new Double[1]);
        java.util.Arrays.sort(this.data);
    }
    
    /**
     * Initializes a DataSet using an array of doubles. The array is sorted
     * before it is applied to the instance.
     * @param data the data of this instance.
     */
    public DataSet (Double[] data) {
        this.data = data;
        java.util.Arrays.sort(this.data);
    }
    
    /**
     * Initializes a DataSet using a Collection of doubles. The collection is
     * converted to an array and sorted before it is applied to the instance.
     * @param data 
     */
    public DataSet (java.util.Collection<Double> data) {
        this(data.toArray(new Double[1]));
    }
    
    /**
     * Gets and returns the number of elements in this DataSet.
     * @return the number of elements in this DataSet.
     */
    public int length() {
        return data.length;
    }
    
    /**
     * Returns the combined sum of all the elements in this DataSet.
     * @return the combined sum of all the elements in this DataSet.
     */
    public double sum() {
        
        double value = 0;
        
        for (int n = 0; n < data.length; ++n) value += data[n];
        
        return value;
    }
    
    /**
     * Calculates the mean, or average of the data in this instance.
     * @return the mean, or average of the data in this instance.
     */
    public double mean () {
        return sum() / (double)data.length;
    }
    
    /**
     * Returns the value at the specified quartile.
     * @param quartile the quartile to get, where 0 is minimum and 4 is maximum.
     * @return the specified quartile of the DataSet.
     */
    public double quartile (int quartile) {
        
        // Ensures the quartile argument is valid.
        if (quartile > 4 || quartile < 0) throw new IllegalArgumentException(
                "Argument \"quartile\" out of range. Expected 0, 1, 2, 3, 4;"
                + "got " + quartile + "."
        );
        
        // The number of elements apart each quartile is from each other.
        // EXAMPLE WITH 9 ELEMENTS:
        //   0   1   2   3   4   5   6   7   8   -  quartileDataSize = 2
        //  MIN      Q1     MED     Q3      MAX
        //
        // EXAMPLE WITH 7 ELEMENTS:
        //   0   1   2   3   4   5   6           -  quartileDataSize = 1.5
        //  MIN    Q1   MED   Q3    MAX
        double quartileDataSize = (data.length - 1) / 4;
        
        // The value to the left of the decimal place in qds
        int qdsInt;
        // The value to the right of the decimal place in qds. qds will always
        // be quartered, so the value will always be the numerator of a 
        // fraction over 4.
        int qdsQuarter;
        
        // Checks the trailing digits of the quartileDataSize and assigns to 
        // above values.
        qdsInt = (int)quartileDataSize;
        double decimalPart = quartileDataSize - qdsInt;
        
        if (decimalPart > 0.24 && decimalPart < 0.26) qdsQuarter = 1;
        else if (decimalPart > 0.49 && decimalPart < 0.51) qdsQuarter = 2;
        else if (decimalPart > 0.74 && decimalPart < 0.76) qdsQuarter = 3;
        else qdsQuarter = 0;
        
        // Two values quartile could be sandwiched between
        int leftBound = qdsInt;
        int rightBound = qdsInt + 1;
        
        // Determines which of the bounds to use, and returns the value.
        switch (qdsQuarter) {
            case 0:
            case 1:
                // Right on the money!
                return data[leftBound];
            case 2:
                // Average of two values. Let's be recursive!
                return new DataSet(
                        new Double[] { data[leftBound], data[rightBound] }
                ).mean();
            case 3:
                // It's right on the money, but on the right bound.
                return data[rightBound];
            default:
                // If this runs, I don't know what happened.
                throw new IllegalArgumentException("Internal variable "
                        + "\"qdsQuarter\" was unexpected value of" + qdsQuarter 
                        + ".");
        }
    }
    
    /**
     * Gets the minimum value, or quartile 0, by calling the {@code quartile()}
     * method.
     * @return the minimum value, or lowest value, of the DataSet.
     */
    public double min () {
        return quartile(0);
    }
    
    /**
     * Gets the maximum value, or quartile 4, by calling the {@code quartile()}
     * method.
     * @return the maximum value, or highest value, of the DataSet.
     */
    public double max () {
        return quartile(0);
    }
    
    /**
     * Gets the range (max - min) of this DataSet.
     * @return the range of this DataSet.
     */
    public double range () {
        return max() - min();
    }
    
    /**
     * Gets the inter-quartile range (Q3 - Q1) of this DataSet.
     * @return the inter-quartile range of this DataSet.
     */
    public double iqr () {
        return quartile(3) - quartile(1);
    }
}
