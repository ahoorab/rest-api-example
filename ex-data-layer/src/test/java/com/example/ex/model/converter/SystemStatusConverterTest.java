package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.SystemStatus;

public class SystemStatusConverterTest {
    
    private SystemStatusConverter converter = new SystemStatusConverter();

    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(SystemStatus.NONE);
        
        assertThat(dbData).isEqualTo("none");
    }
    
    @Test
    public void convertOffToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(SystemStatus.OFF);
        
        assertThat(dbData).isEqualTo("off");
    }

    @Test
    public void convertPostTradeToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(SystemStatus.POSTTRADE);
        
        assertThat(dbData).isEqualTo("posttrade");
    }

    @Test
    public void convertPreTradeToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(SystemStatus.PRETRADE);
        
        assertThat(dbData).isEqualTo("pretrade");
    }

    @Test
    public void convertStartedToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(SystemStatus.STARTED);
        
        assertThat(dbData).isEqualTo("started");
    }

    @Test
    public void convertTradingToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(SystemStatus.TRADING);
        
        assertThat(dbData).isEqualTo("trading");
    }

    @Test
    public void convertNoneToEnum() {
        SystemStatus type = converter.convertToEntityAttribute("none");
        
        assertThat(type).isEqualTo(SystemStatus.NONE);
    }

    @Test
    public void convertOffToEnum() {
        SystemStatus type = converter.convertToEntityAttribute("off");
        
        assertThat(type).isEqualTo(SystemStatus.OFF);
    }

    @Test
    public void convertPostTradeToEnum() {
        SystemStatus type = converter.convertToEntityAttribute("posttrade");
        
        assertThat(type).isEqualTo(SystemStatus.POSTTRADE);
    }

    @Test
    public void convertPreTradeToEnum() {
        SystemStatus type = converter.convertToEntityAttribute("pretrade");
        
        assertThat(type).isEqualTo(SystemStatus.PRETRADE);
    }

    @Test
    public void convertStartedToEnum() {
        SystemStatus type = converter.convertToEntityAttribute("started");
        
        assertThat(type).isEqualTo(SystemStatus.STARTED);
    }

    @Test
    public void convertTradingToEnum() {
        SystemStatus type = converter.convertToEntityAttribute("trading");
        
        assertThat(type).isEqualTo(SystemStatus.TRADING);
    }

}