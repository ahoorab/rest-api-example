package com.example.ex.model.type;

import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeUtils {
    
    private TypeUtils() { }

    public static String getJsonName(Enum<?> type) {
        try {
            Field name = type.getClass().getField(type.name());
            JsonProperty property = name.getAnnotation(JsonProperty.class);
            return property.value();
        } catch (NoSuchFieldException | SecurityException e ) {
            return "";
        }
    }
}
