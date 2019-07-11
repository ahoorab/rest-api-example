package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.Status;

@Converter(autoApply = true)
public class StatusConverter extends GenericEnumConverter<Status> {

    public StatusConverter() {
        super(Status.class);
    }

}