package com.bordereast.jaql;

import com.bordereast.jaql.arango.Keyword;

public class Pair {
    private Keyword key;
    private Object value;
    
    public Pair(Keyword key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Keyword getKey() {
        return key;
    }

    public void setKey(Keyword key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    
}
