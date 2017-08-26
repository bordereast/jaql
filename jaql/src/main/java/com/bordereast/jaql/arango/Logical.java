package com.bordereast.jaql.arango;

public enum Logical {
    AND (" && "),
    OR (" || "),
    NOT (" NOT "),
    BLANK (""),
    OPEN_BRACKET (" ( "),
    CLOSE_BRACKET (" ) ")
    ;
    
    private final String name;       

    private Logical(String s) {
        name = s;
    }
    
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
