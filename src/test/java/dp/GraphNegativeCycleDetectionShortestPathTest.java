package dp;

import model.Edge;
import model.Path;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class GraphNegativeCycleDetectionShortestPathTest {



    static private class Entry{
        public List<Edge> edges = new ArrayList<>();
        public List<Path> paths = new ArrayList<>();
        public boolean hasNegativeCycle;
    }

    private AllPairsShortestPathTest.Entry getEntry1() {
        AllPairsShortestPathTest.Entry entry = new AllPairsShortestPathTest.Entry();
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
        path.totalLength = 5;
        path.edges.add(Edge.create("s", "u", 2));
        path.edges.add(Edge.create("u", "v", -1));
        path.edges.add(Edge.create("v", "t", 4));
        entry.paths.add(path);
        entry.hasNegativeCycle = false;
        return entry;
    }

    private AllPairsShortestPathTest.Entry getEntryPathEmptyStart() {
        AllPairsShortestPathTest.Entry entry = new AllPairsShortestPathTest.Entry();
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

    private AllPairsShortestPathTest.Entry getEntryNoPath() {
        AllPairsShortestPathTest.Entry entry = new AllPairsShortestPathTest.Entry();
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

    private AllPairsShortestPathTest.Entry getEntryInvalidPathEndpoint() {
        AllPairsShortestPathTest.Entry entry = new AllPairsShortestPathTest.Entry();
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
        path.src = "w";
        path.target = "z";
        path.totalLength = Integer.MAX_VALUE;
        entry.paths.add(path);
        entry.hasNegativeCycle = false;
        return entry;
    }


    private AllPairsShortestPathTest.Entry getEntry2() {
        AllPairsShortestPathTest.Entry entry = new AllPairsShortestPathTest.Entry();
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

    private AllPairsShortestPathTest.Entry getEntryEmpty() {
        AllPairsShortestPathTest.Entry entry = new AllPairsShortestPathTest.Entry();
        return entry;
    }


    private List<AllPairsShortestPathTest.Entry> getTestData() {
        List<AllPairsShortestPathTest.Entry> entries = new ArrayList<>();
        entries.add(getEntry1());
        entries.add(getEntry2());
        entries.add(getEntryEmpty());
        entries.add(getEntryPathEmptyStart());
        entries.add(getEntryNoPath());
        entries.add(getEntryInvalidPathEndpoint());
        return entries;
    }

    @Test
    public void graphNegativeCycleDetectionShortestPath() {
        List<AllPairsShortestPathTest.Entry> entries = getTestData();
        GraphNegativeCycleDetectionShortestPath graphNegativeCycleDetectionShortestPath = new GraphNegativeCycleDetectionShortestPath();
        for (AllPairsShortestPathTest.Entry entry : entries) {
            GraphNegativeCycleDetectionShortestPath.Result result = graphNegativeCycleDetectionShortestPath.findAllPairsShortestPath(entry.edges);
            if (Utility.isEmpty(entry.edges)) {
                Assert.assertTrue(null == result);
                continue;
            }
            Assert.assertTrue(result.hasNegativeCycle == entry.hasNegativeCycle);
            if (! result.hasNegativeCycle) {
                Assert.assertTrue(null != result.subProblems);
            }
        }
    }
}
