package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.InstructionType;

public class InstructionTypeConverterTest {

    private InstructionTypeConverter converter = new InstructionTypeConverter();

    @Test
    public void convertCompositeToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(InstructionType.COMPOSITE);
        
        assertThat(dbData).isEqualTo("Composite");
    }
    
    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(InstructionType.NONE);
        
        assertThat(dbData).isEqualTo("None");
    }
    
    @Test
    public void convertCompositeToEnum() {
        InstructionType type = converter.convertToEntityAttribute("Composite");
        
        assertThat(type).isEqualTo(InstructionType.COMPOSITE);
    }

    @Test
    public void convertNoneToEnum() {
        InstructionType type = converter.convertToEntityAttribute("None");
        
        assertThat(type).isEqualTo(InstructionType.NONE);
    }

}
