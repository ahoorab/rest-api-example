package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.BrokerType;

@Converter(autoApply = true)
public class BrokerTypeConverter extends GenericEnumConverter<BrokerType> {

    public BrokerTypeConverter() {
        super(BrokerType.class);
    }
}