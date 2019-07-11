package com.example.ex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JWT {
    @JsonProperty("id_token") 
    private String idToken;
    @JsonProperty("access_token") 
    private String accessToken;
    @JsonProperty("refresh_token") 
    private String refreshtoken;
    @JsonProperty("expires_in") 
    private int expiresIn;
    @JsonProperty("token_type") 
    private String tokenType;
}
