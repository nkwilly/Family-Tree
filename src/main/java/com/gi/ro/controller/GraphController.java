package com.gi.ro.controller;

import com.gi.ro.entity.Female;
import com.gi.ro.entity.Male;
import com.gi.ro.entity.Union;
import com.gi.ro.repository.FemaleRepository;
import com.gi.ro.repository.MaleRepository;
import com.gi.ro.repository.UnionRepository;
import com.gi.ro.service.GraphAlgorithms;
import com.gi.ro.service.utils.Edge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/graph")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Graph Algorithms", description = "API pour exécuter des algorithmes de graphe sur l'arbre généalogique")
public class GraphController {

    private final GraphAlgorithms graphAlgorithms;
    private final MaleRepository maleRepository;
    private final FemaleRepository femaleRepository;
    private final UnionRepository unionRepository;

    @Operation(
            summary = "Trouver le plus court chemin entre deux personnes",
            description = "Utilise l'algorithme de Dijkstra pour déterminer le chemin le plus court entre deux personnes dans l'arbre généalogique à partir de leur ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chemin trouvé avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucun chemin trouvé entre les personnes spécifiées", content = @Content)
    })
    @GetMapping("/shortest-path")
    public List<UUID> getShortestPath(
            @Parameter(description = "UUID de la personne source") @RequestParam UUID from,
            @Parameter(description = "UUID de la personne destination") @RequestParam UUID to) {
        Map<UUID, List<Edge>> graph = buildGraph();
        log.info("graph {}", graph.entrySet().stream().map(entry -> entry.getKey().toString() + "\t" + entry.getValue().toString()).collect(Collectors.joining("\n")));
        return graphAlgorithms.dijkstraShortestPath(graph, from, to);
    }

    @Operation(
            summary = "Calculer les distances depuis une personne",
            description = "Utilise l'algorithme de Dijkstra pour calculer les distances entre une personne source et toutes les autres personnes accessibles dans le graphe"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distances calculées avec succès")
    })
    @GetMapping("/distances")
    public Map<UUID, Integer> getDistances(
            @Parameter(description = "UUID de la personne source") @RequestParam UUID from) {
        Map<UUID, List<Edge>> graph = buildGraph();
        return graphAlgorithms.dijkstra(graph, from);
    }

    @Operation(
            summary = "Obtenir un arbre couvrant minimal",
            description = "Utilise l'algorithme de Kruskal pour calculer un arbre couvrant minimal de l'arbre généalogique complet"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arbre couvrant minimal généré avec succès")
    })
    @GetMapping("/spanning-tree")
    public Set<Edge> getSpanningTree() {
        Map<UUID, List<Edge>> graph = buildGraph();
        Set<UUID> nodes = graph.keySet();
        List<Edge> allEdges = new ArrayList<>();
        for (List<Edge> edges : graph.values()) {
            allEdges.addAll(edges);
        }
        return graphAlgorithms.kruskal(nodes, allEdges);
    }

    @Operation(
            summary = "Obtenir un arbre couvrant minimal avec algorithme de Prim",
            description = "Utilise l'algorithme de Prim pour calculer un arbre couvrant minimal à partir d'un nœud racine spécifié"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arbre couvrant minimal généré avec succès"),
            @ApiResponse(responseCode = "404", description = "Nœud racine non trouvé", content = @Content)
    })
    @GetMapping("/spanning-tree/prim")
    public Set<Edge> getPrimSpanningTree(
            @Parameter(description = "UUID du nœud racine pour commencer l'algorithme de Prim") @RequestParam UUID from) {
        Map<UUID, List<Edge>> graph = buildGraph();
        return graphAlgorithms.prim(graph, from);
    }

    /**
     * Méthode utilitaire pour construire le graphe à partir des entités.
     * À adapter selon ta logique de connexion des nœuds (ex : Male/Female <-> Union).
     */
    private Map<UUID, List<Edge>> buildGraph() {
        Map<UUID, List<Edge>> graph = new HashMap<>();

        List<Male> males = maleRepository.findAll();
        List<Female> females = femaleRepository.findAll();
        List<Union> unions = unionRepository.findAll();

        for (Union union : unions) {
            UUID husbandId = union.getHusband().getId();
            UUID wifeId = union.getWife().getId();
            UUID unionId = union.getId();

            Edge edgeMaleUnion = new Edge(husbandId, unionId);
            graph.computeIfAbsent(husbandId, k -> new ArrayList<>()).add(edgeMaleUnion);
            graph.computeIfAbsent(unionId, k -> new ArrayList<>()).add(edgeMaleUnion);

            Edge edgeFemaleUnion = new Edge(wifeId, unionId);
            graph.computeIfAbsent(wifeId, k -> new ArrayList<>()).add(edgeFemaleUnion);
            graph.computeIfAbsent(unionId, k -> new ArrayList<>()).add(edgeFemaleUnion);
        }

        for (Male male : males) {
            graph.putIfAbsent(male.getId(), new ArrayList<>());
        }
        for (Female female : females) {
            graph.putIfAbsent(female.getId(), new ArrayList<>());
        }

        return graph;
    }
}