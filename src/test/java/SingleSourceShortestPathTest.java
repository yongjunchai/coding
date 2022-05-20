import model.Edge;
import model.Node;
import model.Path;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SingleSourceShortestPathTest {
    static private class Entry{
        public List<Edge> edges = new ArrayList<>();
        public List<Path> paths = new ArrayList<>();
        public boolean hasNegativeCycle;
    }

    private List<Entry> getTestData() {
        List<Entry> entries = new ArrayList<>();

        Entry entry = new Entry();
        entry.edges.add(Edge.create("s", "v", 4));
        entry.edges.add(Edge.create("s", "u", 2));
        entry.edges.add(Edge.create("u", "v", -1));
        entry.edges.add(Edge.create("u", "w", 2));
        entry.edges.add(Edge.create("w", "t", 2));
        entry.edges.add(Edge.create("v", "t", 4));
        entry.edges.add(Edge.create("x", "y", 4));
        entry.edges.add(Edge.create("t", "y", 4));
        Path path = new Path();
        path.src = "s";
        path.target = "t";
        path.totalLengh = 5;
        path.edges.add(Edge.create("s", "u", 2));
        path.edges.add(Edge.create("u", "v", -1));
        path.edges.add(Edge.create("v", "t", 4));
        entry.paths.add(path);
        entry.hasNegativeCycle = false;
        entries.add(entry);


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
        for (Entry entry : entries) {
            SingleSourceShortestPath.Result result = singleSourceShortestPath.findSingleSourceShortestPath("s", entry.edges);
            Utility.dump(result.nodes, fetchValueNode);
            Utility.dump(result.subProblems, fetchValueInt);
            for (Path path : entry.paths) {
                Path pathFind = result.pathMap.get(path.target);
                Assert.assertTrue(path.totalLengh == pathFind.totalLengh);
                Assert.assertTrue(path.edges.size() == pathFind.edges.size());
                int edges = path.edges.size();
                System.out.printf("path: %s --> %s\n", path.src, path.target);
                for (int i = 0; i < edges; ++ i) {
                    Assert.assertTrue(path.edges.get(i).src.equals(pathFind.edges.get(i).src));
                    Assert.assertTrue(path.edges.get(i).target.equals(pathFind.edges.get(i).target));
                    Assert.assertTrue(path.edges.get(i).length == (pathFind.edges.get(i).length));
                    System.out.printf("%s -> %s, %d\n", path.edges.get(i).src, path.edges.get(i).target, path.edges.get(i).length);
                }
            }
        }
    }
}
