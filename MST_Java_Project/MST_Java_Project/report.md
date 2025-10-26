
# MST Assignment — Report

This report summarizes the implementation and experimental comparison of **Prim's** and **Kruskal's** Minimum Spanning Tree algorithms.

## Project
- Language: Java (Maven project)
- Files: `src/main/java/...` contains implementations of Graph, Edge, Prim, Kruskal, DSU, and `Main`.
- Input format: JSON (see `src/main/resources/input_example.json`).
- Output: `output.json` (detailed per-graph) and `comparison.csv` (summary).

## Experimental setup (benchmarks)
I generated random connected graphs with varying number of vertices `n` and edge density `d`. For each (n,d) I measured execution time (ms) and a rough operation count for both algorithms. Benchmarks were executed using a Python implementation for speed of prototyping; the Java versions are functionally equivalent and included in the project.

### Results (time)

### Density 0.1

![](plot_time_density_10.png)


### Density 0.3

![](plot_time_density_30.png)


### Density 0.6

![](plot_time_density_60.png)


## Results (operations)

### Density 0.1

![](plot_ops_density_10.png)


### Density 0.3

![](plot_ops_density_30.png)


### Density 0.6

![](plot_ops_density_60.png)


## Discussion and conclusions

- **Kruskal** tends to perform well on sparse graphs because sorting edges and DSU is efficient when number of edges is low.
- **Prim** (with a binary heap) can be faster on denser graphs because it grows the tree incrementally using adjacency lists.
- Operation counts are approximate and depend on implementation details (heap operations, comparator calls, recursion in find()).
- The Java implementation included is ready to run and will produce `output.json` and `comparison.csv` when provided with `input_example.json`.

## How to run

1. Build the project with Maven:
```
mvn clean package
```
2. Run:
```
mvn exec:java -Dexec.mainClass="com.assignment.mst.Main" -Dexec.args="--input src/main/resources/input_example.json --output output.json --csv comparison.csv"
```
3. Output files will appear in project root: `output.json`, `comparison.csv`.

