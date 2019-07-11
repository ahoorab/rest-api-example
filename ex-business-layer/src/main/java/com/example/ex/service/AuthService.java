package com.example.ex.service;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWTClaimsSet;
import com.example.ex.exception.RoleNotFoundException;
import com.example.ex.exception.UnauthorizedException;
import com.example.ex.model.PermissionType;
import com.example.ex.model.Resource;
import com.example.ex.model.Role;
import com.example.ex.model.User;
import com.example.ex.model.entity.admin.DealCode;

@Service
public class AuthService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private DealCodeService dealCodeService;

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof JWTClaimsSet) {
            User user = new User();
            JWTClaimsSet details = (JWTClaimsSet) authentication.getDetails();
            String dealCodeHandle;
            try {
                user.setUuid(details.getStringClaim("sub"));
                user.setAuthTime((Long) details.getClaim("auth_time"));
                user.setIssued((Date) details.getClaim("iat"));
                user.setExpire((Date) details.getClaim("exp"));
                user.setName(details.getStringClaim("name"));
                user.setCognitoUserName(details.getStringClaim("cognito:username"));
                user.setEmail(details.getStringClaim("email"));
                user.setProfile(details.getStringClaim("profile"));
                user.setGroups(details.getStringListClaim("cognito:groups"));
                dealCodeHandle = details.getStringClaim("custom:dealcode");
            } catch (ParseException e) {
                throw new RoleNotFoundException("Cannot parse dealcode");
            }
            if (dealCodeHandle == null) {
                throw new RoleNotFoundException("User has no dealcode");
            }
            DealCode dealCode = dealCodeService.getFromCache(dealCodeHandle.replaceAll("!", ""));
            if (dealCode == null) {
                throw new RoleNotFoundException("Dealcode handle <" + dealCodeHandle + ">  was not found");
            }
            user.setDealCode(dealCode);
            Role role = Role.fromDealCode(dealCode.getDcType());
            user.setRole(role);
            return user;
        }
        return null;
    }
    
    public boolean hasPermission(Resource resource, PermissionType type) {
        if (resource == null || type == null) {
            return false;
        }
        User user = getLoggedUser();
        if (user == null) {
            LOGGER.debug("auth client cannot get user claims, they are null, probably user is not authenticated");
            throw new UnauthorizedException("who are you? you are not authenticated, aren't you?");
        }
        if (user.getDealCode() == null) {
            //this should be never happen, but just in case
            LOGGER.error("user has no dealcode, cannot get the role");
            return false;
        }
        return user.getRole().hasPermission(resource, type);
    }

    public boolean hasPermission(String resource, HttpMethod httpMethod) {
        return hasPermission(Resource.fromUri(resource), PermissionType.fromHttpMethod(httpMethod));
    }
    
    public boolean isAllowedByFirm(User user, String grantorFirm, String granteeFirm) {
        if (Role.TW.equals(user.getRole())) {
            return true;
        }
        if (Role.CP.equals(user.getRole()) && user.getDealCode().getFirm().equalsIgnoreCase(grantorFirm)) {
            return true;
        }
        return Role.TP.equals(user.getRole()) && user.getDealCode().getFirm().equalsIgnoreCase(granteeFirm);
    }

}