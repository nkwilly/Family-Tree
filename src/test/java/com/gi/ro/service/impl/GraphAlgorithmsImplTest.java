package com.gi.ro.service.impl;

import com.gi.ro.service.utils.Edge;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgorithmsImplTest {
    private final GraphAlgorithmsImpl graphAlgorithms = new GraphAlgorithmsImpl();
    private final Map<UUID, List<Edge>> graph = new HashMap<>();
    private final UUID nodeA = UUID.randomUUID();
    private final UUID nodeB = UUID.randomUUID();
    private final UUID nodeC = UUID.randomUUID();
    private final UUID nodeD = UUID.randomUUID();
    private final UUID nodeE = UUID.randomUUID();
    private final UUID nodeF = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        System.out.println("Node A: " + nodeA);
        System.out.println("Node B: " + nodeB);
        System.out.println("Node C: " + nodeC);
        System.out.println("Node D: " + nodeD);
        System.out.println("Node E: " + nodeE);
        System.out.println("Node F: " + nodeF + "\n");

        Edge edgeAB = new Edge();
        edgeAB.from = nodeA;
        edgeAB.to = nodeB;

        Edge edgeBC = new Edge();
        edgeBC.from = nodeB;
        edgeBC.to = nodeC;

        Edge edgeAD = new Edge();
        edgeAD.from = nodeA;
        edgeAD.to = nodeD;

        Edge edgeBE = new Edge();
        edgeBE.from = nodeB;
        edgeBE.to = nodeE;

        Edge edgeDE = new Edge();
        edgeDE.from = nodeD;
        edgeDE.to = nodeE;

        Edge edgeCF = new Edge();
        edgeCF.from = nodeC;
        edgeCF.to = nodeF;

        graph.put(nodeA, new ArrayList<>(Arrays.asList(edgeAB, edgeAD)));
        graph.put(nodeB, new ArrayList<>(Arrays.asList(edgeAB, edgeBC, edgeBE)));
        graph.put(nodeC, new ArrayList<>(Arrays.asList(edgeBC, edgeCF)));
        graph.put(nodeD, new ArrayList<>(Arrays.asList(edgeAD, edgeDE)));
        graph.put(nodeE, new ArrayList<>(Arrays.asList(edgeBE, edgeDE)));
        graph.put(nodeF, new ArrayList<>(Arrays.asList(edgeCF)));
    }

    @Test
    void dijkstra() {
        Map<UUID, Integer> dijkstraSourceA = graphAlgorithms.dijkstra(graph, nodeA);
        System.out.println("\nResult\n");
        for (UUID uuid : dijkstraSourceA.keySet())
            System.out.println("UUID: " + uuid + "\tdistance : " + dijkstraSourceA.get(uuid));
    }

    @Test
    void dijkstraShortestPath() {
        List<UUID> shortestPath = graphAlgorithms.dijkstraShortestPath(graph, nodeA, nodeF);

        System.out.println("\nResult\n");
        for (UUID uuid : shortestPath)
            System.out.println("UUID: " + uuid);
    }

    @Test
    void kruskal() {
    }

    @Test
    void prim() {
    }
}