package dp;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class WISTest {

    private static class Entry
    {
        public WIS.Node[] nodes;
        public int maxWisWeight;
    }

    private List<Entry> getEntries() {
        List<Entry> entries = new ArrayList<>();
        Entry entry1 = new Entry();
        entry1.nodes = new WIS.Node[5];
        entry1.maxWisWeight = 117;
        entry1.nodes[0] = WIS.Node.create("A", 1);
        entry1.nodes[1] = WIS.Node.create("B", 4);
        entry1.nodes[2] = WIS.Node.create("C", 5);
        entry1.nodes[3] = WIS.Node.create("D", 7);
        entry1.nodes[4] = WIS.Node.create("E", 111);
        entries.add(entry1);

        Entry entry2 = new Entry();
        entry2.nodes = new WIS.Node[4];
        entry2.maxWisWeight = 124;
        entry2.nodes[0] = WIS.Node.create("A", 117);
        entry2.nodes[1] = WIS.Node.create("B", 4);
        entry2.nodes[2] = WIS.Node.create("C", 5);
        entry2.nodes[3] = WIS.Node.create("D", 7);
        entries.add(entry2);

        Entry entry3 = new Entry();
        entry3.nodes = null;
        entries.add(entry3);

        return entries;
    }

    @Test
    public void wis() {
        List<Entry> entries = getEntries();
        WIS wis = new WIS();
        for (Entry entry : entries) {
            WIS.Result result = wis.findMaxWIS(entry.nodes);
            if (entry.nodes == null || entry.nodes.length == 0) {
                Assert.assertTrue(null == result);
                continue;
            }
            Utility.FetchValue<WIS.Node> fetchValue = t -> (t.name + ":" + t.weight);
            Utility.dump(entry.nodes, fetchValue);
            Utility.dump(result.nodes, fetchValue);
            Assert.assertTrue(result.totalWeight == entry.maxWisWeight);
        }
    }
}
