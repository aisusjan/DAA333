package com.assignment.mst.algo;

import com.assignment.mst.model.Edge;
import com.assignment.mst.model.Graph;

import java.util.*;

public class PrimMST {

    public static class Result {
        public List<Edge> mstEdges;
        public Long totalCost;
        public long ops;
        public long timeMs;
    }

    public static Result compute(Graph g) {
        Result r = new Result();
        long ops = 0;
        long t0 = System.nanoTime();
        if (g.vertexCount() == 0) {
            r.mstEdges = Collections.emptyList();
            r.totalCost = 0L;
            r.ops = ops;
            r.timeMs = 0;
            return r;
        }

        if (!g.isConnected()) {
            r.mstEdges = null;
            r.totalCost = null;
            r.ops = ops;
            r.timeMs = 0;
            return r;
        }

        Map<String, List<Edge>> adj = g.adjacency();
        Set<String> visited = new HashSet<>();
        List<Edge> mst = new ArrayList<>();
        long total = 0;

        String start = g.getNodes().get(0);
        visited.add(start);

        class PQItem implements Comparable<PQItem> {
            long w; String u, v;
            PQItem(long w, String u, String v) { this.w=w; this.u=u; this.v=v; }
            public int compareTo(PQItem o) { return Long.compare(this.w, o.w); }
        }

        PriorityQueue<PQItem> pq = new PriorityQueue<>();
        for (Edge e : adj.get(start)) {
            pq.add(new PQItem(e.getWeight(), e.getFrom(), e.getTo())); ops++;
        }

        while (!pq.isEmpty() && visited.size() < g.vertexCount()) {
            PQItem it = pq.poll(); ops++;
            if (visited.contains(it.v)) { ops++; continue; }
            visited.add(it.v);
            mst.add(new Edge(it.u, it.v, it.w));
            total += it.w;
            ops++;
            for (Edge e : adj.get(it.v)) {
                if (!visited.contains(e.getTo())) {
                    pq.add(new PQItem(e.getWeight(), e.getFrom(), e.getTo())); ops++;
                }
            }
        }

        long t1 = System.nanoTime();
        if (mst.size() != g.vertexCount() - 1) {
            r.mstEdges = null;
            r.totalCost = null;
        } else {
            r.mstEdges = mst;
            r.totalCost = total;
        }
        r.ops = ops;
        r.timeMs = (t1 - t0)/1_000_000;
        return r;
    }
}