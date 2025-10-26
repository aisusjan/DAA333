package com.assignment.mst.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Graph {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    @JsonCreator
    public Graph(@JsonProperty("id") int id,
                 @JsonProperty("nodes") List<String> nodes,
                 @JsonProperty("edges") List<Edge> edges) {
        this.id = id;
        this.nodes = (nodes == null) ? new ArrayList<>() : new ArrayList<>(nodes);
        this.edges = (edges == null) ? new ArrayList<>() : new ArrayList<>(edges);
    }

    public int getId() { return id; }
    public List<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }

    public int vertexCount() { return nodes.size(); }
    public int edgeCount() { return edges.size(); }

    public Map<String, List<Edge>> adjacency() {
        Map<String, List<Edge>> adj = new HashMap<>();
        for (String n : nodes) adj.put(n, new ArrayList<>());
        for (Edge e : edges) {
            if (!adj.containsKey(e.getFrom())) adj.put(e.getFrom(), new ArrayList<>());
            if (!adj.containsKey(e.getTo())) adj.put(e.getTo(), new ArrayList<>());
            adj.get(e.getFrom()).add(e);
            adj.get(e.getTo()).add(new Edge(e.getTo(), e.getFrom(), e.getWeight()));
        }
        return adj;
    }

    public boolean isConnected() {
        if (nodes.isEmpty()) return true;
        Map<String, List<Edge>> adj = adjacency();
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(nodes.get(0));
        while (!stack.isEmpty()) {
            String u = stack.pop();
            if (!visited.add(u)) continue;
            for (Edge e : adj.get(u)) {
                if (!visited.contains(e.getTo())) stack.push(e.getTo());
            }
        }
        return visited.size() == nodes.size();
    }
}