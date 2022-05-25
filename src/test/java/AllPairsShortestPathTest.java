import model.Edge;
import model.Node;
import model.Path;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AllPairsShortestPathTest {

    static private class Entry{
        public List<Edge> edges = new ArrayList<>();
        public List<Path> paths = new ArrayList<>();
        public boolean hasNegativeCycle;
    }

    private Entry getEntry1() {
        Entry entry = new Entry();
        entry.edges.add(Edge.create("s", "v", 4));
        entry.edges.add(Edge.create("s", "u", 2));
        entry.edges.add(Edge.create("u", "v", -1));
        entry.edges.add(Edge.create("u", "w", 2));
        entry.edges.add(Edge.create("w", "t", 2));
        entry.edges.add(Edge.create("v", "t", 4));
        entry.edges.add(Edge.create("x", "y", 4));
        entry.edges.add(Edge.create("t", "y", 4));
        entry.edges.add(Edge.create("x", "t", 4));
        entry.edges.add(Edge.create("v", "y", 4));
        entry.edges.add(Edge.create("t", "y", 2));
        Path path = new Path();
        path.src = "s";
        path.target = "t";
        path.totalLengh = 5;
        path.edges.add(Edge.create("s", "u", 2));
        path.edges.add(Edge.create("u", "v", -1));
        path.edges.add(Edge.create("v", "t", 4));
        entry.paths.add(path);
        entry.hasNegativeCycle = false;
        return entry;
    }

    private Entry getEntryPathEmptyStart() {
        Entry entry = new Entry();
        entry.edges.add(Edge.create("s", "v", 4));
        entry.edges.add(Edge.create("s", "u", 2));
        entry.edges.add(Edge.create("u", "v", -1));
        entry.edges.add(Edge.create("u", "w", 2));
        entry.edges.add(Edge.create("w", "t", 2));
        entry.edges.add(Edge.create("v", "t", 4));
        entry.edges.add(Edge.create("x", "y", 4));
        entry.edges.add(Edge.create("t", "y", 4));
        entry.edges.add(Edge.create("x", "t", 4));
        entry.edges.add(Edge.create("v", "y", 4));
        entry.edges.add(Edge.create("t", "y", 2));
        entry.edges.add(Edge.create("p", "w", 2));
        Path path = new Path();
        path.target = "t";
        path.totalLengh = 5;
        path.edges.add(Edge.create("s", "u", 2));
        path.edges.add(Edge.create("u", "v", -1));
        path.edges.add(Edge.create("v", "t", 4));
        entry.paths.add(path);
        entry.hasNegativeCycle = false;
        return entry;
    }

    private Entry getEntryNoPath() {
        Entry entry = new Entry();
        entry.edges.add(Edge.create("s", "v", 4));
        entry.edges.add(Edge.create("s", "u", 2));
        entry.edges.add(Edge.create("u", "v", -1));
        entry.edges.add(Edge.create("u", "w", 2));
        entry.edges.add(Edge.create("w", "t", 2));
        entry.edges.add(Edge.create("v", "t", 4));
        entry.edges.add(Edge.create("x", "y", 4));
        entry.edges.add(Edge.create("t", "y", 4));
        entry.edges.add(Edge.create("x", "t", 4));
        entry.edges.add(Edge.create("v", "y", 4));
        entry.edges.add(Edge.create("t", "y", 2));
        entry.edges.add(Edge.create("p", "w", 2));
        Path path = new Path();
        path.src = "s";
        path.target = "p";
        path.totalLengh = Integer.MAX_VALUE;
        entry.paths.add(path);
        entry.hasNegativeCycle = false;
        return entry;
    }


    private Entry getEntry2() {
        Entry entry = new Entry();
        entry.edges.add(Edge.create("s", "v", 4));
        entry.edges.add(Edge.create("s", "u", 2));
        entry.edges.add(Edge.create("u", "v", -1));
        entry.edges.add(Edge.create("u", "w", 2));
        entry.edges.add(Edge.create("w", "t", 2));
        entry.edges.add(Edge.create("v", "t", 4));
        entry.edges.add(Edge.create("x", "y", 4));
        entry.edges.add(Edge.create("t", "y", 4));
        entry.edges.add(Edge.create("v", "y", 4));
        entry.edges.add(Edge.create("t", "y", 2));
        entry.edges.add(Edge.create("v", "w", 2));
        entry.edges.add(Edge.create("w", "u", -5));
        entry.hasNegativeCycle = true;
        return entry;
    }

    private Entry getEntryEmpty() {
        Entry entry = new Entry();
        return entry;
    }


    private List<Entry> getTestData() {
        List<Entry> entries = new ArrayList<>();
        entries.add(getEntry1());
        entries.add(getEntry2());
        entries.add(getEntryEmpty());
        entries.add(getEntryPathEmptyStart());
        entries.add(getEntryNoPath());
        return entries;
    }

    @Test
    public void findAllPairsShortestPathTest() {
        List<Entry> entries = getTestData();
        Utility.FetchValue<Integer> fetchValue = a -> {
            if (a == Integer.MAX_VALUE) {
                return "-";
            }
            return Integer.toString(a);
        };
        for (Entry entry : entries) {
            AllPairsShortestPath allPairsShortestPath = new AllPairsShortestPath();
            AllPairsShortestPath.Result result = allPairsShortestPath.findAllPairsShortestPath(entry.edges);
            if (Utility.isEmpty(entry.edges)) {
                Assert.assertTrue(null == result);
                continue;
            }
            if (entry.hasNegativeCycle) {
                Assert.assertTrue(result.hasNegativeLoop);
                continue;
            }

            Utility.dump(result.subProblems[result.nodes.length], fetchValue);
            for (Path path : entry.paths) {
                Path pathFind = allPairsShortestPath.findPath(path.src, path.target);
                if (Utility.isEmpty(path.src) || Utility.isEmpty(path.target)) {
                    Assert.assertTrue(null == pathFind);
                    continue;
                }
                Assert.assertTrue(path.totalLengh == pathFind.totalLengh);
                Assert.assertTrue(path.edges.size() == pathFind.edges.size());
                System.out.printf("%s -- > %s, %d\n", pathFind.src, pathFind.target, pathFind.totalLengh);
                int edgeLen = path.edges.size();
                for (int i = 0; i < edgeLen; ++ i) {
                    Assert.assertTrue(path.edges.get(i).src.equals(pathFind.edges.get(i).src));
                    Assert.assertTrue(path.edges.get(i).target.equals(pathFind.edges.get(i).target));
                    Assert.assertTrue(path.edges.get(i).length == pathFind.edges.get(i).length);
                    System.out.printf("%s -> %s, %d \n", pathFind.edges.get(i).src, pathFind.edges.get(i).target, pathFind.edges.get(i).length);
                }
                System.out.println();
            }
        }
    }

    @Test
    public void dumpNegativeCycleGraph() {
        List<Entry> entries = getTestData();
        Utility.FetchValue<Integer> fetchValue = a -> {
            if (a == Integer.MAX_VALUE) {
                return "-";
            }
            else {
                return Integer.toString(a);
            }
        };
        Utility.FetchValue<Node> nodeFetchValue = node -> node.name;
        for (Entry entry : entries) {
            AllPairsShortestPath allPairsShortestPath = new AllPairsShortestPath();
            AllPairsShortestPath.Result result = allPairsShortestPath.findAllPairsShortestPath(entry.edges);
            if (Utility.isEmpty(entry.edges)) {
                Assert.assertTrue(null == result);
                continue;
            }

            if (! entry.hasNegativeCycle) {
                continue;
            }

            for (int nodePathLen = 0; nodePathLen <= result.nodes.length; ++ nodePathLen) {
                System.out.printf("node path length = %d\n", nodePathLen);
                Utility.dump(result.nodes, nodeFetchValue);
                Utility.dump(result.subProblems[nodePathLen], fetchValue);
                System.out.println();
            }

            System.out.println("Dump paths");
            for (int i = 0; i < result.nodes.length; ++ i) {
                for (int j = 0; j < result.nodes.length; ++ j) {
                    if (result.subProblems[result.nodes.length][i][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    System.out.printf("%s --> %s, %d \n", result.nodes[i].name, result.nodes[j].name, result.subProblems[result.nodes.length][i][j]);
                    Path path = allPairsShortestPath.findPath(result.nodes[i].name, result.nodes[j].name);
                    if (null == path) {
                        continue;
                    }
                    for (Edge edge : path.edges) {
                        System.out.printf("%s -> %s, %d\n", edge.src, edge.target, edge.length);
                    }
                    System.out.println("------------------------");
                }
            }

        }
    }
    
}
