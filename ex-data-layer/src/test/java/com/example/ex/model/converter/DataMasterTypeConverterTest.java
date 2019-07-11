package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.DataMasterType;

public class DataMasterTypeConverterTest {

    private DataMasterTypeConverter converter = new DataMasterTypeConverter();

    @Test
    public void convertVenueRefDataToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(DataMasterType.VENUE_REF_DATA);
        
        assertThat(dbData).isEqualTo("VenueRefData");
    }
    
    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(DataMasterType.NONE);
        
        assertThat(dbData).isEqualTo("none");
    }

    @Test
    public void convertVenueRefDataToEnum() {
        DataMasterType type = converter.convertToEntityAttribute("VenueRefData");
        
        assertThat(type).isEqualTo(DataMasterType.VENUE_REF_DATA);
    }
    
    @Test
    public void convertNoneToEnum() {
        DataMasterType type = converter.convertToEntityAttribute("none");
        
        assertThat(type).isEqualTo(DataMasterType.NONE);
    }

}