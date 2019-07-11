package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.RoleType;

@Converter(autoApply = true)
public class RoleTypeConverter extends GenericEnumConverter<RoleType> {

    public RoleTypeConverter() {
        super(RoleType.class);
    }

}