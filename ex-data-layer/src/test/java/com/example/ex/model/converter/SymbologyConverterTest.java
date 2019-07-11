package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.Symbology;

public class SymbologyConverterTest {

    private SymbologyConverter converter = new SymbologyConverter();

    @Test
    public void convertInetToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(Symbology.INET);
        
        assertThat(dbData).isEqualTo("INET");
    }
    
    @Test
    public void convertInetToEnum() {
        Symbology type = converter.convertToEntityAttribute("INET");
        
        assertThat(type).isEqualTo(Symbology.INET);
    }

}
