package com.gi.ro.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnionCreateDTO {

    @NotNull(message = "L'identifiant du mari est obligatoire")
    private UUID husbandId;

    @NotNull(message = "L'identifiant de la femme est obligatoire")
    private UUID wifeId;

    private String date;
    private String place;
    private Boolean church;
}