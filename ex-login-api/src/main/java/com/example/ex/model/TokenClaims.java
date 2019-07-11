package com.example.ex.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenClaims {
    private String uuid;
    private Long authTime;
    private Date issued;
    private Date expire;
    private String name;
    private String cognitoUserName;
    private String email;
    private String profile;
    private String dealcode;
    private List<String> groups;
}
