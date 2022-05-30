import model.Edge;
import model.Path;

import java.util.ArrayList;
import java.util.List;

public class AllPairsLongestPath {

    private AllPairsShortestPathV2 allPairsShortestPathV2;

    public AllPairsShortestPathV2.Result findAllPairsLongestPath(final List<Edge> edges) {
        if (Utility.isEmpty(edges)) {
            return null;
        }
        this.allPairsShortestPathV2 = new AllPairsShortestPathV2();
        List<Edge> negatedEdges = new ArrayList<>();
        for (Edge edge: edges) {
            negatedEdges.add(Edge.create(edge.src, edge.target, (-1) * edge.length));
        }
        return this.allPairsShortestPathV2.findAllPairsShortestPath(negatedEdges);
    }

    public Path findPath(final String src, final String target) {
        if (null == allPairsShortestPathV2) {
            return null;
        }
        Path path = allPairsShortestPathV2.findPath(src, target);
        if (null == path) {
            return null;
        }
        if (path.totalLength == Integer.MAX_VALUE) {
            return path;
        }
        Path updatedPath = new Path();
        updatedPath.totalLength = (- 1) * path.totalLength;
        updatedPath.src = path.src;
        updatedPath.target = path.target;
        for (Edge edge : path.edges) {
            updatedPath.edges.add(Edge.create(edge.src, edge.target, (-1) * edge.length));
        }
        return updatedPath;
    }
}
