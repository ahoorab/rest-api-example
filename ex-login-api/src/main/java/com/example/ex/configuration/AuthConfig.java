package com.example.ex.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;

@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor;

    @Override 
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .addFilter(new AuthFilter(super.authenticationManager(),configurableJWTProcessor))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}