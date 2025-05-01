package com.gi.ro.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "unions")
public class Union {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "husband_id")
    private Male husband;

    @OneToOne
    @JoinColumn(name = "wife_id")
    private Female wife;

    private String date;
    private String place;
    private boolean church;
}