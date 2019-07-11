package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.ConnectionType;

@Converter(autoApply = true)
public class ConnectionTypeConverter extends GenericEnumConverter<ConnectionType> {

    public ConnectionTypeConverter() {
        super(ConnectionType.class);
    }

}