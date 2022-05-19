package model;

import java.util.HashMap;
import java.util.Map;

public class Node {
    public String name;
    public int index;
    public Map<String, Integer> incomingEdges = new HashMap<>();
    public Map<String, Integer> outgoingEdges = new HashMap<>();

    public void addOutgoingEdge(final String target, final int edgeLen) {
        outgoingEdges.put(target, edgeLen);
    }

    public void addIncomingEdge(final String src, final int edgeLen) {
        incomingEdges.put(src, edgeLen);
    }

    static Node create(final String name) {
        Node node = new Node();
        node.name = name;
        return node;
    }
}
