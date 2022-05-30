package dp;

import model.Edge;
import model.Node;
import model.Path;
import utils.Utility;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AllPairsShortestPathV2 {
    static public class Result {
        public Note[][][] subProblems;
        Map<String, Node> nodeMap;
        Node[] nodes;
        boolean hasNegativeCycle;
    }

    static public class Note {
        public int value;
        public int incomingNodeIndex;
    }


    private Result context;

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
        Note[][][] subProblems = new Note[nodes.length + 1][nodes.length][nodes.length];
        //base case, there is no internal nodes. after step 0, the graph has been built up with the input edges
        for (int i = 0; i < nodes.length; ++ i) {
            for (int j = 0; j < nodes.length; ++ j) {
                subProblems[0][i][j] = new Note();
                if (i == j) {
                    subProblems[0][i][j].value = 0;
                }
                Node srcNode = nodes[i];
                Integer edgeLen = srcNode.outgoingEdges.get(nodes[j].name);
                if (edgeLen == null) {
                    subProblems[0][i][j].value = Integer.MAX_VALUE;
                }
                else {
                    subProblems[0][i][j].value = edgeLen;
                    subProblems[0][i][j].incomingNodeIndex = srcNode.index;
                }
            }
        }
        //systemically solve the sub problems
        for (int internalNodesLen = 1; internalNodesLen <= nodes.length; ++ internalNodesLen) {
            for (int srcNodeIndex = 0; srcNodeIndex < nodes.length; ++ srcNodeIndex) {
                for (int targetNodeIndex = 0; targetNodeIndex < nodes.length; ++ targetNodeIndex) {
                    subProblems[internalNodesLen][srcNodeIndex][targetNodeIndex] = new Note();
                    //case 1, the last internal node is not used;
                    int case1Len = subProblems[internalNodesLen - 1][srcNodeIndex][targetNodeIndex].value;

                    //case 2, the last internal node is used
                    int case2Len = Integer.MAX_VALUE;
                    if (subProblems[internalNodesLen - 1][srcNodeIndex][nodes[internalNodesLen - 1].index].value != Integer.MAX_VALUE &&
                        subProblems[internalNodesLen - 1][nodes[internalNodesLen - 1].index][targetNodeIndex].value != Integer.MAX_VALUE
                    ) {
                        case2Len = subProblems[internalNodesLen - 1][srcNodeIndex][nodes[internalNodesLen - 1].index].value + subProblems[internalNodesLen - 1][nodes[internalNodesLen - 1].index][targetNodeIndex].value;
                    }
                    if (case1Len < case2Len) {
                        subProblems[internalNodesLen][srcNodeIndex][targetNodeIndex].value = case1Len;
                        subProblems[internalNodesLen][srcNodeIndex][targetNodeIndex].incomingNodeIndex = subProblems[internalNodesLen - 1][srcNodeIndex][targetNodeIndex].incomingNodeIndex;
                    }
                    else {
                        subProblems[internalNodesLen][srcNodeIndex][targetNodeIndex].value = case2Len;
                        subProblems[internalNodesLen][srcNodeIndex][targetNodeIndex].incomingNodeIndex = internalNodesLen - 1;

                    }
                }
            }
        }
        boolean hasNegativeCycle = false;
        for (int i = 0; i < nodes.length; ++ i) {
            if (subProblems[nodes.length][i][i].value < 0) {
                hasNegativeCycle = true;
                break;
            }
        }

        Result result = new Result();
        result.subProblems = subProblems;
        result.nodeMap = nodeMap;
        result.nodes = nodes;
        result.hasNegativeCycle = hasNegativeCycle;

        this.context = result;
        return result;
    }

    public Path findPath(final String src, final String target) {
        if (Utility.isEmpty(src) || Utility.isEmpty(target) || this.context == null) {
            return null;
        }
        Node srcNode = context.nodeMap.get(src);
        Node targetNode = context.nodeMap.get(target);
        if (null == srcNode || null == targetNode) {
            return null;
        }
        Path path = new Path();
        path.src = src;
        path.target = target;
        if (context.subProblems[context.nodes.length][srcNode.index][targetNode.index].value == Integer.MAX_VALUE) {
            path.totalLength = Integer.MAX_VALUE;
            return path;
        }
        path.totalLength = context.subProblems[context.nodes.length][srcNode.index][targetNode.index].value;
        Node curNode = targetNode;
        Deque<Edge> edgeDeque = new LinkedList<>();
        while (curNode.index != srcNode.index) {
            Node incomingNode = context.nodes[context.subProblems[context.nodes.length][srcNode.index][curNode.index].incomingNodeIndex];
            Integer edgeLen = Integer.MAX_VALUE;
            if (srcNode.index == incomingNode.index) {
                //this is the last edge
                edgeLen = context.subProblems[context.nodes.length][srcNode.index][curNode.index].value;
            }
            else {
                edgeLen = context.subProblems[context.nodes.length][srcNode.index][curNode.index].value -
                        context.subProblems[context.nodes.length][srcNode.index][incomingNode.index].value;
            }
            edgeDeque.addFirst(Edge.create(incomingNode.name, curNode.name, edgeLen));
            curNode = incomingNode;
        }
        while (edgeDeque.size() > 0) {
            path.edges.add(edgeDeque.removeFirst());
        }
        return path;
    }
}
