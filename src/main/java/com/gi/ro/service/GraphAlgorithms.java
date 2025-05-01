package com.gi.ro.service;

import com.gi.ro.service.utils.Edge;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface GraphAlgorithms {
    // Dijkstra: retourne la distance minimale depuis source vers chaque sommet
    Map<UUID, Integer> dijkstra(Map<UUID, List<Edge>> graph, UUID source);

    // Dijkstra: retourne le chemin le plus court entre source et target
    List<UUID> dijkstraShortestPath(Map<UUID, List<Edge>> graph, UUID source, UUID target);

    // Kruskal: retourne l'ensemble des arêtes de l'arbre couvrant de poids minimal
    Set<Edge> kruskal(Set<UUID> nodes, List<Edge> edges);

    // Prim: retourne l'ensemble des arêtes de l'arbre couvrant minimal à partir d'un sommet donné
    Set<Edge> prim(Map<UUID, List<Edge>> graph, UUID start);
}