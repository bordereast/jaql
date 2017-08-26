package com.bordereast.arangorm;

import java.util.ArrayList;
import java.util.List;

import com.bordereast.jaql.arango.annotation.ArangoRelation;

public class User {
    

    private String firstName;
    private String lastName;
    
    @ArangoRelation(localField="roles", joinCollection="user_roles", targetCollection="role")
    private List<Role> roles;
    
    @ArangoRelation(localField="permissions", joinCollection="user_permissions", targetCollection="permission")
    private List<Permission> permissions;
    
    public User() {
        roles = new ArrayList<Role>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    
}
