package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sergio Pintos <spintos@gmail.com>
 */
public enum CreditMethod {

    @JsonProperty("None")
    NONE, 
    @JsonProperty("NetShorts")
    NET_SHORTS,
    @JsonProperty("NetLong")
    NET_LONG,
    @JsonProperty("NetGreater")
    NET_GREATER;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }
}
