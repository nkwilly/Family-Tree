package com.gi.ro.controller;

import com.gi.ro.entity.Union;
import com.gi.ro.service.UnionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/unions")
@RequiredArgsConstructor
@Tag(name = "Unions", description = "API pour gérer les unions entre personnes dans l'arbre généalogique")
public class UnionController {

    private final UnionService unionService;

    @Operation(summary = "Récupérer toutes les unions",
            description = "Retourne la liste complète des unions enregistrées dans l'arbre généalogique")
    @ApiResponse(responseCode = "200", description = "Liste des unions récupérée avec succès",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Union.class))))
    @GetMapping
    public ResponseEntity<List<Union>> getAllUnions() {
        List<Union> unions = unionService.findAll();
        return ResponseEntity.ok(unions);
    }

    @Operation(summary = "Récupérer une union par ID",
            description = "Retourne les détails d'une union spécifique identifiée par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Union trouvée"),
            @ApiResponse(responseCode = "404", description = "Union non trouvée", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Union> getUnionById(
            @Parameter(description = "Identifiant unique de l'union") @PathVariable UUID id) {
        Optional<Union> union = unionService.findById(id);
        return union.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer une nouvelle union",
            description = "Enregistre une nouvelle union entre deux personnes dans l'arbre généalogique")
    @ApiResponse(responseCode = "200", description = "Union créée avec succès")
    @PostMapping
    public ResponseEntity<Union> createUnion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données de l'union à créer, incluant les références aux personnes concernées")
            @RequestBody Union union) {
        Union saved = unionService.save(union);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Mettre à jour une union",
            description = "Modifie les informations d'une union existante dans l'arbre généalogique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Union mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Union non trouvée", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Union> updateUnion(
            @Parameter(description = "Identifiant unique de l'union") @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données mises à jour de l'union")
            @RequestBody Union union) {
        Union updated = unionService.update(id, union);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer une union",
            description = "Supprime une union de l'arbre généalogique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Union supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Union non trouvée", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnion(
            @Parameter(description = "Identifiant unique de l'union") @PathVariable UUID id) {
        unionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
