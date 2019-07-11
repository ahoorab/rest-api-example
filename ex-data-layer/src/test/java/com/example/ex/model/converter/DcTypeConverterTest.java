package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.DcType;

public class DcTypeConverterTest {

    private DcTypeConverter converter = new DcTypeConverter();

    @Test
    public void convertPrincipalToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(DcType.PRINCIPAL);
        
        assertThat(dbData).isEqualTo("Principal");
    }
    
    @Test
    public void convertPbClientToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(DcType.PBCLIENT);
        
        assertThat(dbData).isEqualTo("PBClient");
    }

    @Test
    public void convertSubPbClientToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(DcType.SUB_PB_CLIENT);
        
        assertThat(dbData).isEqualTo("SubPBClient");
    }

    @Test
    public void convertExxToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(DcType.EXX);
        
        assertThat(dbData).isEqualTo("Exx");
    }

    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(DcType.NONE);
        
        assertThat(dbData).isEqualTo("None");
    }
    
    @Test
    public void convertPrincipalToEnum() {
        DcType type = converter.convertToEntityAttribute("Principal");
        
        assertThat(type).isEqualTo(DcType.PRINCIPAL);
    }
    
    @Test
    public void convertPbClientToEnum() {
        DcType type = converter.convertToEntityAttribute("PBClient");
        
        assertThat(type).isEqualTo(DcType.PBCLIENT);
    }

    @Test
    public void convertSubPbClientToEnum() {
        DcType type = converter.convertToEntityAttribute("SubPBClient");
        
        assertThat(type).isEqualTo(DcType.SUB_PB_CLIENT);
    }

    @Test
    public void convertExxToEnum() {
        DcType type = converter.convertToEntityAttribute("Exx");
        
        assertThat(type).isEqualTo(DcType.EXX);
    }

    @Test
    public void convertNoneToEnum() {
        DcType type = converter.convertToEntityAttribute("None");
        
        assertThat(type).isEqualTo(DcType.NONE);
    }

}
