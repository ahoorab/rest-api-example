package com.example.ex.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/"+"${ex.redis.path.book}")
public class BookController extends CacheController {
    
    public static final String BOOKED_ORDERS_URI = "/bookedorders";
    
    @Value("${ex.redis.path.book}")
    private String bookPath;
    @Value("${ex.redis.path.book.bookedorders}")
    private String bookedOrdersPath;
    private Map<String,Function<String, String>> handlers;
    
    @PostConstruct
    public void setHandlers() {
        handlers = new HashMap<>();       
        handlers.put(bookedOrdersPath, cacheService::getBookedOrders);
    }

    @Override
    protected String getControllerKeyPrefix() {
        return bookPath;
    }
    
    @Override
    protected Function<String, String> getHandler(String key) {
        return handlers.get(key);
    }
    
}