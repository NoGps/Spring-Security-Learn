package com.casa.aems.identity.config;

import com.casa.aems.identity.filter.StatelessAuthenticationFilter;
import com.casa.aems.identity.token.JwtProperties;
import com.casa.aems.identity.token.JwtTokenHandler;
import com.casa.aems.identity.token.TokenHandler;
import com.casa.aems.identity.utils.DomainRoleEvaluator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class CasaSecurityConfig {
    @Bean
    CasaIdentityConfigAdapter casaIdentityConfigAdapter(){
        return new CasaIdentityConfigAdapter();
    }

    @Bean
    StatelessAuthenticationFilter statelessAuthenticationFilter(){
        return new StatelessAuthenticationFilter();
    }

    @Bean
    TokenHandler tokenHandler(){
        return new JwtTokenHandler();
    }

    @Bean("roleEvaluator")
    DomainRoleEvaluator roleEvaluator(){
        return new DomainRoleEvaluator();
    }

    @ConfigurationProperties(prefix = "identity.jwt")
    @Bean
    JwtProperties jtwProperties() {
        return new JwtProperties();
    }

    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .anyRequest().authenticated();
    };

    public void configure(WebSecurity web) throws Exception{

    };
}
