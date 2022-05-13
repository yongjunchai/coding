import java.util.Deque;
import java.util.LinkedList;

public class WIS {
    public static class Node {
        public String name;
        public int weight;

        static public Node create(final String name, final int weight) {
            Node node = new Node();
            node.name = name;
            node.weight = weight;
            return node;
        }
    }

    public static class Result {
        public int totalWeight;
        public Node[] nodes;
    }

    public Result findMaxWIS(final Node[] nodes) {
        if (null == nodes || nodes.length == 0) {
            return null;
        }
        int[] subProblems = new int[nodes.length + 1];
        //base case
        subProblems[0] = 0;
        subProblems[1] = nodes[0].weight;
        for (int i = 2; i <= nodes.length; ++ i) {
            int case1 = subProblems[i - 2] + nodes[i - 1].weight;
            int case2 = subProblems[i - 1];
            subProblems[i] = Math.max(case1, case2);
        }
        //reconstruct solution
        Deque<Node> deque = new LinkedList<>();
        int i = nodes.length;
        //go through non-base cases
         while (i > 1){
            int case1 = subProblems[i - 2] + nodes[i - 1].weight;
            int case2 = subProblems[i - 1];
            if(case1 == subProblems[i]) {
                deque.addFirst(nodes[i - 1]);
                i -= 2;
                continue;
            }
            i -= 1;
         }
         //base case, if the second last was not selected, add the first one
         if (! deque.getFirst().name.equals(nodes[1].name)) {
             deque.addFirst(nodes[0]);
         }
         Node[] nodeWIS = new Node[deque.size()];
         for (int j = 0; j < nodeWIS.length; ++ j) {
             nodeWIS[j] = deque.removeFirst();
         }
         Result result = new Result();
         result.nodes = nodeWIS;
         result.totalWeight = subProblems[nodes.length];
         return result;
    }
}
