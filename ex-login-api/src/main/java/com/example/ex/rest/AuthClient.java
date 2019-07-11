package com.example.ex.rest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.AmazonWebServiceResult;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.example.ex.exception.InternalServerErrorException;
import com.example.ex.model.CognitoUser;
import com.example.ex.model.JWT;
import com.example.ex.service.OAuthClient;

@Component
public class AuthClient implements OAuthClient<AmazonWebServiceResult<ResponseMetadata>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthClient.class);

    @Value("${cognito.endpoints.token}")
    private String tokenUrl;
    @Value("${cognito.client}")
    private String clientId;
    @Value("${cognito.secret}")
    private String clientSecret;
    @Value("${cognito.callback}")
    private String callbackUrl;
    @Value("${cognito.region}")
    private String region;
    @Value("${cognito.poolid}")
    private String poolId;
    @Value("${cognito.urls}")
    private String urls;
    @Value("${cognito.login}")
    private String login;

    private AWSCognitoIdentityProvider cognitoClient;

    /**
     * Get token with authorization code
     */
    @Override
    public JWT getToken(String code) {
        RestTemplate client = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        headers.add("HeaderName", "value");
        headers.add("Authorization", "Basic " + auth);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<Object> req = new HttpEntity<>(null, headers);

        String url = tokenUrl + "?grant_type=authorization_code&client_id=" + clientId + "&code=" + code
                + "&redirect_uri=" + callbackUrl + "&client_secret=" + clientSecret;

        return client.postForObject(url, req, JWT.class);
    }

    @Override
    public SignUpResult createUser(CognitoUser user) {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setClientId(clientId);
        signUpRequest.setUsername(user.getUsername());
        signUpRequest.setPassword(user.getPassword());
        List<AttributeType> list = new ArrayList<>();

        AttributeType phoneAttribute = new AttributeType();
        phoneAttribute.setName("phone_number");
        phoneAttribute.setValue(user.getPhone());
        list.add(phoneAttribute);

        AttributeType emailAttribute = new AttributeType();
        emailAttribute.setName("email");
        emailAttribute.setValue(user.getEmail());
        list.add(emailAttribute);

        AttributeType dealCodeAttribute = new AttributeType();
        dealCodeAttribute.setName("custom:dealcode");
        dealCodeAttribute.setValue(user.getDealcode());
        list.add(dealCodeAttribute);

        signUpRequest.setUserAttributes(list);
        signUpRequest.setSecretHash(getSecretHash(user.getUsername()));

        return cognitoClient.signUp(signUpRequest);
    }

    @Override
    public ConfirmSignUpResult confirmSignUp(String username, String code) {
        ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest();
        confirmSignUpRequest.setUsername(username);
        confirmSignUpRequest.setConfirmationCode(code);
        confirmSignUpRequest.setClientId(clientId);

        confirmSignUpRequest.setSecretHash(getSecretHash(username));
        return cognitoClient.confirmSignUp(confirmSignUpRequest);
    }

    private String getSecretHash(String username) {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(username.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(clientId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error while calculating hash");
        }
    }

    @PostConstruct
    private void setCognitoClient() {
        AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
        cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(region).build();
    }

}