package com.casa.aems.identity.token;

import com.casa.aems.identity.exception.CasaAuthenticationException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TokenHandler {
    Authentication getAuthentication(HttpServletRequest request)throws CasaAuthenticationException;
    Authentication getAuthentication(String userName, String password) throws Exception;
    List<CasaDomain> parseDomainsFromToken(String token);
}
