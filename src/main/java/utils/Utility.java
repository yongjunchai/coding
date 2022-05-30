package utils;

import model.Edge;
import model.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

    public interface FetchValue<T> {
        String getValue(T t);
    }

    public static <T> void dump(final T[][] t, FetchValue fetchValue) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        int n = t[0].length;
        if (n == 0) {
            return;
        }
        for (int i = m - 1; i >= 0; -- i) {
            for (int j = 0; j < n; ++ j) {
                System.out.print(String.format("%5s", fetchValue.getValue(t[i][j])));
            }
            System.out.println();
        }
    }

    public static void dump(final double[][] t) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        int n = t[0].length;
        if (n == 0) {
            return;
        }
        for (int i = m - 1; i >= 0; -- i) {
            for (int j = 0; j < n; ++ j) {
                System.out.print(String.format("%5.2f", t[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void dump(final double[] t) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        for (int i = 0; i < m; ++ i) {
                System.out.print(String.format("%5.2f", t[i]));
            }
            System.out.println();
    }

    public static void dump(final int[][] t, FetchValue fetchValue) {
        if (null == t) {
            return;
        }
        int m = t.length;
        if (m == 0) {
            return;
        }
        int n = t[0].length;
        if (n == 0) {
            return;
        }
        for (int i = m - 1; i >= 0; -- i) {
            for (int j = 0; j < n; ++ j) {
                System.out.print(String.format("%5s", fetchValue.getValue(t[i][j])));
            }
            System.out.println();
        }
    }

    public static void dump(final int[] t) {
        if (t == null || t.length == 0) {
            return;
        }
        for (int i = 0; i < t.length; ++ i) {
            System.out.print(String.format("%5s", Integer.toString(t[i])));
        }
        System.out.println();
    }

    public static <T> void dump(final T[] t, FetchValue fetchValue) {
        if (t == null || t.length == 0) {
            return;
        }
        for (int i = 0; i < t.length; ++ i) {
            System.out.print(String.format("%5s", fetchValue.getValue(t[i])));
        }
        System.out.println();
    }

    public static <T> boolean isEmpty(T[] t) {
        return t == null || t.length == 0;
    }
    public static  boolean isEmpty(Map t) {
        return t == null || t.isEmpty();
    }

    public static boolean isEmpty(final String str) {
        return null == str || str.length() == 0;
    }

    public static <T> boolean isEmpty(final Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }

    public static Map<String, Node> buildNodeMap(final List<Edge> edges) {
        Map<String, Node> nodeMap = new HashMap<>();
        if (isEmpty(edges)) {
            return nodeMap;
        }
        for (Edge edge: edges) {
            Node srcNode = nodeMap.get(edge.src);
            Node targetNode = nodeMap.get(edge.target);
            if (null == srcNode) {
                srcNode = new Node();
                srcNode.name = edge.src;
                nodeMap.put(edge.src, srcNode);
            }
            if (null == targetNode) {
                targetNode = new Node();
                targetNode.name = edge.target;
                nodeMap.put(edge.target, targetNode);
            }
            srcNode.outgoingEdges.put(edge.target, edge.length);
            targetNode.incomingEdges.put(edge.src, edge.length);
        }
        return nodeMap;
    }

}
