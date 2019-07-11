package com.example.ex.model;

import java.util.Date;
import java.util.List;

import com.example.ex.model.entity.admin.DealCode;

import lombok.Data;

@Data
public class User {
    private DealCode dealCode;
    private Role role;
    private String uuid;
    private Long authTime;
    private Date issued;
    private Date expire;
    private String name;
    private String cognitoUserName;
    private String email;
    private String profile;
    private List<String> groups;
}
