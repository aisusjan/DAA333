package com.assignment.mst.algo;

import java.util.HashMap;
import java.util.Map;

public class DSU {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    public DSU(Iterable<String> nodes) {
        for (String n : nodes) {
            parent.put(n, n);
            rank.put(n, 0);
        }
    }

    public String find(String x) {
        String p = parent.get(x);
        if (p == null) return null;
        if (!p.equals(x)) {
            String root = find(p);
            parent.put(x, root);
            return root;
        }
        return p;
    }

    public boolean union(String a, String b) {
        String ra = find(a);
        String rb = find(b);
        if (ra == null || rb == null) return false;
        if (ra.equals(rb)) return false;
        int rka = rank.get(ra), rkb = rank.get(rb);
        if (rka < rkb) {
            parent.put(ra, rb);
        } else if (rka > rkb) {
            parent.put(rb, ra);
        } else {
            parent.put(rb, ra);
            rank.put(ra, rka + 1);
        }
        return true;
    }
}