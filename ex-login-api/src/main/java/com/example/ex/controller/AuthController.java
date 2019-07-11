package com.example.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.JWT;
import com.example.ex.model.User;
import com.example.ex.rest.AuthClient;
import com.example.ex.service.AuthService;

/**
 * Auth endpoints
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthClient authClient;
    @Autowired
    private AuthService authService;

    @Value("${cognito.endpoints.authorize}")
    private String authorizeUrl;

    /**
     * Redirect user to correct url for authorization code
     */
    @GetMapping("/login")
    public ResponseEntity<Object> login() {
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .header(HttpHeaders.LOCATION, authorizeUrl)
            .build();
    }

    /**
     * Get aws tokens with authorization code
     */
    @GetMapping("/token")
    public JWT token(@RequestParam("code") String code) {
        return authClient.getToken(code);
    }
    
    @GetMapping("/me")
    public User getCurrentUser() {
        return authService.getLoggedUser();
    }

}