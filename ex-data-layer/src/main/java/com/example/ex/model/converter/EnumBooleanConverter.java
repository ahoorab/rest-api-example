package com.example.ex.model.converter;

import javax.persistence.AttributeConverter;

public class EnumBooleanConverter implements AttributeConverter<Boolean, String>{

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute != null) {
            return attribute.toString().toLowerCase();
        }
        return null;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return Boolean.valueOf(dbData);
        }
        return null;
    }

}