package com.example.ex.model;

import lombok.Data;

@Data
public class CognitoUser {
    
    private String username;
    private String email;
    private String phone;
    private String dealcode;
    private String password;

}
