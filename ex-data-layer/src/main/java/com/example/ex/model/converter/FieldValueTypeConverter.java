package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.FieldValueType;

@Converter(autoApply = true)
public class FieldValueTypeConverter extends GenericEnumConverter<FieldValueType> {

    public FieldValueTypeConverter() {
        super(FieldValueType.class);
    }
}