package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Symbology {
    
    @JsonProperty("INET")
    INET;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
