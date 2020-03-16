package com.casa.aems.identity.token;

import com.casa.aems.identity.config.CasaIdentityConfigAdapter;
import com.casa.aems.identity.exception.CasaAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class JwtTokenHandler implements TokenHandler {
    @Autowired
    JwtProperties jwtProperties;

    private static final Logger log = LoggerFactory.getLogger(JwtTokenHandler.class);

    private AuthenticationManager authManager;

    @Autowired
    CasaIdentityConfigAdapter casaIdentityConfigAdapter;

    public JwtTokenHandler() {

    }

    public User parseUserFromToken(String token) {
        List<CasaDomain> casaDomains = parseDomainsFromToken(token);
        if(CollectionUtils.isEmpty(casaDomains)){
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(CasaDomain domain : casaDomains){
            authorities.add((GrantedAuthority) () -> "ROLE_" + domain.getName());
            for (String item : domain.getRoles()){
                authorities.add((GrantedAuthority) () -> item);
            }
        }
        String userName = parseUserNameFromToken(token);

        User user = new User(userName, "[PASSWORD]", authorities);

        return user;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) throws CasaAuthenticationException {
        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");
        try {
            if (StringUtils.isNotBlank(token)) {
                final User user = parseUserFromToken(token);
                if (user != null) {
                    List<CasaDomain> domains = parseDomainsFromToken(token);
                    return new UserAuthentication(user, domains);
                }
            }
            return null;
        }
        catch (Exception e) {
            log.error("Failed to authorize user");
            throw new CasaAuthenticationException("Requires authentication", e);
        }
    }

    @Override
    public Authentication getAuthentication(String userName, String password) throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userName, password);
        if(authManager == null){
            authManager = casaIdentityConfigAdapter.authenticationManagerBean();
        }
        auth = authManager.authenticate(auth);

        return auth;
    }

    @SuppressWarnings("unchecked")
    public List<CasaDomain> parseDomainsFromToken(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSigningKey())
                .parseClaimsJws(token);

        List<Map<String, Object>> domainsArr = (List<Map<String, Object>>) claims.getBody()
                .get("domains");

        List<CasaDomain> domains = new ArrayList<>();
        for (Map<String, Object> d : domainsArr) {
            String name = (String) d.get("name");
            List<String> roles = (List<String>) d.get("roles");

            CasaDomain cd = new CasaDomain();
            cd.setName(name);
            cd.getRoles().addAll(roles);
            domains.add(cd);
        }
        return domains;
    }

    private String parseUserNameFromToken(String token){
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSigningKey())
                .parseClaimsJws(token);
        String userName = claims.getBody().getSubject();

        return userName;
    }
}
