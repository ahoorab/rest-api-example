package com.example.ex.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ex.rest")
public class WebSocketKeys {

    private Map<String,List<String>> websockets = new HashMap<>();

}