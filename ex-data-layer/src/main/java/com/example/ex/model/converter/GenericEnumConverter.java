package com.example.ex.model.converter;

import java.util.Arrays;

import javax.persistence.AttributeConverter;

public abstract class GenericEnumConverter<E extends Enum<E>> implements AttributeConverter<E, String> {
    
    private Class<E> clazz;
    
    public GenericEnumConverter(Class<E> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public String convertToDatabaseColumn(E attribute) {
        if (attribute != null) {
            return attribute.toString();
        }
        return null;
    }
    
    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return Arrays.stream(clazz.getEnumConstants()).filter(type -> type.toString().equals(dbData)).findFirst().orElse(null);
        }
        return null;
    }
    
}