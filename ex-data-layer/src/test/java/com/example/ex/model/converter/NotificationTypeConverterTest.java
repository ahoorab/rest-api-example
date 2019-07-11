package com.example.ex.model.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.ex.model.type.NotificationType;

public class NotificationTypeConverterTest {

    private NotificationTypeConverter converter = new NotificationTypeConverter();

    @Test
    public void convertNoneToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(NotificationType.NONE);
        
        assertThat(dbData).isEqualTo("None");
    }

    @Test
    public void convertDropCopyToDatabaseColumn() {
        String dbData = converter.convertToDatabaseColumn(NotificationType.DROPCOPY);
        
        assertThat(dbData).isEqualTo("Dropcopy");
    }
    
    @Test
    public void convertDropCopyToEnum() {
        NotificationType type = converter.convertToEntityAttribute("Dropcopy");
        
        assertThat(type).isEqualTo(NotificationType.DROPCOPY);
    }
    
    @Test
    public void convertNoneToEnum() {
        NotificationType type = converter.convertToEntityAttribute("None");
        
        assertThat(type).isEqualTo(NotificationType.NONE);
    }

}