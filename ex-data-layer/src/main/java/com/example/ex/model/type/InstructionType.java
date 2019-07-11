package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum InstructionType {
    
    @JsonProperty("Composite")
    COMPOSITE,
    @JsonProperty("None")
    NONE;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
