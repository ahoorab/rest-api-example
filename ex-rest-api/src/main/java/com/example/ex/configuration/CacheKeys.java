package com.example.ex.configuration;

import java.util.HashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ex.redis")
public class CacheKeys {

    private HashMap<String,String> path = new HashMap<>();

}