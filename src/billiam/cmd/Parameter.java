/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.cmd;

import java.util.ArrayList;
import billiam.misc.Mutable;

/**
 * Stores information about a type of a parameter, and can have an object passed
 * to it.
 * @author willharris
 */
public abstract class Parameter {
        
    /**
     * The identifying name of the parameter.
     */
    public final String name;

    /**
     * When pass() is called, this value will be assigned to.
     */
    Object arg = null;

    /**
     * Gets the assigned value for this parameter. A value is assigned via
     * pass().
     * @return the assigned value for this parameter.
     */
    public Object getPassedValue () { return arg; }

    /**
     * Returns the priority of parsing. If a literal matches 2 or more of a
     * type, the literal will be converted to the type with the highest
     * priority.
     * @return the Parameter type's priority.
     */
    public abstract int priority();

    /**
     * Tests if the passed String is a valid literal of that type.
     * @param command the passed String[] containing the argument to test.
     * @param testIndex the index of the argument to test.
     * @return true if the passed String is a valid literal, otherwise
     * false.
     */
    public abstract boolean isType(String[] command, Mutable<Integer> testIndex);

    /**
     * Converts a literal to an object.
     * @param command the String array containing the literal to convert.
     * @param index the index of the literal to convert.
     * @return the converted object.
     */
    public abstract Object convert(String[] command, Mutable<Integer> index);

    /**
     * Returns the actual type of the passed value as a class. This method
     * should not reference any fields.
     * @return a Class instance representing type of the passed value.
     */
    public abstract Class classType();

    /**
     * Returns the nickname of the type of this parameter.
     * @return a String with the nickname of the type.
     */
    public abstract String type();

    /**
     * Passes a value to this Parameter. This value will be stored until
     * pass() is called again.
     * @param command the String array containing the argument to pass to this
     * parameter.
     * @param indexToPass the index of the argument to pass.
     */
    public final void pass (String[] command, Mutable<Integer> indexToPass) {
        if (isType(command, indexToPass)) {
            arg = convert(command, indexToPass);
        } else throw new IllegalArgumentException("Argument passed does "
                + "not match the required type of this Parameter.");
    }

    @Override
    public final boolean equals (Object other) {

        if (this == other) return true;
        if (other == null) return false;

        if (other instanceof Parameter) {
            Parameter castedOther = (Parameter)other;

            return this.type().equals(castedOther
                    .type());
        }

        return false;
    }

    @Override
    public String toString () {
        return "<" + name + ": " + type() + ">";
    }

    @Override
    public int hashCode () {
        return type().hashCode();
    }

    private Parameter (String name) {
        this.name = name;
    }

    /**
     * Returns a parameter of type int. To convert from string,
     * Integer.parseInt() will be called when pass() is called.
     */
    public static class IntType extends Parameter {
    
        /**
         * Creates a new parameter of type int.
         * @param name the name of the created parameter.
         */
        public IntType (String name) {
            super(name);
        }

        @Override
        public int priority () { return 2; }

        @Override
        public boolean isType (String[] command, Mutable<Integer> test) {
            return command[test.get()].matches("/\\d+/");
        }

        @Override
        public Integer convert (String[] command, Mutable<Integer> index) {
            return Integer.parseInt(command[index.get()]);
        }

        @Override
        public Class classType () { return Integer.class; }

        @Override
        public String type () { return "int"; }
    }

    /**
     * A parameter of type String.
     */
    public static class StrType extends Parameter {
        
        /**
         * Creates a new parameter of type String.
         * @param name the name of the created parameter.
         */
        public StrType (String name) {
            super(name);
        }

        @Override
        public int priority () { return 0; }

        @Override
        public boolean isType (String[] command, Mutable<Integer> test) {
            return true;
        }

        @Override
        public String convert (String[] command, Mutable<Integer> obj) {
            return command[obj.get()];
        }

        @Override
        public Class classType () { return String.class; }

        @Override
        public String type () { return "str"; }
    }

    /**
     * A parameter of type double. To convert from string, Double.parseDouble()
     * will be called when pass() is called.
     */
    public static class FloatType extends Parameter {
        
        /**
         * Creates a parameter of type double.
         * @param name the name of the created parameter.
         */
        public FloatType(String name) {
            super(name);
        }

        @Override
        public int priority () { return 1; }

        @Override
        public boolean isType (String[] command, Mutable<Integer> test) {
            return command[test.get()].matches("/\\d+(\\.\\d+)?/");
        }

        @Override
        public Double convert (String[] command, Mutable<Integer> obj) {
            return Double.parseDouble(command[obj.get()]);
        }

        @Override
        public Class classType () { return Double.class; }

        @Override
        public String type () { return "float"; }
    }

    /**
     * Returns a parameter of type switch. A switch is a keyword that
     * deviates a same keyword into different functions. For example, 
     * given a command "scoreboard" could have 2 functions: objectives and
     * players.
     */
    public static class SwitchType extends Parameter {
        
        /**
         * Creates a parameter of type Switch.
         * @param keyword the keyword that represents the official type.
         */
        public SwitchType (String keyword) {
            super(keyword);
        }

        @Override
        public int priority () { return 3; }

        @Override
        public boolean isType (String[] command, Mutable<Integer> test) {
            return command[test.get()].equals(type());
        }

        @Override
        public String convert (String[] command, Mutable<Integer> obj) {
            return command[obj.get()];
        }

        @Override
        public Class classType () { return String.class; }

        @Override
        public String type () { return "switch<" + name + ">"; }

        @Override
        public String toString () {
            return name;
        }
    }

    /**
     * A parameter of type array. An array is parsed using multiple
     * arguments until a designated end word is found. Once this end word
     * is found, the array is compiled and made into one argument with all
     * the previous arguments.
     */
    public static class ArrayType extends Parameter {
            
        /**
         * Creates a new parameter of type array.
         * @param endWord the word that must be used to mark the end of the
         * array.
         * @param subType the expected Parameter type of the created array.
         */
        public ArrayType (String endWord, Parameter subType) {
            super(endWord);
            this.subType = subType;
        }

        public final Parameter subType;

        @Override
        public int priority () { return 4; }

        @Override
        public boolean isType (String[] command, Mutable<Integer> test) {
            
            for (int n = test.get(); n < command.length; ++n) {
                
                // If it matches the end word
                if (command[n].equals(name)) return true;
                
                // Arg doesn't match subtype.
                if (!(subType.isType(command, new Mutable<>(n)))) return false;
            }
            
            // End of command before endword found.
            return false;
        }

        @Override
        public ArrayList convert (String[] command, Mutable<Integer> startIndex)
        {
            // The value can be casted to the subtype later. Reflection is bad.
            ArrayList value = new ArrayList();
            
            for (int n = startIndex.get(); n < command.length; ++n) {
                
                startIndex.set(n);
                
                // If it matches the end word
                if (command[n].equals(name)) return value;
                
                // Arg doesn't match subtype.
                value.add(subType.convert(command, startIndex));
                
                n = startIndex.get();
            }
            
            throw new IllegalArgumentException("End of command found before "
                    + "endword found.");
        }

        @Override
        public Class classType () { 
            return ArrayList.class;
        }

        @Override
        public String type () {
            return "array<" + subType.type() + ">";
        }

        @Override
        public String toString () {
            return subType.toString() + "... " + name;
        }
    }
}
