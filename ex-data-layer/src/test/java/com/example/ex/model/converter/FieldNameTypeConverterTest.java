package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.FieldNameType;

public class FieldNameTypeConverterTest {

    private FieldNameTypeConverter converter = new FieldNameTypeConverter();

    @Test
    public void convertMarketSessionToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(FieldNameType.MARKET_SESSION);
        
        assertThat(dbData).isEqualTo("MarketSession");
    }
    
    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(FieldNameType.NONE);
        
        assertThat(dbData).isEqualTo("none");
    }

    @Test
    public void convertMarketSessionToEnum() {
        FieldNameType type = converter.convertToEntityAttribute("MarketSession");
        
        assertThat(type).isEqualTo(FieldNameType.MARKET_SESSION);
    }
    
    @Test
    public void convertNoneToEnum() {
        FieldNameType type = converter.convertToEntityAttribute("none");
        
        assertThat(type).isEqualTo(FieldNameType.NONE);
    }

}
