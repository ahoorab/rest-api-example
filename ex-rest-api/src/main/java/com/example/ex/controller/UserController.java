package com.example.ex.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.example.ex.model.CognitoUser;
import com.example.ex.rest.AuthClient;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthClient authClient;

    @PostMapping(value="/create",consumes="application/json")
    public ResponseEntity<Object> create(@Valid @RequestBody CognitoUser user) {
        LOGGER.debug("create user {}",user);
        SignUpResult result = authClient.createUser(user);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }
    
    @PutMapping("/activate")
    public ResponseEntity<Object> confirmSignUp(@RequestParam("username") String username, @RequestParam("code") String code) {
        LOGGER.debug("confirm sign up, username {}, code {}",username,code);
        ConfirmSignUpResult result = authClient.confirmSignUp(username, code);
        return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
    }
    
}