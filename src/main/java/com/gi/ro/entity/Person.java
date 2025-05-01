package com.gi.ro.entity;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class Person {
    protected String name;
    protected String email;
    protected String phone;
    protected String country;
    protected String city;
    protected Date born;
    protected Date death;
    protected String photoUrl;
    protected String unionId; // UUID as String
}