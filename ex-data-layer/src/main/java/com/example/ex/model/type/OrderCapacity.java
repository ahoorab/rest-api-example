package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderCapacity {
    
    @JsonProperty("Principal")
    PRINCIPAL, 
    @JsonProperty("Agency")
    AGENCY,
    @JsonProperty("None")
    NONE;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
