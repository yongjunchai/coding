package dp;

import model.Edge;
import model.Node;
import model.Path;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SingleSourceShortestPath {

    public static class Result {
        public boolean hasNegativeCycle;
        public int[][] subProblems;
        public Node[] nodes;
        public Map<String, Path> pathMap;
    }

    public Result findSingleSourceShortestPath(final String source, final List<Edge> edges) {
        if (Utility.isEmpty(source) || Utility.isEmpty(edges)) {
            return null;
        }
        Map<String, Node> nodeMap = Utility.buildNodeMap(edges);
        Node[] nodes = new Node[nodeMap.size()];
        int nodeIndex = 0;
        for (Map.Entry<String, Node> entry : nodeMap.entrySet()) {
            nodes[nodeIndex] = entry.getValue();
            nodes[nodeIndex].index = nodeIndex;
            ++ nodeIndex;
        }

        int[][] subProblems = new int[nodes.length + 1][nodes.length];
        //base case, path step is 0
        for (int i = 0; i < nodes.length; ++ i) {
            if (nodes[i].name.equals(source)) {
                subProblems[0][i] = 0;
            }
            else {
                subProblems[0][i] = Integer.MAX_VALUE;
            }
        }
        //systemically solve the sub problems
        boolean stable = true;
        for (int step = 1; step <= nodes.length; ++ step) {
            stable = true;
            for (int i = 0; i < nodes.length; ++ i) {
                if (nodes[i].name.equals(source)) {
                    subProblems[step][i] = 0;
                    continue;
                }
                //for all incoming edges, find the shortest one to current node
                int minPath = Integer.MAX_VALUE;
                for (Map.Entry<String, Integer> entry : nodes[i].incomingEdges.entrySet()) {
                    Node node = nodeMap.get(entry.getKey());
                    int pathLen = 0;
                    if (subProblems[step - 1][node.index] == Integer.MAX_VALUE) {
                        pathLen = Integer.MAX_VALUE;
                    }
                    else {
                        pathLen = subProblems[step - 1][node.index] + entry.getValue();

                    }
                    if (minPath > pathLen) {
                        minPath = pathLen;
                    }
                }
                if (minPath < subProblems[step - 1][i]) {
                    subProblems[step][i] = minPath;
                    stable = false;
                }
                else {
                    subProblems[step][i] = subProblems[step - 1][i];
                }
            }
        }
        Result result = new Result();
        result.hasNegativeCycle = ! stable;
        result.subProblems = subProblems;
        result.nodes = nodes;
        result.pathMap = reconstructPath(source, nodes, nodeMap, subProblems);
        return result;
    }

    private Map<String, Path> reconstructPath(final String src, final Node[] nodes, final Map<String, Node> nodeMap, final int[][] subProblems)
    {
        Map<String, Path> pathMap = new HashMap<>();
        for (int i = 0; i < nodes.length; ++ i) {
            if (nodes[i].name.equals(src)) {
                continue;
            }
            Path path = new Path();
            path.src = src;
            path.target = nodes[i].name;
            path.totalLength = subProblems[nodes.length][i];
            if (path.totalLength == Integer.MAX_VALUE) {
                pathMap.put(path.target, path);
                continue;
            }
            Deque<Edge> edgeDeque = new LinkedList<>();
            int curNodeIndex = i;
            for (int step = nodes.length; step > 0; -- step) {
                if (subProblems[step][curNodeIndex] == subProblems[step - 1][curNodeIndex]) {
                    continue;
                }
                for (Map.Entry<String, Integer> entry : nodes[curNodeIndex].incomingEdges.entrySet()) {
                    Node incomingNode = nodeMap.get(entry.getKey());
                    int pathLen = 0;
                    if (subProblems[step - 1][incomingNode.index] == Integer.MAX_VALUE) {
                        continue;
                    }
                    else {
                        pathLen = subProblems[step - 1][incomingNode.index] + entry.getValue();
                    }
                    if (pathLen == subProblems[step][curNodeIndex]) {
                        Edge edge = new Edge();
                        edge.src = incomingNode.name;
                        edge.target = nodes[curNodeIndex].name;
                        edge.length = entry.getValue();
                        edgeDeque.addFirst(edge);
                        curNodeIndex = incomingNode.index;
                        break;
                    }
                }
            }
            while (edgeDeque.size() > 0) {
                Edge edge = edgeDeque.removeFirst();
                path.edges.add(edge);
            }
            pathMap.put(nodes[i].name, path);
        }
        return pathMap;
    }

}
