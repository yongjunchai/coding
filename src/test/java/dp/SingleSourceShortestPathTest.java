package dp;

import dp.SingleSourceShortestPath;
import dp.SingleSourceShortestPathV2;
import model.Edge;
import model.Node;
import model.Path;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class SingleSourceShortestPathTest {
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
        entry.edges.add(Edge.create("p", "w", 2));
        Path path = new Path();
        path.src = "s";
        path.target = "t";
        path.totalLength = 5;
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
        path.totalLength = 5;
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
        path.totalLength = Integer.MAX_VALUE;
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
    public void findSingleSourceShortestPath() {
        List<Entry> entries= getTestData();
        SingleSourceShortestPath singleSourceShortestPath = new SingleSourceShortestPath();
        Utility.FetchValue<Integer> fetchValueInt = a -> {
            if (a == Integer.MAX_VALUE) {
                return "-";
            }
            return Integer.toString(a);
        };
        Utility.FetchValue<Node> fetchValueNode = n -> n.name;
        final String source = "s";
        for (Entry entry : entries) {
            SingleSourceShortestPath.Result result = singleSourceShortestPath.findSingleSourceShortestPath(source, entry.edges);
            if (Utility.isEmpty(entry.edges)) {
                Assert.assertTrue(null == result);
                continue;
            }
            Assert.assertTrue(entry.hasNegativeCycle == result.hasNegativeCycle);
            Utility.dump(result.nodes, fetchValueNode);
            Utility.dump(result.subProblems, fetchValueInt);
            if (entry.hasNegativeCycle) {
                continue;
            }
            for (Path path : result.pathMap.values()) {
                System.out.printf("path: %s --> %s, %d\n", path.src, path.target, path.totalLength);
                for (int i = 0; i < path.edges.size(); ++ i) {
                    System.out.printf("%s -> %s, %d\n", path.edges.get(i).src, path.edges.get(i).target, path.edges.get(i).length);
                }
                System.out.println();
            }

            for (Path path : entry.paths) {
                Path pathFind = result.pathMap.get(path.target);
                Assert.assertTrue(path.totalLength == pathFind.totalLength);
                Assert.assertTrue(path.edges.size() == pathFind.edges.size());
                int edges = path.edges.size();
                for (int i = 0; i < edges; ++ i) {
                    Assert.assertTrue(path.edges.get(i).src.equals(pathFind.edges.get(i).src));
                    Assert.assertTrue(path.edges.get(i).target.equals(pathFind.edges.get(i).target));
                    Assert.assertTrue(path.edges.get(i).length == (pathFind.edges.get(i).length));
                }
            }
        }
    }

    @Test
    public void findSingleSourceShortestPathV2() {
        List<Entry> entries= getTestData();
        SingleSourceShortestPathV2 singleSourceShortestPath = new SingleSourceShortestPathV2();
        Utility.FetchValue<SingleSourceShortestPathV2.Note> fetchValueInt = a -> {
            if (a.value == Integer.MAX_VALUE) {
                return "-";
            }
            return Integer.toString(a.value);
        };
        Utility.FetchValue<Node> fetchValueNode = n -> n.name;
        final String source = "s";
        for (Entry entry : entries) {
            SingleSourceShortestPathV2.Result result = singleSourceShortestPath.findSingleSourceShortestPath(source, entry.edges);
            if (Utility.isEmpty(entry.edges)) {
                Assert.assertTrue(null == result);
                continue;
            }
            Assert.assertTrue(entry.hasNegativeCycle == result.hasNegativeCycle);
            Utility.dump(result.nodes, fetchValueNode);
            Utility.dump(result.subProblems, fetchValueInt);
            System.out.println();
            if (entry.hasNegativeCycle) {
                continue;
            }
            for (Path path : entry.paths) {
                Path pathFind = singleSourceShortestPath.reconstructPath(path.src, path.target, result.nodes, result.nodeMap, result.subProblems);
                if (Utility.isEmpty(path.src) || path.totalLength == Integer.MAX_VALUE) {
                    Assert.assertTrue(pathFind == null);
                    continue;
                }
                Assert.assertTrue(path.totalLength == pathFind.totalLength);
                Assert.assertTrue(path.edges.size() == pathFind.edges.size());
                int edges = path.edges.size();
                for (int i = 0; i < edges; ++ i) {
                    Assert.assertTrue(path.edges.get(i).src.equals(pathFind.edges.get(i).src));
                    Assert.assertTrue(path.edges.get(i).target.equals(pathFind.edges.get(i).target));
                    Assert.assertTrue(path.edges.get(i).length == (pathFind.edges.get(i).length));
                }
                System.out.printf("path: %s --> %s, %d\n", path.src, path.target, path.totalLength);
                for (int i = 0; i < path.edges.size(); ++ i) {
                    System.out.printf("%s -> %s, %d\n", path.edges.get(i).src, path.edges.get(i).target, path.edges.get(i).length);
                }
                System.out.println();

            }
        }
    }

}
