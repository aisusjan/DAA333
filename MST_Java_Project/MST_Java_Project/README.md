
# MST Java Project

This Maven project implements Prim's and Kruskal's Minimum Spanning Tree algorithms in Java.

## Requirements
- Java 17+
- Maven 3+

## Build
```
mvn clean package
```

## Run
By default the program reads `src/main/resources/input_example.json` and writes `output.json` and `comparison.csv`:
```
mvn exec:java -Dexec.mainClass="com.assignment.mst.Main" -Dexec.args="--input src/main/resources/input_example.json --output output.json --csv comparison.csv"
```

## Tests
```
mvn test
```

## Notes
- The report `report.md` contains experimental graphs (PNG) comparing execution time and operation counts for different graph sizes and densities.
