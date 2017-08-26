package com.bordereast.jaql.arango;

public enum Keyword {
    /* Arango Keywords */
    FOR (" FOR "),
    RETURN (" RETURN "),
    FILTER (" FILTER "),
    SORT (" SORT "),
    LIMIT (" LIMIT "),
    LET (" LET "),
    COLLECT (" COLLECT "),
    INSERT (" INSERT "),
    UPDATE (" UPDATE "),
    REPLACE (" REPLACE "),
    REMOVE (" REMOVE "),
    UPSERT (" UPSERT "),
    FLATTEN (" FLATTEN "),
    /* Internal Keywords */
    LOGICAL ( " _LOGICAL_ " )
    ;
    
    private final String name;       

    private Keyword(String s) {
        name = s;
    }
    
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
