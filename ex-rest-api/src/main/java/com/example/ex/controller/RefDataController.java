package com.example.ex.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/"+"${ex.redis.path.refdata}")
public class RefDataController extends CacheController {

    @Value("${ex.redis.path.refdata}")
    private String refDataPath;

    @Override
    protected String getControllerKeyPrefix() {
        return refDataPath;
    }
    
}