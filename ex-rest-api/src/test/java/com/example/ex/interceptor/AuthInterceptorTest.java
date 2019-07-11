package com.example.ex.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.HandlerMapping;

import com.example.ex.configuration.ControllerTestConfiguration;
import com.example.ex.model.PermissionType;
import com.example.ex.model.Resource;
import com.example.ex.service.AuthService;

@WebMvcTest
public class AuthInterceptorTest extends ControllerTestConfiguration {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private AuthService authService;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private ServerHttpRequest serverHttpRequest;
    private ServerHttpResponse serverHttpResponse;

    @Before
    public void init() {
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(authService.hasPermission(Mockito.any(Resource.class), Mockito.any(PermissionType.class))).thenReturn(true);
        Mockito.when(authService.hasPermission(Mockito.anyString(),Mockito.any(HttpMethod.class))).thenReturn(true);
        serverHttpRequest = Mockito.mock(ServerHttpRequest.class);
        serverHttpResponse = Mockito.mock(ServerHttpResponse.class);
    }

    @Test
    public void shouldNotInterceptNonCacheUri() throws Exception {
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/ex-admin-api/mpids/1");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("GET");
        boolean notIntercepted = authInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());

        assertThat(notIntercepted).isTrue();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(httpServletRequest, Mockito.times(0)).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Mockito.verify(httpServletResponse, Mockito.times(0)).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnNotFoundWhenNotAuthorizedKey() throws Exception {
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/ex-admin-api/live/nonauthorizedkey");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("GET");
        boolean notIntercepted = authInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());

        assertThat(notIntercepted).isFalse();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(httpServletRequest, Mockito.times(0)).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Mockito.verify(httpServletResponse, Mockito.times(1)).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnAllowAuthorizedKey() throws Exception {
        Map<String, Object> uriAttributes = new HashMap<>();

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/ex-admin-api/live/tradereports");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("GET");
        Mockito.when(httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(uriAttributes);
        boolean notIntercepted = authInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());

        assertThat(notIntercepted).isTrue();
        assertThat(uriAttributes).isNotEmpty();
        assertThat(uriAttributes.get("key")).isNotNull();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(httpServletRequest, Mockito.times(1)).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Mockito.verify(httpServletResponse, Mockito.times(0)).setStatus(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void shouldNotAllowUserWithoutPermissions() throws Exception {
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/ex-admin-api/mpids/1");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("GET");
        Mockito.when(authService.hasPermission(Mockito.any(Resource.class), Mockito.any(PermissionType.class))).thenReturn(false);
        Mockito.when(authService.hasPermission(Mockito.anyString(),Mockito.any(HttpMethod.class))).thenReturn(false);
        boolean notIntercepted = authInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());

        assertThat(notIntercepted).isFalse();
        Mockito.verify(httpServletRequest, Mockito.times(2)).getRequestURI();
        Mockito.verify(httpServletRequest, Mockito.times(0)).getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Mockito.verify(httpServletResponse, Mockito.times(0)).setStatus(HttpStatus.NOT_FOUND.value());
        Mockito.verify(httpServletResponse, Mockito.times(1)).setStatus(HttpStatus.FORBIDDEN.value());
    }
    
    @Test
    public void shouldNotAllowUserWithoutWebSocketsPermissions() throws Exception {
        Mockito.when(serverHttpRequest.getURI()).thenReturn(new URI("/ex-admin-api/data/live/creditmatrix"));
        Mockito.when(serverHttpRequest.getMethod()).thenReturn(HttpMethod.GET);
        Mockito.when(authService.hasPermission(Mockito.any(Resource.class), Mockito.any(PermissionType.class))).thenReturn(false);
        Mockito.when(authService.hasPermission(Mockito.anyString(),Mockito.any(HttpMethod.class))).thenReturn(false);
        boolean notIntercepted = authInterceptor.beforeHandshake(serverHttpRequest,serverHttpResponse,null,null);

        assertThat(notIntercepted).isFalse();
        Mockito.verify(serverHttpRequest, Mockito.atLeastOnce()).getURI();
        Mockito.verify(serverHttpResponse, Mockito.times(1)).setStatusCode(HttpStatus.FORBIDDEN);
    }

}