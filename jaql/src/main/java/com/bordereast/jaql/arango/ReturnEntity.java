package com.bordereast.jaql.arango;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.bordereast.jaql.arango.annotation.ArangoRelation;

public final class ReturnEntity<T> {

    public T entity;
    public String alias;
    public For<T> forEntity;
    public List<Relation> relations;
    
   
    @SuppressWarnings("unchecked")
    public ReturnEntity(T entity, For<T> forEntity) throws NoSuchFieldException, SecurityException {
        this.entity = entity;
        this.alias = forEntity.alias;
        this.forEntity = forEntity;
        relations = new ArrayList<Relation>();
        
        List<Field> fields  = FieldUtils.getFieldsListWithAnnotation(entity.getClass(), ArangoRelation.class);
        
        for(Field f : fields) {
            Field field = entity.getClass().getDeclaredField(f.getName());
            ArangoRelation relation = field.getAnnotation(ArangoRelation.class);
            
            ParameterizedType fieldListType = (ParameterizedType)field.getGenericType();
            Class<?> clazz = (Class<?>) fieldListType.getActualTypeArguments()[0];

            Relation r = new Relation();
            r.arangoRelation = relation;
            r.entityClass = clazz;
            relations.add(r);
        }
    }
    
    
    
}
