package greedy;

import model.Edge;
import model.Node;
import utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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

    static class UnionFindNode {
        private int size;
        private String name;
        private UnionFindNode parent;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public UnionFindNode getParent() {
            return parent;
        }

        public void setParent(UnionFindNode parent) {
            this.parent = parent;
        }
    }

    static public class UnionFind {
        private UnionFindNode[] unionFindNodes;
        private Map<String, UnionFindNode> unionFindNodeMap;

        public UnionFind(Set<String> names) {
            if (Utility.isEmpty(names)) {
                throw new IllegalArgumentException("name set can't be empty");
            }
            unionFindNodes = new UnionFindNode[names.size()];
            unionFindNodeMap = new HashMap<>();
            int i = 0;
            for (String name : names) {
                unionFindNodes[i] = new UnionFindNode();
                unionFindNodes[i].setName(name);
                unionFindNodes[i].setSize(1);
                unionFindNodes[i].setParent(unionFindNodes[i]);
                unionFindNodeMap.put(name, unionFindNodes[i]);
                ++ i;
            }
        }

        public UnionFindNode find(final String name) {
            UnionFindNode curNode = this.unionFindNodeMap.get(name);
            if (curNode == null) {
                return null;
            }
            return findNode(curNode);
        }

        private UnionFindNode findNode(final UnionFindNode unionFindNode) {
            UnionFindNode curNode = unionFindNode;
            while (! curNode.getName().equals(curNode.getParent().getName())) {
                curNode = curNode.getParent();
            }
            return curNode;
        }

        public void union(UnionFindNode v, UnionFindNode w) {
            if (null == v || null == w) {
                return;
            }
            UnionFindNode vRoot = this.findNode(v);
            UnionFindNode wRoot = this.findNode(w);
            if (vRoot.getSize() >=  wRoot.size) {
                vRoot.setSize(vRoot.getSize() + wRoot.getSize());
                wRoot.setParent(vRoot);
            }
            else {
                wRoot.setSize(vRoot.getSize() + wRoot.getSize());
                vRoot.setParent(wRoot);
            }
        }
    }

    public Result kruskal(final Edge[] edges) {
        if (null == edges || edges.length == 0) {
            return null;
        }
        Result result = new Result();
        result.edges = new ArrayList<>();
        Arrays.sort(edges, new EdgeComparator());
        Set<String> nodeNames = new HashSet<>();
        for (Edge edge : edges) {
            nodeNames.add(edge.src);
            nodeNames.add(edge.target);
        }
        UnionFind unionFind = new UnionFind(nodeNames);
        for (Edge edge : edges) {
            UnionFindNode v = unionFind.find(edge.src);
            UnionFindNode w = unionFind.find(edge.target);
            if (v.name.equals(w.getName())) {
                continue;
            }
            unionFind.union(v, w);
            result.length += edge.length;
            result.edges.add(edge);
        }
        return result;
    }

































}
