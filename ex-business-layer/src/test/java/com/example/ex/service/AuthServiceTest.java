package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.nimbusds.jwt.JWTClaimsSet;
import com.example.ex.exception.RoleNotFoundException;
import com.example.ex.exception.UnauthorizedException;
import com.example.ex.model.PermissionType;
import com.example.ex.model.Resource;
import com.example.ex.model.Role;
import com.example.ex.model.User;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.type.DcType;

@RunWith(SpringRunner.class)
public class AuthServiceTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceTest.class);
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @TestConfiguration
    static class AppTypeServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public AuthService authService() {
            return new AuthService();
        }
    }

    @Autowired
    private AuthService authService;
    @Autowired
    private DealCodeService dealCodeService;

    @Test
    public void shouldReturnNullWhenUserIsNotLogged() {
        SecurityContextHolder.getContext().setAuthentication(null);
        User user = authService.getLoggedUser();

        assertThat(user).isNull();
    }

    @Test
    public void shouldNotAllowUserWithoutDealcode() throws ParseException {
        JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder();

        JWTClaimsSet jwtClaimsSet = jwtBuilder.claim("sub", "").claim("auth-time", 0L).claim("iat", new Date()).claim("exp", new Date())
                .claim("name", "").claim("email", "").claim("profile", "").claim("cognito:username", "")
                .claim("cognito:groups", "").claim("custom:dealcode", 1).build();

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getDetails()).thenReturn(jwtClaimsSet);
        thrown.expect(RoleNotFoundException.class);
        thrown.expectMessage("Cannot parse dealcode");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        authService.getLoggedUser();
    }

    @Test
    public void shouldNotAllowUserWithNullDealcode() throws ParseException {
        JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder();

        JWTClaimsSet jwtClaimsSet = jwtBuilder.claim("sub", "").claim("auth-time", 0L).claim("iat", new Date()).claim("exp", new Date())
                .claim("name", "").claim("email", "").claim("profile", "").claim("cognito:username", "")
                .claim("cognito:groups", new ArrayList<>()).claim("custom:dealcode", null).build();

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getDetails()).thenReturn(jwtClaimsSet);
        thrown.expect(RoleNotFoundException.class);
        thrown.expectMessage("User has no dealcode");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        authService.getLoggedUser();
    }
    
    @Test
    public void shouldNotAllowUserWithUnknownDealcode() throws ParseException {
        JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder();
        String dealCodeHandle = "unknowkn";

        JWTClaimsSet jwtClaimsSet = jwtBuilder.claim("sub", "").claim("auth-time", 0L).claim("iat", new Date()).claim("exp", new Date())
                .claim("name", "").claim("email", "").claim("profile", "").claim("cognito:username", "")
                .claim("cognito:groups", new ArrayList<>()).claim("custom:dealcode", dealCodeHandle).build();

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getDetails()).thenReturn(jwtClaimsSet);
        Mockito.when(dealCodeService.getFromCache(dealCodeHandle)).thenReturn(null);
        thrown.expect(RoleNotFoundException.class);
        thrown.expectMessage("Dealcode handle <" + dealCodeHandle + ">  was not found");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        authService.getLoggedUser();
    }
    
    @Test
    public void shouldReturnLoggedUser() {
        setAuthentication(DcType.EXX);
        User user = authService.getLoggedUser();
        assertThat(user.getRole()).isEqualTo(Role.TW);
        assertThat(user.getDealCode()).isNotNull();
    }
    
    private void setAuthentication(DcType dcType) {
        DealCode dealCode = new DealCode();
        String dealCodeHandle = "dealcode";
        dealCode.setHandle(dealCodeHandle);
        dealCode.setDcType(dcType);
        dealCode.setFirm("firm");

        JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder();
        JWTClaimsSet jwtClaimsSet = jwtBuilder.claim("sub", "").claim("auth-time", 0L).claim("iat", new Date()).claim("exp", new Date())
                .claim("name", "").claim("email", "").claim("profile", "").claim("cognito:username", "")
                .claim("cognito:groups", new ArrayList<>()).claim("custom:dealcode", dealCodeHandle).build();

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getDetails()).thenReturn(jwtClaimsSet);
        Mockito.when(dealCodeService.getFromCache(dealCodeHandle)).thenReturn(dealCode );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    @Test
    public void shouldReturnFalseWhenNullResource() {
        boolean hasPermission = authService.hasPermission(null, PermissionType.READ);
        assertThat(hasPermission).isFalse();
        hasPermission = authService.hasPermission(null, HttpMethod.GET);
        assertThat(hasPermission).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenNullPermission() {
        boolean hasPermission = authService.hasPermission(Resource.CURRENCY_PAIR,null);
        assertThat(hasPermission).isFalse();
        hasPermission = authService.hasPermission("currencypairs",null);
        assertThat(hasPermission).isFalse();
    }
    
    @Test(expected=UnauthorizedException.class)
    public void shouldReturnUnauthorizedWhenUserIsNotLogged() {
        SecurityContextHolder.getContext().setAuthentication(null);
        authService.hasPermission(Resource.CURRENCY_PAIR,PermissionType.READ);
    }
    
    private void shouldHavePermission(List<Resource> resources, PermissionType[] permissions) {
        shouldHavePermission(resources, new ArrayList(Arrays.asList(permissions)));
    }
    private void shouldHavePermission(List<Resource> resources, List<PermissionType> permissions) {
        checkPermission(resources,permissions,true);
    }
    private void shouldNotHavePermission(List<Resource> resources, PermissionType[] permissions) {
        shouldNotHavePermission(resources, new ArrayList(Arrays.asList(permissions)));
    }
    private void shouldNotHavePermission(List<Resource> resources, List<PermissionType> permissions) {
        checkPermission(resources,permissions,false);
    }
    
    private void checkPermission(List<Resource> resources, List<PermissionType> permissions, boolean condition) {
        for (Resource resource : resources) {
            for (PermissionType permission : permissions) {
                boolean hasPermission = authService.hasPermission(resource,permission);
                if (hasPermission != condition) {
                    LOGGER.error("Permission {}, Resource {}", permission,resource);
                }
                assertThat(hasPermission).isEqualTo(condition);
            }
        }
    }
    
    @Test
    public void shouldAllowAnyResourceToRoleTW() {
        setAuthentication(DcType.EXX);

        List<Resource> shouldNotExistResource = new ArrayList<>();
        shouldNotExistResource.add(Resource.WEBSOCKETS_TEST);

        List<Resource> readOnlyResources = new ArrayList<>();
        readOnlyResources.add(Resource.BOOK);
        readOnlyResources.add(Resource.CREDIT_DASHBOARD);
        readOnlyResources.add(Resource.ERROR);
        readOnlyResources.add(Resource.LIVE);
        readOnlyResources.add(Resource.REF_DATA);
        readOnlyResources.add(Resource.SWAGGER);
        readOnlyResources.add(Resource.SWAGGER_RESOURCES);
        readOnlyResources.add(Resource.SWAGGER_WEBJARS);
        readOnlyResources.add(Resource.WEBSOCKETS_DATA);

        List<Resource> resources = new ArrayList(Arrays.asList(Resource.values()));
        resources.removeAll(readOnlyResources);
        resources.removeAll(shouldNotExistResource);

        shouldHavePermission(resources,PermissionType.values());

        List<PermissionType> read = new ArrayList<>();
        read.add(PermissionType.READ);

        shouldHavePermission(readOnlyResources,read);
    }
    
    private List<Resource> getCrudResourcesForRoleCP() {
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.CREDIT_LINE);
        resources.add(Resource.CREDIT_POOL);
        return resources;
    }
    
    private List<Resource> getReadOnlyResourcesForRoleCP() {
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.BOOK);
        resources.add(Resource.DEAL_CODE);
        resources.add(Resource.ERROR);
        resources.add(Resource.FIRM);
        resources.add(Resource.FIX_SESSION);
        resources.add(Resource.FIX_SESSION_MPID);
        resources.add(Resource.LIVE);
        resources.add(Resource.MPID);
        resources.add(Resource.REF_DATA);
        resources.add(Resource.SWAGGER);
        resources.add(Resource.SWAGGER_RESOURCES);
        resources.add(Resource.SWAGGER_WEBJARS);
        resources.add(Resource.TRADE_NOTIFICATION_RULE);
        resources.add(Resource.CURRENCY_PAIR);
        resources.add(Resource.CREDIT_DASHBOARD);
        resources.add(Resource.WEBSOCKETS_DATA);
        return resources;
    }

    private List<Resource> getResourcesWithoutPermissionsForRoleCP() {
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.APP_INSTANCE);
        resources.add(Resource.APP_SERVER);
        resources.add(Resource.APP_JVM);
        resources.add(Resource.BLOCKED_COUNTER_PARTY);
        resources.add(Resource.CURRENCY);
        resources.add(Resource.CURRENCY_PAIR_SETTLE_DATE);
        resources.add(Resource.HOLIDAY);
        resources.add(Resource.SCHEDULE_EVENT);
        resources.add(Resource.SYSTEM_STATE);
        resources.add(Resource.USER);
        resources.add(Resource.VENUE);
        resources.add(Resource.WEBSOCKETS_TEST);
        return resources;
    }
    
    @Test
    public void verifyAllResourcesAreCoveredForRoleCP() {
        Set<Resource> resources = new HashSet<>();
        resources.addAll(getCrudResourcesForRoleCP());
        resources.addAll(getResourcesWithoutPermissionsForRoleCP());
        resources.addAll(getReadOnlyResourcesForRoleCP());
        assertThat(resources).containsExactlyInAnyOrder(Resource.values());
    }
        
    @Test
    public void shouldHaveCrudPermissionsForRoleCP() {
        setAuthentication(DcType.PRINCIPAL);

        shouldHavePermission(getCrudResourcesForRoleCP(),PermissionType.values());
    }
    
    @Test
    public void shouldHaveReadOnlyPermissionsForRoleCP() {
        setAuthentication(DcType.PRINCIPAL);

        List<PermissionType> read = new ArrayList<>();
        read.add(PermissionType.READ);

        shouldHavePermission(getReadOnlyResourcesForRoleCP(),read);

        List<PermissionType> allButRead = new ArrayList(Arrays.asList(PermissionType.values()));
        allButRead.remove(PermissionType.READ);

        shouldNotHavePermission(getReadOnlyResourcesForRoleCP(),allButRead);
    }
    
    @Test
    public void shouldNotHavePermissionsForRoleCP() {
        setAuthentication(DcType.PRINCIPAL);

        shouldNotHavePermission(getResourcesWithoutPermissionsForRoleCP(),PermissionType.values());
    }
    
    private List<Resource> getReadOnlyResourcesForRoleTP() {
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.BOOK);
        resources.add(Resource.CREDIT_DASHBOARD);
        resources.add(Resource.CURRENCY_PAIR);
        resources.add(Resource.DEAL_CODE);
        resources.add(Resource.ERROR);
        resources.add(Resource.FIRM);
        resources.add(Resource.FIX_SESSION);
        resources.add(Resource.FIX_SESSION_MPID);
        resources.add(Resource.LIVE);
        resources.add(Resource.MPID);
        resources.add(Resource.REF_DATA);
        resources.add(Resource.SWAGGER);
        resources.add(Resource.SWAGGER_RESOURCES);
        resources.add(Resource.SWAGGER_WEBJARS);
        resources.add(Resource.TRADE_NOTIFICATION_RULE);
        resources.add(Resource.WEBSOCKETS_DATA);
        return resources;
    }

    private List<Resource> getResourcesWithoutPermissionsForRoleTP() {
        List<Resource> resources = new ArrayList<>();
        resources.add(Resource.APP_INSTANCE);
        resources.add(Resource.APP_SERVER);
        resources.add(Resource.APP_JVM);
        resources.add(Resource.BLOCKED_COUNTER_PARTY);
        resources.add(Resource.CREDIT_LINE);
        resources.add(Resource.CREDIT_POOL);
        resources.add(Resource.CURRENCY);
        resources.add(Resource.CURRENCY_PAIR_SETTLE_DATE);
        resources.add(Resource.HOLIDAY);
        resources.add(Resource.SCHEDULE_EVENT);
        resources.add(Resource.SYSTEM_STATE);
        resources.add(Resource.USER);
        resources.add(Resource.VENUE);
        resources.add(Resource.WEBSOCKETS_TEST);
        return resources;
    }
    
    @Test
    public void verifyAllResourcesAreCoveredForRoleTP() {
        Set<Resource> resources = new HashSet<>();
        resources.addAll(getResourcesWithoutPermissionsForRoleTP());
        resources.addAll(getReadOnlyResourcesForRoleTP());
        assertThat(resources).containsExactlyInAnyOrder(Resource.values());
    }

    @Test
    public void shouldHaveCrudPermissionsForRoleTP() {
        setAuthentication(DcType.PRINCIPAL);

        shouldHavePermission(Collections.emptyList(),PermissionType.values());
    }
    
    @Test
    public void shouldHaveReadOnlyPermissionsForRoleTP() {
        setAuthentication(DcType.PBCLIENT);


        List<PermissionType> read = new ArrayList<>();
        read.add(PermissionType.READ);

        shouldHavePermission(getReadOnlyResourcesForRoleTP(),read);

        List<PermissionType> allButRead = new ArrayList(Arrays.asList(PermissionType.values()));
        allButRead.remove(PermissionType.READ);

        shouldNotHavePermission(getReadOnlyResourcesForRoleTP(),allButRead);
    }
    
    @Test
    public void shouldNotHavePermissionsForRoleTP() {
        setAuthentication(DcType.PBCLIENT);

        shouldNotHavePermission(getResourcesWithoutPermissionsForRoleTP(),PermissionType.values());
    }
    
    @Test
    public void shouldNotFilterDataForRoleTW() {
        setAuthentication(DcType.EXX);
        User user = authService.getLoggedUser();
        
        boolean isAllowed = authService.isAllowedByFirm(user, null, null);

        assertThat(isAllowed).isTrue();
    }
    
    @Test
    public void shouldNotBeAllowedToReadAnyDataForRoleCP() {
        setAuthentication(DcType.PRINCIPAL);
        User user = authService.getLoggedUser();
        
        boolean isAllowed = authService.isAllowedByFirm(user, null, null);

        assertThat(isAllowed).isFalse();
    }
    
    @Test
    public void shouldNotBeAllowedToReadGranteeFirmForRoleCP() {
        setAuthentication(DcType.PRINCIPAL);
        User user = authService.getLoggedUser();
        user.getDealCode().setFirm("grantee");
        
        boolean isAllowed = authService.isAllowedByFirm(user, "grantor", "grantee");

        assertThat(isAllowed).isFalse();
    }
    
    @Test
    public void shouldBeAllowedToReadGrantorFirmForRoleCP() {
        setAuthentication(DcType.PRINCIPAL);
        User user = authService.getLoggedUser();
        user.getDealCode().setFirm("grantor");
        
        boolean isAllowed = authService.isAllowedByFirm(user, "grantor", "grantee");

        assertThat(isAllowed).isTrue();
    }
    
    @Test
    public void shouldNotBeAllowedToReadAnyDataForRoleTP() {
        setAuthentication(DcType.PBCLIENT);
        User user = authService.getLoggedUser();
        
        boolean isAllowed = authService.isAllowedByFirm(user, null, null);

        assertThat(isAllowed).isFalse();
    }
    
    @Test
    public void shouldNotBeAllowedToReadGrantorFirmForRoleTP() {
        setAuthentication(DcType.PBCLIENT);
        User user = authService.getLoggedUser();
        user.getDealCode().setFirm("grantor");
        
        boolean isAllowed = authService.isAllowedByFirm(user, "grantor", "grantee");

        assertThat(isAllowed).isFalse();
    }
    
    @Test
    public void shouldBeAllowedToReadGranteeFirmForRoleTP() {
        setAuthentication(DcType.PBCLIENT);
        User user = authService.getLoggedUser();
        user.getDealCode().setFirm("grantee");
        
        boolean isAllowed = authService.isAllowedByFirm(user, "grantor", "grantee");

        assertThat(isAllowed).isTrue();
    }
}