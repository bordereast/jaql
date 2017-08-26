package com.bordereast.arangorm;

import java.util.ArrayList;
import java.util.List;

import com.bordereast.jaql.arango.annotation.ArangoRelation;

public class Role {

    private String name;
    
    @ArangoRelation(localField="permissions", joinCollection="role_permissions", targetCollection="permission")
    private List<Permission> permissions;
    
    public Role() {
        permissions = new ArrayList<Permission>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
