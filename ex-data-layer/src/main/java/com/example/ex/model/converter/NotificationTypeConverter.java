package com.example.ex.model.converter;

import javax.persistence.Converter;

import com.example.ex.model.type.NotificationType;

@Converter(autoApply = true)
public class NotificationTypeConverter extends GenericEnumConverter<NotificationType> {

    public NotificationTypeConverter() {
        super(NotificationType.class);
    }

}