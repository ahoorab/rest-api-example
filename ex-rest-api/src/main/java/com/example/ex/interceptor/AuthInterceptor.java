package com.example.ex.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.example.ex.configuration.CacheKeys;
import com.example.ex.service.AuthService;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter implements HandshakeInterceptor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

    @Value("${ex.redis.path.live}")
    private String livePath;

    @Value("${ex.redis.path.refdata}")
    private String refDataPath;

    @Value("${ex.redis.path.book}")
    private String bookPath;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @Autowired
    private CacheKeys cacheKeys;

    @Autowired
    private AuthService authService;

    private List<String> redisKeys;
    
    @PostConstruct
    public void setRedisKeys() {
        redisKeys = new ArrayList<>();
        redisKeys.add(livePath);
        redisKeys.add(refDataPath);
        redisKeys.add(bookPath);
        LOGGER.debug("auth interceptor started with contextPath {}, redisKeys {}, cacheKeys {}", contextPath, redisKeys , cacheKeys);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LOGGER.debug("pre handle with request {}, response {}, handler {}", request, response, handler);
        //first is empty
        //second is prefix
        int length = StringUtils.isEmpty(contextPath) ? 0 : 1;
        String[] path = request.getRequestURI().split("/");
        String resource = path[1+length];
        
        //this is a default path used by spring, do not use it.
        if ("error".equals(path[path.length-1])) {
            return true;
        }

        if (!request.getRequestURI().contains("/auth")) {
            HttpMethod method = HttpMethod.valueOf(request.getMethod());
            if (!authService.hasPermission(resource,method)) {
                LOGGER.debug("no permissions for resource {} and method {}", resource, request.getMethod());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        }

        if (path.length >= 3+length && redisKeys.contains(resource)) {
            String key = cacheKeys.getPath().get(resource+"."+path[2+length]);
            if (key != null) {
                @SuppressWarnings("unchecked")
                Map<String,Object> pathVariables = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); 
                pathVariables.put("key",key);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        LOGGER.debug("web socket before handshake uri {}", request.getURI());

        int length = StringUtils.isEmpty(contextPath) ? 0 : 1;
        String[] path = request.getURI().toString().split("/");
        String resource = path[3+length];

        if (!authService.hasPermission(resource,request.getMethod())) {
            LOGGER.debug("no permissions for web socket {} and method {}", resource, request.getMethod());
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
        LOGGER.debug("web socket connected with request {}, response {}, wsHandler {}, exception {}", request, response, wsHandler, exception);
    }
}