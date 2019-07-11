package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.CreditLineType;

public class CreditLineTypeConverterTest {

    private CreditLineTypeConverter converter = new CreditLineTypeConverter();

    @Test
    public void convertPrincipalToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(CreditLineType.PRINCIPAL);
        
        assertThat(dbData).isEqualTo("Principal");
    }
    
    @Test
    public void convertPbClientToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(CreditLineType.PBCLIENT);
        
        assertThat(dbData).isEqualTo("PBClient");
    }

    @Test
    public void convertSubPbClientToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(CreditLineType.SUB_PB_CLIENT);
        
        assertThat(dbData).isEqualTo("SubPBClient");
    }

    @Test
    public void convertPrincipalToEnum() {
        CreditLineType type = converter.convertToEntityAttribute("Principal");
        
        assertThat(type).isEqualTo(CreditLineType.PRINCIPAL);
    }
    
    @Test
    public void convertPbClientToEnum() {
        CreditLineType type = converter.convertToEntityAttribute("PBClient");
        
        assertThat(type).isEqualTo(CreditLineType.PBCLIENT);
    }

    @Test
    public void convertSubPbClientToEnum() {
        CreditLineType type = converter.convertToEntityAttribute("SubPBClient");
        
        assertThat(type).isEqualTo(CreditLineType.SUB_PB_CLIENT);
    }

}
