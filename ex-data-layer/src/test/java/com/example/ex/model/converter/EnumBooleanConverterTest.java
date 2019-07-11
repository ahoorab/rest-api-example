package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EnumBooleanConverterTest {

    private EnumBooleanConverter converter = new EnumBooleanConverter();

    @Test
    public void convertTrueToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(true);
        
        assertThat(dbData).isEqualTo("true");
    }
    
    @Test
    public void convertFalseToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(false);
        
        assertThat(dbData).isEqualTo("false");
    }

    @Test
    public void convertTrueToEnum() {
        Boolean bool = converter.convertToEntityAttribute("true");
        
        assertThat(bool).isEqualTo(true);
    }

    @Test
    public void convertFalseToEnum() {
        Boolean bool = converter.convertToEntityAttribute("false");
        
        assertThat(bool).isEqualTo(false);
    }
    
}
