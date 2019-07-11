package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sergio Pintos <spintos@gmail.com>
 */
public enum SystemStatus {

    @JsonProperty("none")
    NONE, 
    @JsonProperty("off")
    OFF, 
    @JsonProperty("started")
    STARTED, 
    @JsonProperty("pretrade")
    PRETRADE, 
    @JsonProperty("trading")
    TRADING, 
    @JsonProperty("postrade")
    POSTTRADE;
    
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
