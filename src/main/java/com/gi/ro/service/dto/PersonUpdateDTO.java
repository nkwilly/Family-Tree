package com.gi.ro.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonUpdateDTO {
    @NotBlank(message = "L'id est obligatoire dans une requête update")
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String country;
    private String city;
    private Date born;
    private Date death;
    private String photoUrl;
    private UUID unionId;
    private int sex; // O pour male et le reste pour female
}