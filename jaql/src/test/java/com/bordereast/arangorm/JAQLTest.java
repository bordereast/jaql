package com.bordereast.arangorm;

import org.junit.Test;

import com.bordereast.jaql.JAQL;
import com.bordereast.jaql.arango.Logical;
import com.bordereast.jaql.arango.Operator;

import static org.junit.Assert.*;

import java.util.Map.Entry;

/**
 * Unit test for ArangoORM.
 */
public class JAQLTest {
    /**
     * Rigorous Test.
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Test
    public void testSimpleQuery() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
        
        JAQL query = new JAQL();
        User user = new User();
        
        String q = query
            .forEntity(user)
            .filter("firstName", Operator.equals, "@f1")
            .logical(Logical.AND)
            .logical(Logical.OPEN_BRACKET)
            .filter("lastName", Operator.notEquals, "@f2")
            .and("lastName", Operator.equals, query.addParam("f3", "Vader")) // can add param like this
            .logical(Logical.CLOSE_BRACKET)
            .returnEntity(user)
            .withParam("f1", "Darth") // or add param like this
            .withParam("f2", "Skylord")
            .build();
        
        System.out.println(q);
        assertNotNull("query should not be null", q);

    }
    
    @Test
    public void testParameters() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
        JAQL query = new JAQL();
        User user = new User();
        
        String q = query
            .forEntity(user)
            .filter("firstName", Operator.equals, "@f1")
            .logical(Logical.AND)
            .logical(Logical.OPEN_BRACKET)
            .filter("lastName", Operator.notEquals, "@f2")
            .and("lastName", Operator.equals, "@f3")
            .logical(Logical.CLOSE_BRACKET)
            .returnEntity(user)
            .withParam("f1", "Darth")
            .withParam("f2", "Skylord")
            .withParam("f3", "Vader")
            .build();

        for(Entry<String, Object> entry : query.bindVars().entrySet()) {
            assertTrue(String.format("query doens't contain this parameter '%s'.", entry.getKey()), q.contains("@" + entry.getKey()));
        }

    }
}
