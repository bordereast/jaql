# JAQL (Jackal)
ArangoDB AQL Query Builder for Java 8+

```
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
```            
