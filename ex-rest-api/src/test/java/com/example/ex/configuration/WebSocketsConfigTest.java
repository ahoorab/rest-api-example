package com.example.ex.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.SessionException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@WebMvcTest
public class WebSocketsConfigTest extends ControllerTestConfiguration {
    
    @Autowired
    private WebSocketsConfig webSocketsConfig;
    
    @Before
    public void init() {
        webSocketsConfig = new WebSocketsConfig();
    }
    
    @Test
    public void testCreateNewSesssion() throws Exception {
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", "testCreateNewSesssion");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage("testCreateNewSesssion".getBytes(),headers);
        SessionConnectedEvent connectedEvent = new SessionConnectedEvent(new Object(), message);

        webSocketsConfig.onSessionConnected(connectedEvent);
               
        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(1);
    }
    
    @Test
    public void testDisconnectSesssion() throws Exception {
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", "testDisconnectSesssion");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage("testDisconnectSesssion".getBytes(),headers);
        SessionConnectedEvent connectedEvent = new SessionConnectedEvent(new Object(), message);

        webSocketsConfig.onSessionConnected(connectedEvent);
               
        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(1);

        SessionDisconnectEvent disconnectedEvent = new SessionDisconnectEvent(new Object(), message, "testDisconnectSesssion",CloseStatus.NORMAL);
        webSocketsConfig.onSessionDisconnect(disconnectedEvent);

        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(0);
    }
    
    @Test
    public void shouldRemoveTopicsOnDisconnect() throws Exception {
        String sessionId = "shouldRemoveTopicsOnDisconnect";
        String topic = "/topic/shouldRemoveTopicsOnDisconnect";
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", sessionId);
        headers.put("simpDestination", topic);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage(sessionId.getBytes(),headers);
        SessionConnectedEvent connectedEvent = new SessionConnectedEvent(new Object(), message);
        webSocketsConfig.onSessionConnected(connectedEvent);
        
        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(1);

        SessionSubscribeEvent subscribeEvent = new SessionSubscribeEvent(new Object(), message);
        webSocketsConfig.onSessionSubscribe(subscribeEvent);
               
        assertThat(webSocketsConfig.getTopicCount(topic)).isEqualTo(1);
        assertThat(webSocketsConfig.getTopics(sessionId).size()).isEqualTo(1);
        assertThat(webSocketsConfig.getTopics(sessionId).iterator().next()).isEqualTo(topic);
        
        SessionDisconnectEvent disconnectedEvent = new SessionDisconnectEvent(new Object(), message, "testDisconnectSesssion",CloseStatus.NORMAL);
        webSocketsConfig.onSessionDisconnect(disconnectedEvent);

        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(0);
        assertThat(webSocketsConfig.getTopicCount(topic)).isEqualTo(0);
        assertThat(webSocketsConfig.getTopics(sessionId).size()).isEqualTo(0);
    }
    
    @Test
    public void testSubscribeToTopic() throws Exception {
        String sessionId = "testSubscribeToTopic";
        String topic = "/topic/testSubscribeToTopic";
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", sessionId);
        headers.put("simpDestination", topic);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage(sessionId.getBytes(),headers);
        SessionConnectedEvent connectedEvent = new SessionConnectedEvent(new Object(), message);
        webSocketsConfig.onSessionConnected(connectedEvent);
        
        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(1);

        SessionSubscribeEvent subscribeEvent = new SessionSubscribeEvent(new Object(), message);
        webSocketsConfig.onSessionSubscribe(subscribeEvent);
               
        assertThat(webSocketsConfig.getTopicCount(topic)).isEqualTo(1);
        assertThat(webSocketsConfig.getTopics(sessionId).size()).isEqualTo(1);
        assertThat(webSocketsConfig.getTopics(sessionId).iterator().next()).isEqualTo(topic);
    }
    
    @Test(expected=SessionException.class)
    public void shouldNotSuscribeIfNotConnected() throws Exception {
        String sessionId = "testSubscribeToTopic";
        String topic = "/topic/testSubscribeToTopic";
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", sessionId);
        headers.put("simpDestination", topic);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage(sessionId.getBytes(),headers);
        SessionSubscribeEvent subscribeEvent = new SessionSubscribeEvent(new Object(), message);
        webSocketsConfig.onSessionSubscribe(subscribeEvent);
    }
    
    @Test
    public void testUnsubscribeToTopic() throws Exception {
        String sessionId = "testUnsubscribeToTopic";
        String topic = "/topic/testSubscribeToTopic";
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", sessionId);
        headers.put("simpDestination", topic);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage(sessionId.getBytes(),headers);
        SessionConnectedEvent connectedEvent = new SessionConnectedEvent(new Object(), message);
        webSocketsConfig.onSessionConnected(connectedEvent);
        
        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(1);

        SessionSubscribeEvent subscribeEvent = new SessionSubscribeEvent(new Object(), message);
        webSocketsConfig.onSessionSubscribe(subscribeEvent);
               
        assertThat(webSocketsConfig.getTopicCount(topic)).isEqualTo(1);
        assertThat(webSocketsConfig.getTopics(sessionId).size()).isEqualTo(1);
        assertThat(webSocketsConfig.getTopics(sessionId).iterator().next()).isEqualTo(topic);

        SessionUnsubscribeEvent unsubscribeEvent = new SessionUnsubscribeEvent(new Object(), message);
        webSocketsConfig.onSessionUnsubscribe(unsubscribeEvent);

        assertThat(webSocketsConfig.getTopicCount(topic)).isEqualTo(0);
        assertThat(webSocketsConfig.getTopics(sessionId).size()).isEqualTo(1);
        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(1);
    }
    
    @Test(expected=SessionException.class)
    public void shouldNotUnsubscribeIfNotConnected() throws Exception {
        String sessionId = "testUnsubscribeToTopic";
        String topic = "/topic/testSubscribeToTopic";
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", sessionId);
        headers.put("simpDestination", topic);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage(sessionId.getBytes(),headers);
        SessionUnsubscribeEvent unsubscribeEvent = new SessionUnsubscribeEvent(new Object(), message);
        webSocketsConfig.onSessionUnsubscribe(unsubscribeEvent);
    }
    
    @Test(expected=SessionException.class)
    public void shouldNotUnsubscribeIfNotSuscribed() throws Exception {
        String sessionId = "testUnsubscribeToTopic";
        String topic = "/topic/testSubscribeToTopic";
        Map<String,String> headers = new HashMap<>();
        headers.put("simpSessionId", sessionId);
        headers.put("simpDestination", topic);
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Message<byte[]> message = new GenericMessage(sessionId.getBytes(),headers);
        SessionConnectedEvent connectedEvent = new SessionConnectedEvent(new Object(), message);
        webSocketsConfig.onSessionConnected(connectedEvent);
        
        assertThat(webSocketsConfig.getTotalSessions()).isEqualTo(1);

        SessionUnsubscribeEvent unsubscribeEvent = new SessionUnsubscribeEvent(new Object(), message);
        webSocketsConfig.onSessionUnsubscribe(unsubscribeEvent);
    }
}