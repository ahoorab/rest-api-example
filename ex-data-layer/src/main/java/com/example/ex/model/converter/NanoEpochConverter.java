package com.example.ex.model.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.AttributeConverter;

public class NanoEpochConverter implements AttributeConverter<String, Long> {
    
    private DateFormat dateFormat;
    
    public NanoEpochConverter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
    }

    @Override
    public Long convertToDatabaseColumn(String attribute) {
        throw new UnsupportedOperationException("Database table is read only");
    }
    
    @Override
    public String convertToEntityAttribute(Long dbData) {
        if (dbData != null) {
            String date = dbData.toString().substring(0, dbData.toString().length() - 3);
            return dateFormat.format(new Date(Long.parseLong(date)/1000));
        }
        return null;
    }
}
