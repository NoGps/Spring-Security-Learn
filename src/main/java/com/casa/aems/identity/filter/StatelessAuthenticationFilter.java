package com.casa.aems.identity.filter;

import com.casa.aems.identity.token.TokenHandler;
import com.casa.aems.identity.exception.CasaAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessAuthenticationFilter extends GenericFilterBean {
    @Autowired
    private TokenHandler tokenHandler;

    private static final Logger log = LoggerFactory
            .getLogger(StatelessAuthenticationFilter.class);

    public StatelessAuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException {

        try {
            Authentication authentication = tokenHandler
                    .getAuthentication((HttpServletRequest) req);
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            chain.doFilter(req, res); // always continue
            // Do we need to unset authentication, like below?
            // SecurityContextHolder.getContext().setAuthentication(null);
        }
        catch (CasaAuthenticationException e) {
            log.error(e.getMessage(), e.getMessage());
            ((HttpServletResponse) res)
                    .sendError(e.getHttpStatus().value(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ((HttpServletResponse) res)
                    .sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
