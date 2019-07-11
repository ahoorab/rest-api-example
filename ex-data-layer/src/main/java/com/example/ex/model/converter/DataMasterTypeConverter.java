package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.DataMasterType;

@Converter(autoApply = true)
public class DataMasterTypeConverter extends GenericEnumConverter<DataMasterType> {

    public DataMasterTypeConverter() {
        super(DataMasterType.class);
    }
} 