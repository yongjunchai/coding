package greedy;

import model.Edge;
import model.Node;
import utils.Utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class MinimumSpanningTrees {

    private static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge left, Edge right) {
            if (left.length < right.length) {
                return  -1;
            }
            else if (left.length == right.length) {
                return  0;
            }
            return 1;
        }
    }

    public static  class Result {
        public int length;
        public List<Edge> edges;
    }

    public Result prim(final List<Edge> edgeList) {
        if (Utility.isEmpty(edgeList)) {
            return null;
        }
        Map<String, Node> nodeMap = Utility.buildNodeMap(edgeList);
        Node startNode = nodeMap.values().iterator().next();
        PriorityQueue<Edge> edgePriorityQueue = new PriorityQueue<>(new EdgeComparator());
        List<Edge> mst = new ArrayList<>();
        Set<String> nodesIncluded = new HashSet<>();
        //init, put the nodes connected to start node to heap
        for (Map.Entry<String, Integer> entry : startNode.outgoingEdges.entrySet()) {
            edgePriorityQueue.add(Edge.create(startNode.name, entry.getKey(), entry.getValue()));
        }
        nodesIncluded.add(startNode.name);
        //invariant: edges in mst span nodesIncluded
        int totalLen = 0;
        while (! edgePriorityQueue.isEmpty()) {
            Edge edge = edgePriorityQueue.poll();
            if (nodesIncluded.contains(edge.target)) {
                continue;
            }
            mst.add(edge);
            nodesIncluded.add(edge.target);
            totalLen += edge.length;
            Node targetNode = nodeMap.get(edge.target);
            if (null == targetNode) {
                throw new IllegalArgumentException("failed to find target node: " + edge.target);
            }
            for(Map.Entry<String, Integer> entry : targetNode.outgoingEdges.entrySet()) {
                if (nodesIncluded.contains(entry.getKey())) {
                    continue;
                }
                edgePriorityQueue.add(Edge.create(targetNode.name, entry.getKey(), entry.getValue()));
            }
        }
        Result result = new Result();
        result.edges = mst;
        result.length = totalLen;
        return  result;
    }
    //TODO: implement PRIM with heap delete log(n)
    //the heap store the vertexes to be added. each vertx have a field storing the index inside the heap.
    //in that way, as long as we know the vertex, we know its index inside the heap, and we can delete the vertex from
    //the heap with log(n).
//    public Result primHeapWithDelete(final List<Edge> edges) {
//        return null;
//    }
}
