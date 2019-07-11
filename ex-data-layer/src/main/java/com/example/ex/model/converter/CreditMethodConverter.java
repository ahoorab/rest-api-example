package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.CreditMethod;

@Converter(autoApply = true)
public class CreditMethodConverter extends GenericEnumConverter<CreditMethod> {

    public CreditMethodConverter() {
        super(CreditMethod.class);
    }

}