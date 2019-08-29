/*
 * Billiam Library is a collection of packages and classes
 * created by Will Harris.
 */
package billiam.cmd;

/**
 *
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
    private Object arg = null;

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
     * @param test the passed String to test.
     * @return true if the passed String is a valid literal, otherwise
     * false.
     */
    public abstract boolean isType(String test);

    /**
     * Converts a literal to an object.
     * @param obj the literal to convert.
     * @return the converted object.
     */
    public abstract Object convert(String obj);

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
     * @param value the value to pass to this parameter.
     */
    public final void pass (String value) {
        if (isType(value)) {
            arg = convert(value);
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
     * @param name the name of the created parameter.
     * @return a parameter of type int.
     */
    public static Parameter intType (String name) {
        return new Parameter(name) {

            @Override
            public int priority () { return 2; }

            @Override
            public boolean isType (String test) {
                return test.matches("/\\d+/");
            }

            @Override
            public Integer convert (String obj) {
                return Integer.parseInt(obj);
            }

            @Override
            public Class classType () { return Integer.class; }

            @Override
            public String type () { return "int"; }
        };
    }

    /**
     * Returns a parameter of type String.
     * @param name the name of the created parameter.
     * @return a parameter of type String.
     */
    public static Parameter strType (String name) {
        return new Parameter(name) {

            @Override
            public int priority () { return 0; }

            @Override
            public boolean isType (String test) {
                return true;
            }

            @Override
            public String convert (String obj) {
                return obj;
            }

            @Override
            public Class classType () { return String.class; }

            @Override
            public String type () { return "str"; }
        };
    }

    /**
     * Returns a parameter of type double. To convert from string,
     * Double.parseDouble() will be called when pass() is called.
     * @param name the name of the created parameter.
     * @return a parameter of type double.
     */
    public static Parameter floatType (String name) {
        return new Parameter(name) {

            @Override
            public int priority () { return 1; }

            @Override
            public boolean isType (String test) {
                return test.matches("/\\d+(\\.\\d+)?/");
            }

            @Override
            public Double convert (String obj) {
                return Double.parseDouble(obj);
            }

            @Override
            public Class classType () { return Double.class; }

            @Override
            public String type () { return "float"; }
        };
    }

    /**
     * Returns a parameter of type switch. A switch is a keyword that
     * deviates a same keyword into different functions. For example, 
     * given a command "scoreboard" could have 2 functions: objects and
     * players.
     * @param keyword the keyword that represents the official type.
     * @return a parameter of type switch.
     */
    public static Parameter switchType (String keyword) {
        return new Parameter(keyword) {

            @Override
            public int priority () { return 3; }

            @Override
            public boolean isType (String test) {
                return test.equals(type());
            }

            @Override
            public String convert (String obj) {
                return obj;
            }

            @Override
            public Class classType () { return String.class; }

            @Override
            public String type () { return "switch<" + name + ">"; }

            @Override
            public String toString () {
                return name;
            }
        };
    }

    /**
     * Returns a parameter of type array. An array is parsed using multiple
     * arguments until a designated end word is found. Once this end word
     * is found, the array is compiled and made into one argument with all
     * the previous arguments.
     * @param endWord the word that must be used to mark the end of the
     * array.
     * @param subtype the expected Parameter type of the created array.
     * @return a parameter of type array.
     */
    public static Parameter arrayType (String endWord, Parameter subtype) {
        return new Parameter(endWord) {

            public final Parameter subType = subtype;

            @Override
            public int priority () { return 4; }

            /**
             * This method should not be called, as CommandLine already
             * knows how to check for arrays.
             * @param test the test string.
             * @return false.
             */
            @Override
            public boolean isType (String test) {
                return false;
            }

            @Override
            public Double convert (String obj) {
                return Double.parseDouble(obj);
            }

            @Override
            public Class classType () { 
                return java.lang.reflect.Array.class;
            }

            @Override
            public String type () { 
                return "array<" + subType.type() + ">";
            }

            @Override
            public String toString () {
                return subType.toString() + "... " + name;
            }
        };
    }
}
