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

    /**
     * Input: an un-directed connected graph G=(V, E)
     * Output: edges of a MST
     *
     * How it works:
     *  set up two vertex sets: X, V-X
     *  vertexes in X are connected by selected edges,
     *  every iteration select the minimum edges that connect the vertex in X and V-X
     *
     * Proof:
     *  1. edges selected by Prim is a spanning tree
     *      since the edge selected is crossing X and V-X, it is type-f addition, it reduces the number of connected components by 1.
     *      after n - 1 edges added, the connected components reduced to 1.
     *  2. edges selected by Prim satisfy MBP
     *      to any v-w edge selected in Prim, v belong to X, w belong to V-X. to any path P of v~w, it needs cross the component boundary.
     *      to any path that crossing X, V-X and connect v~w, it will be longer than v-w. v-w satisfy the MBP.
     *  3. edges selected by Prim is MST
     *      Spanning tree with edges satisfy MBP  is MST.
     *
     * */

    /**
     * implement above idea with a heap of edges
     * */
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
        //invariant: edges in mst connect vertexes in nodesIncluded
        int totalLen = 0;
        while (! edgePriorityQueue.isEmpty()) {
            Edge edge = edgePriorityQueue.poll();
            //to edge addition we only allow TYPE-F
            //if new vertex already included in the mst connected component, skip it
            if (nodesIncluded.contains(edge.target)) {
                continue;
            }
            //TYPE-F, reduce the number of connected component by one
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

    /**
     * Input: an un-directed connected graph G=(V, E)
     * Output: edges of a MST
     *
     * How it works:
     *  there is no edges added at the beginning. n vertexes are in n connected components.
     *  processing edges in ascending order: if vertexes of the edge, belonging to same connected component, skip this edge.
     *  if the vertexes of the edge, belonging to different component, add the edge and fuse the two connected component.

     * Proof:
     *  1. edges selected by kruskal is spanning tree.
     *      since every edge addition is a type-fuse, it will reduce the number of connected components by 1. after n-1 edges added, there will be only one connected component.
     *  since there is no type-C edge, it is a spanning tree.
     *  2. edges selected by kruskal is MBP
     *      To any edge v-w selected by kruskal, it will connect the two connected components that v and w belonging to. Since any other edges that may connect the two connected
     *      component will be longer than v-w, v-w satisfy the MBP.
     *  3. Spanning tree with all edges satisfy the MBP is MST.
     * **/
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
