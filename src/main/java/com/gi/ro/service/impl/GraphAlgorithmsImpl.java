package com.gi.ro.service.impl;

import com.gi.ro.service.GraphAlgorithms;
import com.gi.ro.service.utils.Edge;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@NoArgsConstructor
public class GraphAlgorithmsImpl implements GraphAlgorithms {

    @Override
    public Map<UUID, Integer> dijkstra(Map<UUID, List<Edge>> graph, UUID source) {
        Map<UUID, Integer> distances = new HashMap<>();
        Queue<UUID> queue = new LinkedList<>();
        Set<UUID> visited = new HashSet<>();

        for (UUID node : graph.keySet()) {
                distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            UUID current = queue.poll();
            visited.add(current);
            int currentDistance = distances.get(current);

            for (Edge edge : graph.getOrDefault(current, Collections.emptyList())) {
                UUID neighbor = edge.getOther(current);
                if (!visited.contains(neighbor)) {
                    int newDistance = currentDistance + 1;
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return distances;
    }

    @Override
    public List<UUID> dijkstraShortestPath(Map<UUID, List<Edge>> graph, UUID source, UUID target) {
        Map<UUID, Integer> distances = new HashMap<>();
        Map<UUID, UUID> previous = new HashMap<>();
        Queue<UUID> queue = new LinkedList<>();
        Set<UUID> visited = new HashSet<>();

        for (UUID node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            UUID current = queue.poll();
            visited.add(current);

            if (current.equals(target)) break;

            int currentDistance = distances.get(current);

            for (Edge edge : graph.getOrDefault(current, Collections.emptyList())) {
                UUID neighbor = edge.getOther(current);
                if (!visited.contains(neighbor)) {
                    int newDistance = currentDistance + 1;
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        List<UUID> path = new ArrayList<>();
        UUID step = target;
        if (previous.get(step) != null || step.equals(source)) {
            while (step != null) {
                path.add(step);
                step = previous.get(step);
            }
            Collections.reverse(path);
        }
        return path;
    }

    @Override
    public Set<Edge> kruskal(Set<UUID> nodes, List<Edge> edges) {
        Set<Edge> mst = new HashSet<>();
        Map<UUID, UUID> parent = new HashMap<>();

        for (UUID node : nodes) {
            parent.put(node, node);
        }

        // For unweighted graphs, edge order doesn't matter, but we sort for deterministic output
        edges.sort(Comparator.comparing(Edge::getMinMaxKey));

        for (Edge edge : edges) {
            UUID u = edge.getFrom();
            UUID v = edge.getTo();

            UUID rootU = find(parent, u);
            UUID rootV = find(parent, v);

            if (!rootU.equals(rootV)) {
                mst.add(edge);
                parent.put(rootU, rootV);
            }
        }
        return mst;
    }

    private UUID find(Map<UUID, UUID> parent, UUID node) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent, parent.get(node)));
        }
        return parent.get(node);
    }

    @Override
    public Set<Edge> prim(Map<UUID, List<Edge>> graph, UUID start) {
        Set<Edge> mst = new HashSet<>();
        Set<UUID> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparing(Edge::getMinMaxKey));

        visited.add(start);
        pq.addAll(graph.getOrDefault(start, Collections.emptyList()));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            UUID u = edge.getFrom();
            UUID v = edge.getTo();

            UUID next = !visited.contains(u) ? u : v;
            if (visited.contains(next)) continue;

            mst.add(edge);
            visited.add(next);

            for (Edge nextEdge : graph.getOrDefault(next, Collections.emptyList())) {
                UUID neighbor = nextEdge.getOther(next);
                if (!visited.contains(neighbor)) {
                    pq.add(nextEdge);
                }
            }
        }
        return mst;
    }
}
