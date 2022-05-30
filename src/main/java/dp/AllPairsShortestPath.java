package dp;

import model.Edge;
import model.Node;
import model.Path;
import utils.Utility;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AllPairsShortestPath {

    private Result context;

    static public class Result {
        public boolean hasNegativeLoop;
        public int[][][] subProblems;
        public Node[] nodes;
        public Map<String, Node> nodeMap;
    }

    public Result findAllPairsShortestPath(final List<Edge> edges) {
        if (Utility.isEmpty(edges)) {
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
        final int maxPathLen = nodes.length;
        //subProblem[path length][source node index][target node index]
        int[][][] subProblems = new int[maxPathLen + 1][nodes.length][nodes.length];

        //base case, there is no internal vertexes.
        //after step 0, the graph has been built up with all input edges.
        for (int i = 0; i < nodes.length; ++ i) {
            for (int j = 0; j < nodes.length; ++ j) {
                if (i == j) {
                    subProblems[0][i][j] = 0;
                    continue;
                }
                Node src = nodeMap.get(nodes[i].name);
                Integer edgeLen = src.outgoingEdges.get(nodes[j].name);
                if (edgeLen == null) {
                    subProblems[0][i][j] = Integer.MAX_VALUE;
                }
                else {
                    subProblems[0][i][j] = edgeLen;
                }
            }
        }
        //systemically solve the sub problems
        //after every new internal node added, we will rebuild all the paths.
        for (int nodesLen = 1; nodesLen <= maxPathLen; ++ nodesLen) {
            for (int i = 0; i < nodes.length; ++ i) {
                for (int j = 0; j < nodes.length; ++ j) {
                    //case 1, last vertex is not part of the path
                    int case1Len = subProblems[nodesLen - 1][i][j];
                    //case 2, last vertex is part of the path
                    int case2Len = Integer.MAX_VALUE;
                    if (subProblems[nodesLen - 1][i][nodesLen - 1] != Integer.MAX_VALUE && subProblems[nodesLen - 1][nodesLen - 1][j] != Integer.MAX_VALUE) {
                            case2Len = subProblems[nodesLen - 1][i][nodesLen - 1] + subProblems[nodesLen - 1][nodesLen - 1][j];
                    }
                    subProblems[nodesLen][i][j] = Math.min(case2Len, case1Len);
                }
            }
        }
        //find if there is negative loop
        boolean hasNegativeLoop = false;
        for (int i = 0; i < nodes.length; ++ i) {
            if (subProblems[nodes.length][i][i] < 0) {
                hasNegativeLoop = true;
                break;
            }
        }
        Result result = new Result();
        result.hasNegativeLoop = hasNegativeLoop;
        result.subProblems = subProblems;
        result.nodes = nodes;
        result.nodeMap = nodeMap;
        this.context = result;
        return result;
    }

    public Path findPath(final String source, final String target) {
        if (Utility.isEmpty(source) || Utility.isEmpty(target) || context == null) {
            return null;
        }
        Node srcNode = context.nodeMap.get(source);
        Node targetNode = context.nodeMap.get(target);
        if (null == srcNode || null == targetNode) {
            return null;
        }

        Path path = new Path();
        if (context.subProblems[context.nodes.length][srcNode.index][targetNode.index] == Integer.MAX_VALUE) {
            path.totalLength = Integer.MAX_VALUE;
            path.src = source;
            path.target = target;
            return path;
        }
        path.totalLength = context.subProblems[context.nodes.length][srcNode.index][targetNode.index];
        path.src = source;
        path.target = target;
        Deque<Edge> edgeDeque = new LinkedList<>();
        Node curNode = targetNode;
        while (! curNode.name.equals(srcNode.name)) {

            for(int nodeLen = context.nodes.length; nodeLen >= 0; -- nodeLen) {
                if (nodeLen == 0) {
                    edgeDeque.addFirst(Edge.create(srcNode.name, curNode.name, context.subProblems[0][srcNode.index][curNode.index]));
                    curNode = srcNode;
                    break;
                }
                if (context.subProblems[nodeLen][srcNode.index][curNode.index] == context.subProblems[nodeLen - 1][srcNode.index][curNode.index]) {
                    continue;
                }
                Integer edgeLen = context.nodes[nodeLen - 1].outgoingEdges.get(curNode.name);
                if (null == edgeLen) {
                    return null;
                }
                edgeDeque.addFirst(Edge.create(context.nodes[nodeLen - 1].name, curNode.name, edgeLen));
                curNode = context.nodes[nodeLen - 1];
                break;
            }
        }
        while (edgeDeque.size() > 0) {
            path.edges.add(edgeDeque.removeFirst());
        }
        return path;
    }
}
