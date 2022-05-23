import model.Edge;
import model.Node;
import model.Path;

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

        //base case, there is no internal vertexes, path length is 0
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
        for (int nodesLen = 1; nodesLen <= maxPathLen; ++ nodesLen) {
            for (int i = 0; i < nodes.length; ++ i) {
                for (int j = 0; j < nodes.length; ++ j) {
                    //case 1, last vertex is part of the path
                    //v-->k->w
                    int case1Len = Integer.MAX_VALUE;
                    if (subProblems[nodesLen - 1][i][nodesLen - 1] != Integer.MAX_VALUE) {
                        //length of k->w
                        Node k = nodes[nodesLen - 1];
                        Integer kwLen = k.outgoingEdges.get(nodes[j].name);
                        if (kwLen != null) {
                            case1Len = subProblems[nodesLen - 1][i][nodesLen - 1] + kwLen;
                        }
                    }
                    //case 2, last vertex is not part of the path
                    int case2Len = subProblems[nodesLen - 1][i][j];
                    subProblems[nodesLen][i][j] = Math.min(case1Len, case2Len);
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
        if (Utility.isEmpty(source) || Utility.isEmpty(target)) {
            return null;
        }
        if (context == null) {
            return null;
        }
        Node srcNode = context.nodeMap.get(source);
        Node targetNode = context.nodeMap.get(target);
        if (null == srcNode || null == targetNode) {
            return null;
        }

        Path path = new Path();
        if (context.subProblems[context.nodes.length][srcNode.index][targetNode.index] == Integer.MAX_VALUE) {
            path.totalLengh = Integer.MAX_VALUE;
            path.src = source;
            path.target = target;
            return path;
        }
        path.totalLengh = context.subProblems[context.nodes.length][srcNode.index][targetNode.index];
        path.src = source;
        path.target = target;
        Deque<Edge> edgeDeque = new LinkedList<>();
        Node curNode = targetNode;
        for (int nodesLen = context.nodes.length; nodesLen > 0; -- nodesLen) {
            if (curNode.name.equals(srcNode.name)) {
                break;
            }
            if (curNode.name.equals(context.nodes[nodesLen - 1])) {
                //last node is the same as current target node, skip
                continue;
            }
            //case 1, last vertex is part of the path
            //v-->k->w
            int case1Len = Integer.MAX_VALUE;
            Node k = null;
            Integer kwLen = 0;
            if (context.subProblems[nodesLen - 1][srcNode.index][nodesLen - 1] != Integer.MAX_VALUE) {
                //length of k->w
                k = context.nodes[nodesLen - 1];
                kwLen = k.outgoingEdges.get(curNode.name);
                if (kwLen != null) {
                    case1Len = context.subProblems[nodesLen -  1][srcNode.index][nodesLen - 1] + kwLen;
                }
            }
            //case 2, last vertex is not part of the path
            int case2Len = context.subProblems[nodesLen - 1][srcNode.index][curNode.index];
            if (case1Len == context.subProblems[nodesLen][srcNode.index][curNode.index]) {
                edgeDeque.addFirst(Edge.create(k.name, curNode.name, kwLen));
                curNode = k;
            }
        }
        while (edgeDeque.size() > 0) {
            path.edges.add(edgeDeque.removeFirst());
        }
        return path;
    }
}
