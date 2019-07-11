package com.example.ex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ex.configuration.CacheKeys;
import com.example.ex.configuration.WebSocketKeys;
import com.example.ex.configuration.WebSocketsConfig;
import com.example.ex.model.entity.admin.CreditPoolCreditLineCache;
import com.example.ex.service.CacheService;

/**
 * Abstract controller to get data from redis
 * 
 * Each implementation should implement getKeyPrefix method to return the redis key 
 * Each key is taken from the configuration file
 * 
 * @author spintos
 *
 */
public abstract class CacheController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);
    
    @Value("${ex.redis.path}")
    protected String rdisPath;
    //this should me removed once data is avalable on redis
    @Value("${ex.redis.path.live.creditlinecreditpools}")
    protected String creditLineCreditPoolsKey;
    @Autowired
    protected WebSocketKeys webSockets;
    @Autowired
    protected CacheService cacheService;
    @Autowired
    protected CacheKeys cacheKeys;
    @Autowired
    protected SimpMessagingTemplate template;
    @Autowired
    protected WebSocketsConfig webSocketsConfig;
    @Autowired
    protected ThreadPoolTaskExecutor webSocketsTaskExecutor;

    protected String keyPrefix;
    
    @PostConstruct
    public void init() {
        keyPrefix = rdisPath + ":" + getControllerKeyPrefix();
    }

    /**
     * Class CacheUriInterceptor sets path variable 'key'
     * See method CacheUriInterceptor.preHandle()
     */
    @GetMapping(value = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findAll(@PathVariable("key") String key) {
        return findValues(key).toString();
    }
    
    protected JSONArray findValues(String key) {
        List<JSONObject> objects = new ArrayList<>();
        if (key.equals(creditLineCreditPoolsKey)) {
            //to be removed once data is avalable on redis
            Set<CreditPoolCreditLineCache> cpcls = cacheService.getCreditPoolCreditLineData();
            cpcls.forEach(s -> objects.add(new JSONObject(s)));
        } else {
            cacheService.findAll(keyPrefix + ":" + key).forEach(s -> objects.add(new JSONObject(s)));
        }
        return new JSONArray(objects);
    }

    @GetMapping(value = "/keys", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getAllKeys() {
        return cacheKeys.getPath().keySet().stream().filter(key -> key.startsWith(getControllerKeyPrefix()+"."))
                .map(key -> key.replaceAll(getControllerKeyPrefix()+"\\.", "")).sorted().collect(Collectors.toList());
    }
    
    @GetMapping(value = "/{key}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public String findAll(@PathVariable("key") String key, @PathVariable("id") String id) {
        Function<String,String> handler = getHandler(key);
        if (handler != null) {
            return handler.apply(id);
        }
        LOGGER.debug("Calling find all on cache controller with key <{}> and id <{}>", key, id);
        return this.findAll(key + ":" + id);
    }
    
    protected Function<String, String> getHandler(String key) {
        return null;
    }

    protected void publishUpdates(String topic, Supplier<Object> supplier) {
        if (webSocketsConfig.getTopicCount(topic) > 0) {
            LOGGER.debug("sending topic <{}> updates to clients", topic);
            webSocketsTaskExecutor.execute(() -> template.convertAndSend(topic, supplier.get()));
        }
    }
    
    protected void convertAndSend(String topic, Function<String,Object> function, String key) {
        if (webSocketsConfig.getTopicCount(topic) > 0) {
            LOGGER.debug("sending topic <{}> updates to clients", topic);
            webSocketsTaskExecutor.execute(() -> template.convertAndSend(topic, function.apply(key)));
        }
    }
    
    protected abstract String getControllerKeyPrefix();
}