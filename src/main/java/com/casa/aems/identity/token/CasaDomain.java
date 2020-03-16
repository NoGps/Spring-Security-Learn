package com.casa.aems.identity.token;

import java.util.ArrayList;
import java.util.List;

public class CasaDomain {

    private String name;

    private List<String> roles;

    public CasaDomain() {
        roles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "CasaDomain [name=" + name + ", roles=" + roles + "]";
    }
}
