package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.InstructionType;

@Converter(autoApply = true)
public class InstructionTypeConverter extends GenericEnumConverter<InstructionType> {

    public InstructionTypeConverter() {
        super(InstructionType.class);
    }
}