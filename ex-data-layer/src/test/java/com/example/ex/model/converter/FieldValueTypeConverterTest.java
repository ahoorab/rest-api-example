package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.FieldValueType;

public class FieldValueTypeConverterTest {

    private FieldValueTypeConverter converter = new FieldValueTypeConverter();

    @Test
    public void convertMarketCloseToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(FieldValueType.MARKET_CLOSE);
        
        assertThat(dbData).isEqualTo("MarketClose");
    }
    
    @Test
    public void convertMarketOpenToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(FieldValueType.MARKET_OPEN);
        
        assertThat(dbData).isEqualTo("MarketOpen");
    }

    @Test
    public void convertSystemCloseToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(FieldValueType.SYSTEM_CLOSE);
        
        assertThat(dbData).isEqualTo("SystemClose");
    }
    
    @Test
    public void convertSystemOpenToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(FieldValueType.SYSTEM_OPEN);
        
        assertThat(dbData).isEqualTo("SystemOpen");
    }
    
    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(FieldValueType.NONE);
        
        assertThat(dbData).isEqualTo("none");
    }
    
    @Test
    public void convertMarketCloseToEnum() {
        FieldValueType type = converter.convertToEntityAttribute("MarketClose");
        
        assertThat(type).isEqualTo(FieldValueType.MARKET_CLOSE);
    }
    
    @Test
    public void convertMarketOpenToEnum() {
        FieldValueType type = converter.convertToEntityAttribute("MarketOpen");
        
        assertThat(type).isEqualTo(FieldValueType.MARKET_OPEN);
    }

    @Test
    public void convertSystemCloseToEnum() {
        FieldValueType type = converter.convertToEntityAttribute("SystemClose");
        
        assertThat(type).isEqualTo(FieldValueType.SYSTEM_CLOSE);
    }
    
    @Test
    public void convertSystemOpenToEnum() {
        FieldValueType type = converter.convertToEntityAttribute("SystemOpen");
        
        assertThat(type).isEqualTo(FieldValueType.SYSTEM_OPEN);
    }
    
    @Test
    public void convertNoneToEnum() {
        FieldValueType type = converter.convertToEntityAttribute("none");
        
        assertThat(type).isEqualTo(FieldValueType.NONE);
    }

}
