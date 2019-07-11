package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.Status;

public class StatusConverterTest {
    
    private StatusConverter converter = new StatusConverter();

    @Test
    public void convertEnabledToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(Status.ENABLED);
        
        assertThat(dbData).isEqualTo("Enabled");
    }
    
    @Test
    public void convertDisabledToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(Status.DISABLED);
        
        assertThat(dbData).isEqualTo("Disabled");
    }

    @Test
    public void convertEnabledToEnum() {
        Status type = converter.convertToEntityAttribute("Enabled");
        
        assertThat(type).isEqualTo(Status.ENABLED);
    }

    @Test
    public void convertDisabledToEnum() {
        Status type = converter.convertToEntityAttribute("Disabled");
        
        assertThat(type).isEqualTo(Status.DISABLED);
    }

}
