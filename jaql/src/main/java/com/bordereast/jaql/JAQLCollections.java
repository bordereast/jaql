package com.bordereast.jaql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bordereast.jaql.arango.Filter;
import com.bordereast.jaql.arango.Keyword;

public final class JAQLCollections {

    protected Map<String, Object> paramList;
    protected Map<String, Object> entityList;
    protected List<Filter> filterList;
    protected List<Pair> queryList;
    
    public JAQLCollections() {
        
        paramList = new HashMap<String, Object>();
        
        entityList = new HashMap<String, Object>();
        
        filterList = new ArrayList<Filter>();
        
        queryList = new ArrayList<Pair>();
    }
    
    public void put(Keyword keyword, Object object) {

        queryList.add(new Pair(keyword, object));
    }
    
}
