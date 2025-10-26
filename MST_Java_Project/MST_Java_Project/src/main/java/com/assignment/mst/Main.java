package com.assignment.mst;

import com.assignment.mst.algo.KruskalMST;
import com.assignment.mst.algo.PrimMST;
import com.assignment.mst.model.Graph;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = "src/main/resources/input_example.json";
        String output = "output.json";
        String csv = "comparison.csv";
        for (int i=0;i<args.length;i++) {
            if ("--input".equals(args[i]) && i+1<args.length) input = args[++i];
            if ("--output".equals(args[i]) && i+1<args.length) output = args[++i];
            if ("--csv".equals(args[i]) && i+1<args.length) csv = args[++i];
        }
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(new File(input));
        List<Graph> graphs = new ArrayList<>();
        if (root.has("graphs")) {
            for (JsonNode gnode : root.get("graphs")) {
                graphs.add(om.treeToValue(gnode, Graph.class));
            }
        } else {
            graphs.add(om.treeToValue(root, Graph.class));
        }

        var results = om.createArrayNode();
        var csvLines = new ArrayList<String>();
        csvLines.add("graph_id,vertices,edges,prim_cost,kruskal_cost,prim_time_ms,kruskal_time_ms,prim_ops,kruskal_ops");

        int idx=1;
        for (Graph g : graphs) {
            PrimMST.Result pr = PrimMST.compute(g);
            KruskalMST.Result kr = KruskalMST.compute(g);
            var en = om.createObjectNode();
            en.put("graph_id", idx);
            var stats = om.createObjectNode();
            stats.put("vertices", g.vertexCount());
            stats.put("edges", g.edgeCount());
            en.set("input_stats", stats);

            var prim = om.createObjectNode();
            if (pr.totalCost==null) prim.putNull("total_cost"); else prim.put("total_cost", pr.totalCost);
            prim.put("execution_time_ms", pr.timeMs);
            prim.put("operations_count", pr.ops);
            prim.set("mst_edges", om.valueToTree(pr.mstEdges));
            en.set("prim", prim);

            var krn = om.createObjectNode();
            if (kr.totalCost==null) krn.putNull("total_cost"); else krn.put("total_cost", kr.totalCost);
            krn.put("execution_time_ms", kr.timeMs);
            krn.put("operations_count", kr.ops);
            krn.set("mst_edges", om.valueToTree(kr.mstEdges));
            en.set("kruskal", krn);

            results.add(en);
            csvLines.add(String.format("%d,%d,%d,%s,%s,%d,%d,%d,%d", idx, g.vertexCount(), g.edgeCount(),
                    pr.totalCost==null?"":pr.totalCost, kr.totalCost==null?"":kr.totalCost, pr.timeMs, kr.timeMs, pr.ops, kr.ops));
            idx++;
        }

        var rootOut = om.createObjectNode();
        rootOut.set("results", results);
        om.writerWithDefaultPrettyPrinter().writeValue(new File(output), rootOut);
        try (PrintWriter pw = new PrintWriter(new File(csv))) {
            for (String line : csvLines) pw.println(line);
        }
        System.out.println("Wrote " + output + " and " + csv);
    }
}