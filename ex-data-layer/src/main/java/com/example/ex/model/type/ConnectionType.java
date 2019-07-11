package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ConnectionType {
    
    @JsonProperty("initiator")
    INITIATOR, 
    @JsonProperty("acceptor")
    ACCEPTOR;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
