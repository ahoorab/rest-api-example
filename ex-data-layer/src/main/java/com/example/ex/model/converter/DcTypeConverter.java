package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.DcType;

@Converter(autoApply = true)
public class DcTypeConverter extends GenericEnumConverter<DcType> {

    public DcTypeConverter() {
        super(DcType.class);
    }

}