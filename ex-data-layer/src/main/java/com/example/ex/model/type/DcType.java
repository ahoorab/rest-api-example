package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DcType {
    
    @JsonProperty("Principal")
    PRINCIPAL, 
    @JsonProperty("PBClient")
    PBCLIENT,
    @JsonProperty("SubPBClient")
    SUB_PB_CLIENT,
    @JsonProperty("Exx")
    EXX,
    @JsonProperty("None")
    NONE;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
