package dp;

import model.Edge;
import model.Node;
import model.Path;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SingleSourceShortestPathV2 {
    public static class Result {
        public boolean hasNegativeCycle;
        public Note[][] subProblems;
        public Node[] nodes;
        public Map<String, Node> nodeMap;
    }

    public static class Note {
        public int value;
        public int previousNodeIndex;
    }

    public Result findSingleSourceShortestPath(final String source, final List<Edge> edges) {
        if (Utility.isEmpty(source) || Utility.isEmpty(edges)) {
            return null;
        }
        Map<String, Node> nodeMap = Utility.buildNodeMap(edges);
        Node[] nodes = new Node[nodeMap.size()];
        Note[][] subProblems = new Note[nodes.length + 1][nodes.length];

        //update nodes index
        int nodeIndex = 0;
        for (Node node : nodeMap.values()) {
            nodes[nodeIndex] = node;
            node.index = nodeIndex;
            ++ nodeIndex;
        }

        //base case, step is 0
        for(int i = 0; i < nodes.length; ++ i) {
            subProblems[0][i] = new Note();
            if (nodes[i].name.equals(source)) {
                subProblems[0][i].value = 0;
            }
            else {
                subProblems[0][i].value = Integer.MAX_VALUE;
            }
        }
        boolean stable = true;
        //systemically solve sub problems
        for (int step = 1; step <= nodes.length; ++ step) {
            stable = true;
            for (int i = 0; i < nodes.length; ++ i) {
                Note minPath = new Note();
                minPath.value = Integer.MAX_VALUE;
                for (Map.Entry<String, Integer> entry : nodes[i].incomingEdges.entrySet()) {
                    Node incomingNode = nodeMap.get(entry.getKey());
                    if (subProblems[step - 1][incomingNode.index].value == Integer.MAX_VALUE) {
                        continue;
                    }
                    else {
                        int pathLen = subProblems[step - 1][incomingNode.index].value + entry.getValue();
                        if (pathLen < minPath.value) {
                            minPath.value = pathLen;
                            minPath.previousNodeIndex = incomingNode.index;
                        }
                    }
                }
                if (minPath.value < subProblems[step - 1][i].value) {
                    subProblems[step][i] = minPath;
                    stable = false;
                }
                else {
                    subProblems[step][i] = new Note();
                    subProblems[step][i].value = subProblems[step - 1][i].value;
                }
            }
        }
        Result result = new Result();
        result.hasNegativeCycle = ! stable;
        result.subProblems = subProblems;
        result.nodes = nodes;
        result.nodeMap = nodeMap;
        return result;
    }

    public Path reconstructPath(final String src, final String target, final Node[] nodes, final Map<String, Node> nodeMap, final Note[][] subProblems) {
        if (Utility.isEmpty(src) || Utility.isEmpty(target) || Utility.isEmpty(nodes) || Utility.isEmpty(nodeMap) || Utility.isEmpty(subProblems)) {
            return null;
        }
        Node node = nodeMap.get(target);
        if (subProblems[nodes.length][node.index].value == Integer.MAX_VALUE) {
            return null;
        }
        Deque<Edge> edgeDeque = new LinkedList<>();
        Node curNode = node;
        for (int step = nodes.length; step > 0; -- step) {
            if (subProblems[step][curNode.index].value == subProblems[step - 1][curNode.index].value) {
                continue;
            }
            Integer edgeLen = subProblems[step][curNode.index].value - subProblems[step - 1][subProblems[step][curNode.index].previousNodeIndex].value;
            edgeDeque.addFirst(Edge.create(nodes[subProblems[step][curNode.index].previousNodeIndex].name, curNode.name, edgeLen));
            curNode = nodes[subProblems[step][curNode.index].previousNodeIndex];
        }
        Path path = new Path();
        path.src = src;
        path.target = target;
        path.totalLength = subProblems[nodes.length][node.index].value;
        while (edgeDeque.size() > 0) {
            path.edges.add(edgeDeque.removeFirst());
        }
        return path;
    }
}
