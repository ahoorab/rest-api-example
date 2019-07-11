package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sergio Pintos <spintos@gmail.com>
 */
public enum Status {

    @JsonProperty("Enabled")
    ENABLED, 
    @JsonProperty("Disabled")
    DISABLED;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }
}
