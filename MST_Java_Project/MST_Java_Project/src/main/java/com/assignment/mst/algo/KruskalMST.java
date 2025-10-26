package com.assignment.mst.algo;

import com.assignment.mst.model.Edge;
import com.assignment.mst.model.Graph;

import java.util.*;

public class KruskalMST {

    public static class Result {
        public List<Edge> mstEdges;
        public Long totalCost;
        public long ops;      // simplified op counter
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

        List<Edge> edges = new ArrayList<>(g.getEdges());
        edges.sort(Comparator.comparingLong(Edge::getWeight)); ops += edges.size();

        DSU dsu = new DSU(g.getNodes());
        List<Edge> mst = new ArrayList<>();
        long total = 0;
        for (Edge e : edges) {
            ops++;
            String u = e.getFrom(), v = e.getTo();
            String ru = dsu.find(u), rv = dsu.find(v); ops += 2;
            if (!ru.equals(rv)) {
                boolean merged = dsu.union(u, v); ops++;
                if (merged) {
                    mst.add(e);
                    total += e.getWeight();
                }
            }
        }

        long t1 = System.nanoTime();
        if (mst.size() != g.vertexCount() - 1 && g.vertexCount() > 0) {
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