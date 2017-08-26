package com.bordereast.jaql;

import com.bordereast.jaql.arango.Logical;
import com.bordereast.jaql.arango.Operator;
import com.bordereast.jaql.arango.Relation;
import com.bordereast.jaql.arango.ReturnEntity;
import com.bordereast.jaql.arango.ReturnProperty;
import com.bordereast.jaql.arango.Filter;
import com.bordereast.jaql.arango.For;
import com.bordereast.jaql.arango.Keyword;

public final class JAQLBuilder {

    private final JAQLCollections lists;
    private StringBuilder query;
    private boolean isBracketOpen = false;
    private String[] aliases;
    private int index;
    
    public JAQLBuilder(JAQLCollections lists, String[] aliases, int index) {
        
        query = new StringBuilder("");
        
        this.lists = lists;
        this.aliases = aliases;
        this.index = index;
        
    }
    
    public String build() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
        
        for (Pair entry : lists.queryList) {
            switch(entry.getKey()) {
            case FOR:
                addFor(query, (For) entry.getValue());
                break;
            case FILTER:
                addFilter(query, (Filter) entry.getValue());
                break;
            case LOGICAL:
                addLogical(query, (Logical) entry.getValue());
                break;
            case RETURN:
                if(entry.getValue().getClass().isAssignableFrom(ReturnProperty.class)) {
                    addReturnProperty(query, (ReturnProperty)entry.getValue());
                } else {
                    addReturnEntity(query, (ReturnEntity) entry.getValue());
                }
                
                break;
            default:
                if(entry.getValue() == null) {
                    addKeyword(query, entry.getKey());
                }
                break;
            }
        }
        
        return query.toString();
    }
    
    private void addReturnProperty(StringBuilder s, ReturnProperty value) {
        
        s.append(newLine());
        s.append(String.format(" RETURN %s.%s", value.alias, value.property));
        
    }

    private void addKeyword(StringBuilder s, Keyword keyword) {
        s.append(String.format(" %s ",keyword.name()));
    }
    
    private void addReturnEntity(StringBuilder s, ReturnEntity value) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
        s.append(newLine());
        
        if(value.relations.size() > 0) {
            s.append(String.format(" RETURN MERGE (%s, {", value.alias));
            s.append(newLine());
            
            for(int k = 0; k < value.relations.size(); k++) {
                Relation relation = (Relation) value.relations.get(k);
                JAQL j = new JAQL(++index);
               // ++index; // skip so we don't end up with clashing inner aliases
                JAQL i = new JAQL(++index);
                
                String innerQuery = i
                        .forCollection(relation.arangoRelation.joinCollection())
                        .filter(i.currentAlias() + "._key", Operator.equals, value.alias + "._key")
                        .returnProperty(i.currentAlias(), relation.arangoRelation.localField())
                        .build();
                
                String query = j
                        .forEntity(relation.entityClass.newInstance())
                        .filter(j.currentAlias() + "._key", Operator.in, String.format("%s(%s)", Keyword.FLATTEN.name(), innerQuery))
                        .returnEntity(relation.entityClass.newInstance())
                        .build();
                s.append(" ");
                s.append(String.format("%s: (%s)", relation.arangoRelation.localField(), query.replaceAll(newLine(), "")));
                
                if(value.relations.size() > 1 && k < (value.relations.size()-1)) {
                    s.append(",");
                }
                
                s.append(newLine());
            }
            
            s.append("})");
        } else {
            s.append(String.format(" RETURN %s", value.alias));
        }
        
        
    }

    private void addLogical(StringBuilder s, Logical logical) {
        s.append(logical.toString());
        
        if(logical.equals(Logical.OPEN_BRACKET)) {
            isBracketOpen = true;
        } else if(logical.equals(Logical.CLOSE_BRACKET)) {
            isBracketOpen = false;
        }
    }

    private void addFilter(StringBuilder s, Filter filter) {
        if(!isBracketOpen) {
            s.append(newLine());
        }
        
        if(filter.logcial.equals(Logical.BLANK)) {
            s.append(String.format("%s %s %s %s %s", 
                    isBracketOpen ? "" : "FILTER" , filter.left, filter.operatorA.toString(), filter.operatorB.toString(), filter.right)); 
        } else {
            s.append(String.format("%s %s %s %s %s", 
                    filter.logcial, filter.left, filter.operatorA.toString(), filter.operatorB.toString(), filter.right)); 
        }
    }

    public void addFor(StringBuilder s, For f) {
        if(f.collection == null) {
            s.append(String.format("FOR %s IN %s ", f.alias, f.entity.getClass().getSimpleName().toLowerCase()));
        } else {
            s.append(String.format("FOR %s IN %s ", f.alias, f.collection));
        }
        
    }
    
    public String newLine() {
        return System.getProperty("line.separator");
    }
}
