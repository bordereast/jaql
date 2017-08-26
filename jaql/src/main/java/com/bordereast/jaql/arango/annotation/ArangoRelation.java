package com.bordereast.jaql.arango.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Andrew Grothe
 * 
 * Relation by link table
 * Relation by linked fields
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ArangoRelation {

    String targetCollection();
    String joinCollection();
    String localField();
    
}