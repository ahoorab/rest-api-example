package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sergio Pintos <spintos@gmail.com>
 */
public enum RoleType {

    @JsonProperty("None")
    NONE, 
    @JsonProperty("Broker")
    BROKER,
    @JsonProperty("Trading")
    TRADING;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }
}