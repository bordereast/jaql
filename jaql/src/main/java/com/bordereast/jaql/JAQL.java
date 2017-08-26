package com.bordereast.jaql;

import com.bordereast.jaql.arango.Keyword;
import com.bordereast.jaql.arango.Logical;
import com.bordereast.jaql.arango.Operator;

import java.util.Map;
import java.util.Optional;

import com.bordereast.jaql.arango.Filter;
import com.bordereast.jaql.arango.For;
import com.bordereast.jaql.arango.ReturnEntity;
import com.bordereast.jaql.arango.ReturnProperty;

public final class JAQL {
    
    private JAQLCollections lists;
    private String[] aliases = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","aa","bb","cc","dd","ee","ff","gg","hh","ii","jj","kk","ll","mm","nn","oo","pp","qq","rr","ss","tt","uu","vv","ww","xx","yy","zz"};
    private int index = -1;
    
    public JAQL() {
        
        lists = new JAQLCollections();
    }
    
    public JAQL(int aliasIndex) {
        super();
        lists = new JAQLCollections();
        index = aliasIndex;
    }
    
    public Map<String, Object> bindVars() {
        return lists.paramList;
    }
  
    public <T extends Object> JAQL forEntity(T entity) {
        
        lists.put(Keyword.FOR, new For<T>(entity, aliases[++index]));
        lists.entityList.put(entity.getClass().getSimpleName(), entity);
        return this;
    }
    
    public JAQL forCollection(String collection) {
        lists.put(Keyword.FOR, new For(collection, aliases[++index]));
        return this;
    }
    
    public <T extends Object> JAQL returnEntity(T entity) throws NoSuchFieldException, SecurityException {
        
        for(Pair p : lists.queryList) {
            if(p.getKey().equals(Keyword.FOR)) {
                For f = (For)p.getValue();
                if(f.entity.getClass().getSimpleName().equals(entity.getClass().getSimpleName())) {
                    lists.put(Keyword.RETURN, new ReturnEntity(entity, f));
                    break;
                }
            }
        }
        
        
        return this;
    }
    
    public JAQL returnProperty(String alias, String property) {
        
        lists.put(Keyword.RETURN, new ReturnProperty(alias, property));
        
        return this;
    }
       
    public JAQL withParam(String parameter, Object value) {
        
        lists.paramList.put(parameter, value);
        
        return this;
    }
    
    public String addParam(String parameter, Object value) {
        
        lists.paramList.put(parameter, value);
        
        return "@" + parameter;
    }
    
    public String addCollectionParam(String parameter, Object value) {
        
        lists.paramList.put(parameter, value);
        
        return "@@" + parameter;
    }
    
    public JAQL filter(String left, Operator operator, String right) {

        lists.put(Keyword.FILTER, new Filter(Logical.BLANK, left, operator, right));
        
        return this;
    }
    
    public JAQL filter(String left, Operator operatorA, Operator operatorB, String right) {
        
        lists.put(Keyword.FILTER, new Filter(Logical.BLANK, left, operatorA, operatorB, right));
        
        return this;
    }
    
    public JAQL and(String left, Operator operator, String right) {
        
        lists.put(Keyword.FILTER, new Filter(Logical.AND, left, operator, right));
        
        return this;
    }
    
    public JAQL and(String left, Operator operatorA, Operator operatorB, String right) {
        
        lists.put(Keyword.FILTER, new Filter(Logical.AND, left, operatorA, operatorB, right));
        
        return this;
    }
    
    public JAQL or(String left, Operator operator, String right) {
        
        lists.put(Keyword.FILTER, new Filter(Logical.OR, left, operator, right));
        
        return this;
    }
    
    public JAQL or(String left, Operator operatorA, Operator operatorB, String right) {
        
        lists.put(Keyword.FILTER, new Filter(Logical.OR, left, operatorA, operatorB, right));
        
        return this;
    }
    
    public JAQL not(String left, Operator operator, String right) {
        
        lists.put(Keyword.FILTER, new Filter(Logical.NOT, left, operator, right));
        
        return this;
    }
    
    public JAQL not(String left, Operator operatorA, Operator operatorB, String right) {
        
        lists.put(Keyword.FILTER, new Filter(Logical.NOT, left, operatorA, operatorB, right));
        
        return this;
    }
    
    public JAQL logical(Logical logical) {
        
        lists.put(Keyword.LOGICAL, logical);
        
        return this;
    }
    
    public JAQL keyword(Keyword keyword) {

        lists.put(keyword, null);
        
        return this;
    }
    
    public String currentAlias() {
        return aliases[index];
    }
    
    
    public String build() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
        
        return new JAQLBuilder(lists, aliases, index).build().replaceAll(" {2,}", " ");
    }
    
    
}