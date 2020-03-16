package com.casa.aems.identity.config;

import com.casa.aems.identity.filter.StatelessAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CasaIdentityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private CasaSecurityConfig casaSecurityConfig;

    @Autowired(required = false)
    UserDetailsService userDetailsService;

    @Autowired(required = false)
    StatelessAuthenticationFilter statelessAuthenticationFilter;

    public CasaIdentityConfigAdapter() {
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (this.userDetailsService != null) {
            auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder(10));
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurity security = http.addFilterBefore(this.statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        if (this.casaSecurityConfig != null) {
            this.casaSecurityConfig.configure(security);
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        if (this.casaSecurityConfig != null) {
            this.casaSecurityConfig.configure(web);
        }
    }
}


