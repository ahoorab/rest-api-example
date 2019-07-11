package com.example.ex.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DataMasterType {
    
    @JsonProperty("VenueRefData")
    VENUE_REF_DATA, 
    @JsonProperty("none")
    NONE;
    
    @Override
    public String toString() {
        return TypeUtils.getJsonName(this);
    }

}
