package com.bordereast.jaql.arango;

public enum Operator {
    equals (" == "),
    notEquals (" != "),
    lessThan (" < "),
    lessOrEqual (" <= "),
    greaterThan (" > "),
    greaterOrEqual (" >= "),
    in (" IN "),
    notIn (" NOT IN "),
    like (" LIKE "),
    equalsRegex (" =~ "),
    notEqualsRegex (" !~ "),
    all (" ALL "),
    none (" BLANK "),
    any (" ANY "),
    blank ("")
    ;
    
    private final String name;       

    private Operator(String s) {
        name = s;
    }
    
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
