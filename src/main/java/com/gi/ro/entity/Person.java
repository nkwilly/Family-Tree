package com.gi.ro.entity;

import com.gi.ro.service.utils.TimestampConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@MappedSuperclass
@EqualsAndHashCode
public abstract class Person {
    protected String name;
    protected String email;
    protected String phone;
    protected String country;
    protected String city;
    @Convert(converter = TimestampConverter.class)
    protected Date born;
    @Convert(converter = TimestampConverter.class)
    protected Date death;
    protected String photoUrl;
    protected UUID unionId;
}