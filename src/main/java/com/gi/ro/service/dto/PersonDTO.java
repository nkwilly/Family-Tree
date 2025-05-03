package com.gi.ro.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO extends PersonCreateDTO{
    @NotNull(message = "Le sexe doit être précisé")
    private int sex; // male 0 female autre nombre
}
