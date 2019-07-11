package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.RoleType;

public class RoleTypeConverterTest {

    private RoleTypeConverter converter = new RoleTypeConverter();

    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(RoleType.NONE);
        
        assertThat(dbData).isEqualTo("None");
    }

    @Test
    public void convertTradingToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(RoleType.TRADING);
        
        assertThat(dbData).isEqualTo("Trading");
    }
    
    @Test
    public void convertBrokerToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(RoleType.BROKER);
        
        assertThat(dbData).isEqualTo("Broker");
    }
    
    @Test
    public void convertDrokerToEnum() {
        RoleType type = converter.convertToEntityAttribute("Broker");
        
        assertThat(type).isEqualTo(RoleType.BROKER);
    }
    
    @Test
    public void convertTradingToEnum() {
        RoleType type = converter.convertToEntityAttribute("Trading");
        
        assertThat(type).isEqualTo(RoleType.TRADING);
    }
    
    @Test
    public void convertNoneToEnum() {
        RoleType type = converter.convertToEntityAttribute("None");
        
        assertThat(type).isEqualTo(RoleType.NONE);
    }

}