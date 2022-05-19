package model;

public class Edge {
    public String src;
    public String target;
    public int length;
    static public Edge create(final String src, final String target, final int length) {
        Edge edge = new Edge();
        edge.src = src;
        edge.target = target;
        edge.length = length;
        return edge;
    }
}
