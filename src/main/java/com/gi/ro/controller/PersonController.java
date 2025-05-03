package com.gi.ro.controller;

import com.gi.ro.entity.Female;
import com.gi.ro.entity.Male;
import com.gi.ro.service.MapperService;
import com.gi.ro.service.PersonService;
import com.gi.ro.service.dto.PersonCreateDTO;
import com.gi.ro.service.dto.PersonDTO;
import com.gi.ro.service.dto.PersonUpdateDTO;
import com.gi.ro.service.dto.PersonWithIdDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Personnes", description = "API pour gérer les personnes (hommes et femmes) de l'arbre généalogique")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService<Male> maleService;
    private final PersonService<Female> femaleService;
    private final MapperService mapperService;

    @Operation(summary = "Récupérer toutes les personnes",
            description = "Récupère la liste complète des hommes et femmes dans l'arbre généalogique")
    @ApiResponse(responseCode = "200", description = "Liste des personnes récupérée avec succès")
    @GetMapping("/persons")
    public ResponseEntity<List<PersonWithIdDTO>> getAllPersons() {
        log.info("Hello world1");
        List<PersonWithIdDTO> males = maleService.findAll().stream().map(mapperService::toPersonWithIdDTO).toList();
        log.info("Hello world");
        List<PersonWithIdDTO> females = femaleService.findAll().stream().map(mapperService::toPersonWithIdDTO).toList();
        log.info("Hello world2");
        List<PersonWithIdDTO> persons = new ArrayList<>();
        persons.addAll(males);
        persons.addAll(females);
        return ResponseEntity.ok(persons);
    }

    @Operation(summary = "Récupérer une personne par ID",
            description = "Récupère une personne (homme ou femme) par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personne trouvée"),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée", content = @Content)
    })
    @GetMapping("/persons/{id}")
    public ResponseEntity<PersonWithIdDTO> getPersonById(
            @Parameter(description = "Identifiant unique de la personne") @PathVariable UUID id) {
        Optional<Male> male = maleService.findById(id);
        if (male.isPresent())
            return ResponseEntity.ok(male.map(mapperService::toPersonWithIdDTO).get());

        Optional<Female> female = femaleService.findById(id);
        if (female.isPresent())
            return ResponseEntity.ok(female.map(mapperService::toPersonWithIdDTO).get());

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Créer une nouvelle personne",
            description = "Crée une nouvelle entrée pour une personne (homme ou femme) dans l'arbre généalogique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personne créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Type de personne non reconnu", content = @Content)
    })
    @PostMapping("/persons")
    public ResponseEntity<?> createPerson(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données de la personne à créer")
           @Valid @RequestBody PersonDTO person) {
        if (person.getSex() == 0) {
            Male requestMale = mapperService.toMale(person);
            Male saved = maleService.save(requestMale);
            URI location = this.buildLocation(saved.getId(), "/{id}");
            return ResponseEntity.created(location).body(saved);
        } else {
            Female requestFemale = mapperService.toFemale(person);
            Female saved = femaleService.save(requestFemale);
            URI location = this.buildLocation(saved.getId(), "/{id}");
            return ResponseEntity.created(location).body(saved);
        }
    }

    @Operation(summary = "Mettre à jour une personne",
            description = "Met à jour les données d'une personne existante dans l'arbre généalogique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personne mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Type de personne non reconnu", content = @Content),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée", content = @Content)
    })
    @PutMapping("/persons/{id}")
    public ResponseEntity<PersonWithIdDTO> updatePerson(
            @Parameter(description = "Identifiant unique de la personne") @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données mises à jour de la personne")
            @Valid @RequestBody PersonUpdateDTO person) {
        if(person.getSex() == 0) {
            Male updateMale = mapperService.personUpdateDTOToMale(person);
            Male saved = maleService.update(updateMale.getId(), updateMale);
            return ResponseEntity.ok(mapperService.toPersonWithIdDTO(saved));
        }
        return ResponseEntity.badRequest().body(new PersonWithIdDTO());
    }

    @Operation(summary = "Supprimer une personne",
            description = "Supprime une personne de l'arbre généalogique par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Personne supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée", content = @Content)
    })
    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "Identifiant unique de la personne") @PathVariable UUID id) {
        Optional<Male> male = maleService.findById(id);
        if (male.isPresent()) {
            maleService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        Optional<Female> female = femaleService.findById(id);
        if (female.isPresent()) {
            femaleService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Specifics routes for Male
    @Operation(summary = "Récupérer tous les hommes",
            description = "Récupère la liste complète des hommes dans l'arbre généalogique")
    @ApiResponse(responseCode = "200", description = "Liste des hommes récupérée avec succès",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Male.class))))
    @GetMapping("/males")
    public ResponseEntity<List<Male>> getAllMales() {
        return ResponseEntity.ok(maleService.findAll());
    }

    @Operation(summary = "Récupérer un homme par ID",
            description = "Récupère un homme par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Homme trouvé"),
            @ApiResponse(responseCode = "404", description = "Homme non trouvé", content = @Content)
    })
    @GetMapping("/males/{id}")
    public ResponseEntity<Male> getMaleById(
            @Parameter(description = "Identifiant unique de l'homme") @PathVariable UUID id) {
        return maleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un nouvel homme",
            description = "Crée une nouvelle entrée pour un homme dans l'arbre généalogique")
    @ApiResponse(responseCode = "201", description = "Homme créé avec succès")
    @PostMapping("/males")
    public ResponseEntity<Male> createMale(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données de l'homme à créer")
            @Valid @RequestBody PersonCreateDTO personDTO) {
        Male male = mapperService.toMale(personDTO);
        Male savedMale = maleService.save(male);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMale);
    }

    @Operation(summary = "Mettre à jour un homme",
            description = "Met à jour les données d'un homme existant dans l'arbre généalogique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Homme mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Homme non trouvé", content = @Content)
    })
    @PutMapping("/males/{id}")
    public ResponseEntity<Male> updateMale(
            @Parameter(description = "Identifiant unique de l'homme") @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données mises à jour de l'homme")
            @Valid @RequestBody PersonCreateDTO personDTO) {
        Male existingMale = maleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Homme non trouvé avec l'id: " + id));

        // Mapper les champs du DTO vers l'entité existante
        Male updatedMale = mapperService.toMale(personDTO);
        updatedMale.setId(existingMale.getId()); // Conserver l'ID original

        return ResponseEntity.ok(maleService.save(updatedMale));
    }

    @Operation(summary = "Supprimer un homme",
            description = "Supprime un homme de l'arbre généalogique par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Homme supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Homme non trouvé", content = @Content)
    })
    @DeleteMapping("/males/{id}")
    public ResponseEntity<Void> deleteMale(
            @Parameter(description = "Identifiant unique de l'homme") @PathVariable UUID id) {
        maleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Specific routes for Female
    @Operation(summary = "Récupérer toutes les femmes",
            description = "Récupère la liste complète des femmes dans l'arbre généalogique")
    @ApiResponse(responseCode = "200", description = "Liste des femmes récupérée avec succès",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Female.class))))
    @GetMapping("/females")
    public ResponseEntity<List<Female>> getAllFemales() {
        return ResponseEntity.ok(femaleService.findAll());
    }

    @Operation(summary = "Récupérer une femme par ID",
            description = "Récupère une femme par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Femme trouvée"),
            @ApiResponse(responseCode = "404", description = "Femme non trouvée", content = @Content)
    })
    @GetMapping("/females/{id}")
    public ResponseEntity<Female> getFemaleById(
            @Parameter(description = "Identifiant unique de la femme") @PathVariable UUID id) {
        return femaleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer une nouvelle femme",
            description = "Crée une nouvelle entrée pour une femme dans l'arbre généalogique")
    @ApiResponse(responseCode = "201", description = "Femme créée avec succès")
    @PostMapping("/females")
    public ResponseEntity<Female> createFemale(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données de la femme à créer")
            @Valid @RequestBody PersonCreateDTO personDTO) {
        Female female = mapperService.toFemale(personDTO);
        Female savedFemale = femaleService.save(female);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFemale);
    }

    @Operation(summary = "Mettre à jour une femme",
            description = "Met à jour les données d'une femme existante dans l'arbre généalogique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Femme mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Femme non trouvée", content = @Content)
    })
    @PutMapping("/females/{id}")
    public ResponseEntity<Female> updateFemale(
            @Parameter(description = "Identifiant unique de la femme") @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données mises à jour de la femme")
            @Valid @RequestBody PersonCreateDTO personDTO) {
        Female existingFemale = femaleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Femme non trouvée avec l'id: " + id));

        // Mapper les champs du DTO vers l'entité existante
        Female updatedFemale = mapperService.toFemale(personDTO);
        updatedFemale.setId(existingFemale.getId()); // Conserver l'ID original

        return ResponseEntity.ok(femaleService.save(updatedFemale));
    }

    @Operation(summary = "Supprimer une femme",
            description = "Supprime une femme de l'arbre généalogique par son identifiant unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Femme supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Femme non trouvée", content = @Content)
    })
    @DeleteMapping("/females/{id}")
    public ResponseEntity<Void> deleteFemale(
            @Parameter(description = "Identifiant unique de la femme") @PathVariable UUID id) {
        femaleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    private URI buildLocation(UUID uuid, String path) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(uuid)
                .toUri();
    }
}