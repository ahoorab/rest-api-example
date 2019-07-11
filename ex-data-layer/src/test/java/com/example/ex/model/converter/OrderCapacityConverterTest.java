package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.OrderCapacity;

public class OrderCapacityConverterTest {

    private OrderCapacityConverter converter = new OrderCapacityConverter();

    @Test
    public void convertAgencyToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(OrderCapacity.AGENCY);
        
        assertThat(dbData).isEqualTo("Agency");
    }
    
    @Test
    public void convertPrincipalToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(OrderCapacity.PRINCIPAL);
        
        assertThat(dbData).isEqualTo("Principal");
    }

    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(OrderCapacity.NONE);
        
        assertThat(dbData).isEqualTo("None");
    }
    
    @Test
    public void convertAgencyCloseToEnum() {
        OrderCapacity type = converter.convertToEntityAttribute("Agency");
        
        assertThat(type).isEqualTo(OrderCapacity.AGENCY);
    }
    
    @Test
    public void convertPrincipalToEnum() {
        OrderCapacity type = converter.convertToEntityAttribute("Principal");
        
        assertThat(type).isEqualTo(OrderCapacity.PRINCIPAL);
    }

    @Test
    public void convertNoneToEnum() {
        OrderCapacity type = converter.convertToEntityAttribute("None");
        
        assertThat(type).isEqualTo(OrderCapacity.NONE);
    }

}
