package com.bordereast.jaql.arango;

public final class For<T> {

    public T entity;
    public String alias;
    public String collection;
    
    public For(T entity, String alias) {
        this.entity = entity;
        this.alias = alias;
        this.collection = null;
    }
    
    public For(String collection, String alias) {
        this.collection = collection;
        this.alias = alias;
        this.entity = null;
    }
    
}
