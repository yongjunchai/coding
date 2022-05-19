import model.Edge;
import model.Node;

import java.util.List;
import java.util.Map;

public class SingleSourceShortestPath {

    public static class Result {
        public boolean hasNegativeCycle;
        public int[][] subProblems;

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
            if (stable) {
                break;
            }
        }
        Result result = new Result();
        result.hasNegativeCycle = ! stable;
        result.subProblems = subProblems;
        return result;
    }
}
