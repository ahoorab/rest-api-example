package com.example.ex.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.example.ex.model.CognitoAuthenticationToken;

public class AuthFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    private ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor;

    public AuthFilter(AuthenticationManager authenticationManager, ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor) {
        super(authenticationManager);
        this.configurableJWTProcessor = configurableJWTProcessor;
    }
    
    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            String token = extractToken(req.getHeader("Authorization"));
            CognitoAuthenticationToken authentication = extractAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (AccessDeniedException e) {
            accessDenied(res,e,"Access denied - " + e.getMessage());
        }
    }
    
    private void accessDenied(HttpServletResponse res, Exception e, String message) throws IOException {
        LOGGER.error("Access denied",e);
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(message);
    }

    /**
     * Extract token from header
     */
    private String extractToken(String header) {
        String[] headers = header != null ? header.split("Bearer ") : null;

        if (headers == null || headers.length < 2) {
            return null;
        } else {
            return headers[1];
        }
    }

    /**
     * Extract authentication details from token
     */
    private CognitoAuthenticationToken extractAuthentication(String token) {
        if (token == null) {
            return null;
        }

        try {
            JWTClaimsSet claims = configurableJWTProcessor.process(token, null);
            return new CognitoAuthenticationToken(token, claims);
        } catch (Exception e) {
            throw new AccessDeniedException(e.getMessage(),e);
        }
    }
    
}