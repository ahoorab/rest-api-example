package com.example.ex.model;

import java.util.ArrayList;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.nimbusds.jwt.JWTClaimsSet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AWS identity token for security context
 *
 * @property token raw token
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class CognitoAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -6433985285747848939L;

    private String credentials;
    private JWTClaimsSet principal;
   
    public CognitoAuthenticationToken(String token, JWTClaimsSet claims) {
        super(new ArrayList<>());
        super.setDetails(claims);
        super.setAuthenticated(true);
        this.credentials = token;
        this.principal = claims;
    }

}