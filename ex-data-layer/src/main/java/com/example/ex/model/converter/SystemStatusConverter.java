package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.SystemStatus;

@Converter(autoApply = true)
public class SystemStatusConverter extends GenericEnumConverter<SystemStatus> {

    public SystemStatusConverter() {
        super(SystemStatus.class);
    }

}