package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.CreditMethod;

public class CreditMethodConverterTest {

    private CreditMethodConverter converter = new CreditMethodConverter();

    @Test
    public void convertNetGreaterToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(CreditMethod.NET_GREATER);
        
        assertThat(dbData).isEqualTo("NetGreater");
    }
    
    @Test
    public void convertNetLongToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(CreditMethod.NET_LONG);
        
        assertThat(dbData).isEqualTo("NetLong");
    }

    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(CreditMethod.NONE);
        
        assertThat(dbData).isEqualTo("None");
    }
    
    @Test
    public void convertNetShortsToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(CreditMethod.NET_SHORTS);
        
        assertThat(dbData).isEqualTo("NetShorts");
    }
    
    @Test
    public void convertNetGreaterToEnum() {
        CreditMethod type = converter.convertToEntityAttribute("NetGreater");
        
        assertThat(type).isEqualTo(CreditMethod.NET_GREATER);
    }
    
    @Test
    public void convertNetLongToEnum() {
        CreditMethod type = converter.convertToEntityAttribute("NetLong");
        
        assertThat(type).isEqualTo(CreditMethod.NET_LONG);
    }

    @Test
    public void convertNoneToEnum() {
        CreditMethod type = converter.convertToEntityAttribute("None");
        
        assertThat(type).isEqualTo(CreditMethod.NONE);
    }

    @Test
    public void convertNetShortsToEnum() {
        CreditMethod type = converter.convertToEntityAttribute("NetShorts");
        
        assertThat(type).isEqualTo(CreditMethod.NET_SHORTS);
    }
}
