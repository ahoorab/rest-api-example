package com.example.ex.service;

import com.example.ex.model.CognitoUser;
import com.example.ex.model.JWT;

public interface OAuthClient<T> {

    T confirmSignUp(String username, String code);

    T createUser(CognitoUser user);

    JWT getToken(String code);

}
