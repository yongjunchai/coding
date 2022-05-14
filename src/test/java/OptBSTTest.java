import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class OptBSTTest {

    private static class Entry {
        public int[] keys;
        public double[] frequencies;
        public double minAverageSearchTime;
    }

    private Entry createEntry(final double[] frequencies, final double minAverageSearchTime) {
        Entry entry = new Entry();
        entry.keys = new int[frequencies.length];
        entry.frequencies = frequencies;
        for(int i = 0; i < frequencies.length; ++ i) {
            entry.keys[i] = i + 1;
        }
        entry.minAverageSearchTime = minAverageSearchTime;
        return entry;
    }

    private List<Entry> getTestData() {
        List<Entry> entries = new ArrayList<>();
        entries.add(createEntry(new double[] {0.1, 0.8, 0.1}, 1.2));
        entries.add(createEntry(new double[] {0.8, 0.1, 0.1}, 1.3));
        entries.add(createEntry(new double[] {0.1, 0.1, 0.8}, 1.3));
        return entries;
    }

    @Test
    public void optBSTTest() {
        List<Entry> entries = getTestData();
        OptBST optBST = new OptBST();
        for (Entry entry : entries) {
            OptBST.Result result = optBST.getOptBST(entry.keys, entry.frequencies);
            Utility.dump(result.subProblems);
            Assert.assertTrue(result.weightedSearchTime == entry.minAverageSearchTime);
        }
    }
}
