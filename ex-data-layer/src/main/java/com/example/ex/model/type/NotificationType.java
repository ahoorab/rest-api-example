package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sergio Pintos <spintos@gmail.com>
 */
public enum NotificationType {

    @JsonProperty("None")
    NONE, 
    @JsonProperty("Dropcopy")
    DROPCOPY;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }
}
