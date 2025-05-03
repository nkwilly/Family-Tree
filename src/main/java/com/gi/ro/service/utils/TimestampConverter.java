package com.gi.ro.service.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Date;

@Converter(autoApply = false)
public class TimestampConverter implements AttributeConverter<Date, String> {

    @Override
    public String convertToDatabaseColumn(Date date) {
        return date != null ? String.valueOf(date.getTime()) : null;
    }

    @Override
    public Date convertToEntityAttribute(String timestamp) {
        return timestamp != null ? new Date(Long.parseLong(timestamp)) : null;
    }
}
