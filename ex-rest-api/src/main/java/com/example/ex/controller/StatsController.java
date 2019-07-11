package com.example.ex.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.configuration.WebSocketKeys;
import com.example.ex.configuration.WebSocketsConfig;

@RestController
@RequestMapping("/stats")
public class StatsController {
    
    @Autowired
    private WebSocketKeys webSockets;
    @Autowired
    protected WebSocketsConfig webSocketsConfig;
    @Autowired
    private ThreadPoolTaskExecutor webSocketsTaskExecutor;
    
    @GetMapping(value = "/sockets/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, List<String>> getTopics() {
        return webSockets.getWebsockets();
    }

    @GetMapping(value = "/sockets/topics/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Integer> getTopicsCount() {
        return webSocketsConfig.getTopicsCount();
    }

    @GetMapping(value = "/sockets/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<String> getSessions() {
        return webSocketsConfig.getSessions();
    }

    @GetMapping(value = "/sockets/sessions/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Set<String>> getSessionTopics() {
        return webSocketsConfig.getSessionTopics();
    }

    @GetMapping(value = "/sockets/threads", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int getSocketThreads() {
        return webSocketsTaskExecutor.getActiveCount();
    }

}
