package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.CreditLineType;

@Converter(autoApply = true)
public class CreditLineTypeConverter extends GenericEnumConverter<CreditLineType> {

    public CreditLineTypeConverter() {
        super(CreditLineType.class);
    }

}