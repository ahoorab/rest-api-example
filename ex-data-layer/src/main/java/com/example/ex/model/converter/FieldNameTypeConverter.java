package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.FieldNameType;

@Converter(autoApply = true)
public class FieldNameTypeConverter extends GenericEnumConverter<FieldNameType> {

    public FieldNameTypeConverter() {
        super(FieldNameType.class);
    }
}