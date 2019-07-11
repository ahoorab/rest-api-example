package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BrokerType {
    
    @JsonProperty("Broker")
    BROKER, 
    @JsonProperty("MPID")
    MPID,
    @JsonProperty("None")
    NONE;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
