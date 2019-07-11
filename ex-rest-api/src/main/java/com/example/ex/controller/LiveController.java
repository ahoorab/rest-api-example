package com.example.ex.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.configuration.WebSocketsConfig;
import com.example.ex.model.entity.cache.CreditMatrixEntry;

@RestController
@RequestMapping("/"+"${ex.redis.path.live}")
public class LiveController extends CacheController {
    
    public static final String CREDIT_MATRIX_URI = "/creditmatrix";
    public static final String WEB_SOCKET_CREDIT_MATRIX = WebSocketsConfig.WEB_SOCKET_DATA_PREFIX + CREDIT_MATRIX_URI;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LiveController.class);

    @Value("${ex.redis.path.live}")
    protected String livePath;
    @Value("${ex.redis.path.live.tradereports}")
    protected String tradeReportsKey;
    
    @GetMapping(value = CREDIT_MATRIX_URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CreditMatrixEntry> getCreditMatrix() {
        LOGGER.debug("getting credit matrix");
        List<CreditMatrixEntry> creditMatrix = cacheService.getCreditMatrix();
        LOGGER.debug("credit matrix is <{}>", creditMatrix);
        return creditMatrix;
    }
    
    @Scheduled(fixedDelayString = "${ex.rest.live.delay}")
    public void publishCreditMatrixDataUpdates(){
        publishUpdates(WEB_SOCKET_CREDIT_MATRIX, () -> cacheService.getUtilizationAmounts());
    }

    @Scheduled(fixedDelayString = "${ex.rest.live.delay}")
    public void publishKeyUpdates(){
        super.webSockets.getWebsockets().get(getControllerKeyPrefix()).forEach(key -> {
            String redisKey = cacheKeys.getPath().get(getControllerKeyPrefix() + "." + key);
            Set<String> keys = cacheService.getAllKeys(keyPrefix + ":" + redisKey);
            String topic = WebSocketsConfig.WEB_SOCKET_DATA_PREFIX+"/"+getControllerKeyPrefix()+"/"+key;
            if (keys.size() == 1 && keys.iterator().next().equals(keyPrefix + ":" + redisKey)) {
                LOGGER.debug("running thread to publish updates for topic <{}> and key <{}>", topic, key);
                super.convertAndSend(topic,this::findValues,key);
            } else {
                Set<String> ids = keys.stream().map(k -> {
                    String[] split = k.split(":");
                    return split[split.length-1];
                }).collect(Collectors.toSet());
                for (String id : ids) {
                    String topicWithId = topic+"/"+id;
                    LOGGER.debug("running thread to publish updates for topic <{}> and key <{}> and id <{}> and rediskey <{}:{}:{}>",topicWithId,key,id,keyPrefix,key,id);
                    String redisKeyWithId = keyPrefix + ":" + key + ":" + id;
                    super.convertAndSend(topicWithId, this::findAll,redisKeyWithId);
                }
            }
        });
    }
    
    @Override
    protected String getControllerKeyPrefix() {
        return livePath;
    }
    
}