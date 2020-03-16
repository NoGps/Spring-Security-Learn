package com.casa.aems.identity.utils;

import com.casa.aems.identity.token.CasaDomain;
import com.casa.aems.identity.token.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DomainRoleEvaluator {
    @Autowired
    TokenHandler tokenHandler;

    public DomainRoleEvaluator() {
    }

    public boolean hasPermission(String domain, String role) {
        ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attr.getRequest();
        final String token = request.getHeader("Authorization");
        List<CasaDomain> casaDomains= tokenHandler.parseDomainsFromToken(token.substring(7));
        for(CasaDomain casaDomain : casaDomains){
            if (casaDomain.getName().equals(domain)){
                for(String authority : casaDomain.getRoles()){
                    if (authority.equals(role))
                        return true;
                }
            }
        }

        return false;
    }

}
