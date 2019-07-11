package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.Symbology;

@Converter(autoApply = true)
public class SymbologyConverter extends GenericEnumConverter<Symbology> {

    public SymbologyConverter() {
        super(Symbology.class);
    }
}