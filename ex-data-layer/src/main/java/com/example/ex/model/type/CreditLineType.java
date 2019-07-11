package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CreditLineType {
    
    @JsonProperty("Principal")
    PRINCIPAL, 
    @JsonProperty("PBClient")
    PBCLIENT,
    @JsonProperty("SubPBClient")
    SUB_PB_CLIENT;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
