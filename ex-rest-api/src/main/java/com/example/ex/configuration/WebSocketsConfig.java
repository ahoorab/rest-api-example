package com.example.ex.configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.SessionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.example.ex.interceptor.AuthInterceptor;

/**
 * Spring boot configuration for websockets endpoints
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketsConfig implements WebSocketMessageBrokerConfigurer {
    
    public static final String WEB_SOCKET_DATA_PREFIX = "/data";
    
    private Set<String> webSocketSessions = ConcurrentHashMap.newKeySet();
    private Map<String,Integer> topicsCount = new ConcurrentHashMap<>();
    private Map<String,Set<String>> sessionTopics = new ConcurrentHashMap<>();
    
    @Value("${ex.redis.path.live}")
    private String livePath;
    @Value("${ex.redis.path.book}")
    private String bookPath;
    @Value("${ex.redis.path.refdata}")
    private String refDataPath;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
   
    @Autowired
    private AuthInterceptor authInterceptor;
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(WEB_SOCKET_DATA_PREFIX);
        config.setApplicationDestinationPrefixes(contextPath+"/"+livePath);
        config.setApplicationDestinationPrefixes(contextPath+"/"+bookPath);
        config.setApplicationDestinationPrefixes(contextPath+"/"+refDataPath);
    }
 
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String dataPrefix = WEB_SOCKET_DATA_PREFIX+"/";
        registry.addEndpoint(dataPrefix+livePath).setAllowedOrigins("*").withSockJS().setInterceptors(authInterceptor);
        registry.addEndpoint(dataPrefix+bookPath).setAllowedOrigins("*").withSockJS().setInterceptors(authInterceptor);
        registry.addEndpoint(dataPrefix+refDataPath).setAllowedOrigins("*").withSockJS().setInterceptors(authInterceptor);;
    }
    
    @EventListener
    protected void onSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        webSocketSessions.add(sha.getSessionId());
    }

    @EventListener
    protected void onSessionDisconnect(SessionDisconnectEvent event) {
        String session = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
        webSocketSessions.remove(session);

        if (sessionTopics.containsKey(session)) {
            for (String topic : sessionTopics.get(session)) {
                Integer count = topicsCount.get(topic);
                if (count != null && count > 0) {
                    topicsCount.put(topic,--count);
                }
            }
        }
        sessionTopics.remove(session);
    }

    @EventListener
    protected synchronized void onSessionSubscribe(SessionSubscribeEvent event) throws SessionException {
        String topic = event.getMessage().getHeaders().get("simpDestination").toString();
        String session = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
        
        if (webSocketSessions.contains(session)) {
            int count = 0;
            if (topicsCount.containsKey(topic)) {
                count = topicsCount.get(topic);
            }
            topicsCount.put(topic,++count);
            
            if (!sessionTopics.containsKey(session)) {
                sessionTopics.put(session, ConcurrentHashMap.newKeySet());
            }
            sessionTopics.get(session).add(topic);
        } else {
            throw new SessionException("Cannot subscribe to topic <" + topic + "> to unknown session <" + session + ">", null, null);
        }

    }

    @EventListener
    protected synchronized void onSessionUnsubscribe(SessionUnsubscribeEvent event) throws SessionException {
        String topic = event.getMessage().getHeaders().get("simpDestination").toString();
        String session = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();

        if (webSocketSessions.contains(session) && sessionTopics.getOrDefault(session, new HashSet<>()).contains(topic)) {
            Integer count = topicsCount.get(topic);
            if (count != null && count > 0) {
                topicsCount.put(topic,--count);
            }
        } else {
            throw new SessionException("Cannot unsubscribe to topic <" + topic + "> to unknown session <" + session + ">", null, null);
        }
    }
    
    @Bean
    public ThreadPoolTaskExecutor webSocketsTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
    
    public int getTotalSessions() {
        return webSocketSessions.size();
    }

    public int getTopicCount(String topic) {
        return topicsCount.get(topic) == null ? 0 : topicsCount.get(topic);
    }
    
    public Set<String> getTopics(String sessionId) {
        return Collections.unmodifiableSet(sessionTopics.getOrDefault(sessionId,Collections.emptySet()));
    }
    
    public Set<String> getSessions() {
        return Collections.unmodifiableSet(webSocketSessions);
    }
    
    public Map<String, Integer> getTopicsCount() {
        return Collections.unmodifiableMap(topicsCount);
    }

    public Map<String, Set<String>> getSessionTopics() {
        return Collections.unmodifiableMap(sessionTopics);
    }
}