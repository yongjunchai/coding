import model.Edge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class GraphNegativeCycleDetectionShortestPath {

    static class Result {
        public boolean hasNegativeCycle;
        public AllPairsShortestPathV2.Note[][][] subProblems;
    }

    public Result findAllPairsShortestPath(final List<Edge> edges) {
        // add a new source vertex to all nodes, with length as 0.
        // run single source shortest algorithm to detect negative cycle.
        // if there is no negative cycle, run the all pairs shortest path algorithm

        //find out the vertex names
        Set<String> vertexes = new HashSet<>();
        for (Edge edge: edges) {
            vertexes.add(edge.src);
            vertexes.add(edge.target);
        }
        String mockSourceVertex = UUID.randomUUID().toString();
        List<Edge> newEdges = new ArrayList<>();
        newEdges.addAll(edges);
        for (String vertex : vertexes) {
            newEdges.add(Edge.create(mockSourceVertex, vertex, 0));
        }
        SingleSourceShortestPathV2 singleSourceShortestPathV2 = new SingleSourceShortestPathV2();
        SingleSourceShortestPathV2.Result singleSourceShortestPathV2Result = singleSourceShortestPathV2.findSingleSourceShortestPath(mockSourceVertex, newEdges);
        if (singleSourceShortestPathV2Result == null) {
            return null;
        }
        if (singleSourceShortestPathV2Result.hasNegativeCycle) {
            Result result = new Result();
            result.hasNegativeCycle = true;
            return result;
        }
        AllPairsShortestPathV2 allPairsShortestPathV2 = new AllPairsShortestPathV2();
        AllPairsShortestPathV2.Result allPairsShortestPathResult = allPairsShortestPathV2.findAllPairsShortestPath(edges);
        Result result = new Result();
        result.hasNegativeCycle = false;
        result.subProblems = allPairsShortestPathResult.subProblems;
        return result;
    }
}
