package greedy;

import model.Edge;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumSpanningTreesTest {

    private static class Entry {
        public List<Edge> edges;
        public int mstLen;
    }

    private Entry getEntry1() {
        Entry entry = new Entry();
        List<Edge> edges = new ArrayList<>();
        edges.add(Edge.create("a", "b", 1));
        edges.add(Edge.create("a", "d", 3));
        edges.add(Edge.create("a", "c", 4));
        edges.add(Edge.create("b", "d", 2));
        edges.add(Edge.create("c", "d", 5));
        entry.edges = edges;
        entry.mstLen = 7;
        return entry;
    }

    private List<Entry> getTestData() {
        return Arrays.asList(getEntry1());
    }

    @Test
    public void mstTest() {
        List<Entry> entries = getTestData();
        MinimumSpanningTrees minimumSpanningTrees = new MinimumSpanningTrees();
        for (Entry entry : entries) {
            MinimumSpanningTrees.Result result = minimumSpanningTrees.prim(entry.edges);
            for (Edge edge : result.edges) {
                System.out.println(String.format("%3s -> %3s : %3d", edge.src, edge.target, edge.length));
            }
            Assert.assertTrue(entry.mstLen == result.length);
        }
    }


}
