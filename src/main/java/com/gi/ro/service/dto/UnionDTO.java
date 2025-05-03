package com.gi.ro.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnionDTO {
    @NotNull(message = "L'id est requis")
    private UUID id;

    private UUID husbandId;

    private UUID wifeId;

    private String date;

    private String place;

    private boolean church;
}
