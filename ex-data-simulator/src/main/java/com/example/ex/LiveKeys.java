package com.example.ex;

import java.util.HashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ex.redis.path.live")
public class LiveKeys {

    private HashMap<String,String> path = new HashMap<>();

}
