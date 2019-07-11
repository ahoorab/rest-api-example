package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.OrderCapacity;

@Converter(autoApply = true)
public class OrderCapacityConverter extends GenericEnumConverter<OrderCapacity> {

    public OrderCapacityConverter() {
        super(OrderCapacity.class);
    }

}