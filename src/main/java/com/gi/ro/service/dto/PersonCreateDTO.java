package com.gi.ro.service.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonCreateDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    private String email;
    private String phone;
    private String country;
    private String city;
    private Date born;
    private Date death;
    private String photoUrl;
    private String unionId;
}
