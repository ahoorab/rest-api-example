package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.ConnectionType;

public class ConnectionTypeConverterTest {

    private ConnectionTypeConverter converter = new ConnectionTypeConverter();

    @Test
    public void convertAcceptorToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(ConnectionType.ACCEPTOR);
        
        assertThat(dbData).isEqualTo("acceptor");
    }
    
    @Test
    public void convertInitiatorToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(ConnectionType.INITIATOR);
        
        assertThat(dbData).isEqualTo("initiator");
    }

    @Test
    public void convertAcceptorToEnum() {
        ConnectionType type = converter.convertToEntityAttribute("acceptor");
        
        assertThat(type).isEqualTo(ConnectionType.ACCEPTOR);
    }
    
    @Test
    public void convertInitiatorToEnum() {
        ConnectionType type = converter.convertToEntityAttribute("initiator");
        
        assertThat(type).isEqualTo(ConnectionType.INITIATOR);
    }

}
