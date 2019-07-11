package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FieldValueType {
    
    @JsonProperty("MarketOpen")
    MARKET_OPEN, 
    @JsonProperty("MarketClose")
    MARKET_CLOSE, 
    @JsonProperty("SystemOpen")
    SYSTEM_OPEN, 
    @JsonProperty("SystemClose")
    SYSTEM_CLOSE, 
    @JsonProperty("none")
    NONE;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
