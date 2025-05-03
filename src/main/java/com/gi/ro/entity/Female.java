package com.gi.ro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "female")
public class Female extends Person {
    @Id
    @GeneratedValue
    private UUID id;

    @Builder
    public Female(UUID id, String name, String email, String phone, String country, String city, Date born, Date death, String photoUrl, UUID unionId) {
        super(name, email, phone, country, city, born, death, photoUrl, unionId);
        this.id = id;
    }
}