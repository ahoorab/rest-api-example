package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.BrokerType;

public class BrokerTypeConverterTest {

    private BrokerTypeConverter converter = new BrokerTypeConverter();

    @Test
    public void convertBrokerToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(BrokerType.BROKER);
        
        assertThat(dbData).isEqualTo("Broker");
    }
    
    @Test
    public void convertMpidToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(BrokerType.MPID);
        
        assertThat(dbData).isEqualTo("MPID");
    }

    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(BrokerType.NONE);
        
        assertThat(dbData).isEqualTo("None");
    }
    
    @Test
    public void convertBrokerToEnum() {
        BrokerType type = converter.convertToEntityAttribute("Broker");
        
        assertThat(type).isEqualTo(BrokerType.BROKER);
    }
    
    @Test
    public void convertMpidToEnum() {
        BrokerType type = converter.convertToEntityAttribute("MPID");
        
        assertThat(type).isEqualTo(BrokerType.MPID);
    }

    @Test
    public void convertNoneToEnum() {
        BrokerType type = converter.convertToEntityAttribute("None");
        
        assertThat(type).isEqualTo(BrokerType.NONE);
    }

}
