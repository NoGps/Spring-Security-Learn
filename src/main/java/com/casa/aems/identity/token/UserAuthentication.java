package com.casa.aems.identity.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAuthentication implements Authentication {

    private static final long serialVersionUID = -6924972448792058258L;
    private final User user;
    private boolean authenticated = true;
    private List<CasaDomain> domains;

    public UserAuthentication(User user) {
        this.user = user;
        this.domains = new ArrayList<>();
    }

    public UserAuthentication(User user, List<CasaDomain> domains) {
        this(user);
        if (domains != null) {
            this.domains = domains;
        }
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public User getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isRoleDefinedForDomain(String domain, String role) {
        boolean hasRole = false;

        outer: for (CasaDomain d : domains) {
            if (d.getName().equals(domain)) {
                List<String> roles = d.getRoles();
                for (String r : roles) {
                    if (r.equals(role)) {
                        hasRole = true;
                        break outer;
                    }
                }
            }
        }
        return hasRole;
    }    
}
