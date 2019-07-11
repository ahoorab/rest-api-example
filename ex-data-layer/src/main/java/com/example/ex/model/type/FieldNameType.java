package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FieldNameType {
    
    @JsonProperty("MarketSession")
    MARKET_SESSION, 
    @JsonProperty("none")
    NONE;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
