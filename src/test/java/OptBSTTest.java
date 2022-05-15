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
        entries.add(createEntry(new double[] {}, 0));

        return entries;
    }

    @Test
    public void optBSTTest() {
        List<Entry> entries = getTestData();
        OptBST optBST = new OptBST();
        for (Entry entry : entries) {
            OptBST.Result result = optBST.getOptBST(entry.keys, entry.frequencies);
            if (entry.frequencies == null || entry.frequencies.length == 0) {
                Assert.assertTrue(null == result);
                continue;
            }
            Utility.dump(entry.frequencies);
            Utility.dump(result.subProblems);
            Assert.assertTrue(result.weightedSearchTime == entry.minAverageSearchTime);
            System.out.println();
        }
    }

    @Test
    public void optBSTTestV2() {
        List<Entry> entries = getTestData();
        OptBSTV2 optBST = new OptBSTV2();
        for (Entry entry : entries) {
            OptBSTV2.Result result = optBST.getOptBST(entry.keys, entry.frequencies);
            if (entry.frequencies == null || entry.frequencies.length == 0) {
                Assert.assertTrue(null == result);
                continue;
            }
            Utility.dump(entry.frequencies);
            Utility.dump(result.subProblems);
            Assert.assertTrue(result.minAverageSearchTime == entry.minAverageSearchTime);
            System.out.println();
        }
    }

    @Test
    public void optBSTTestV3() {
        List<Entry> entries = getTestData();
        OptBSTV3 optBST = new OptBSTV3();
        Utility.FetchValue<OptBSTV3.Note> fetchValue = note -> {
            if (null == note) {
                return "";
            }
            else {
                return String.format("%5.2f", note.value);
            }
        };
        for (Entry entry : entries) {
            OptBSTV3.Result result = optBST.getOptBST(entry.keys, entry.frequencies);
            if (entry.frequencies == null || entry.frequencies.length == 0) {
                Assert.assertTrue(null == result);
                continue;
            }
            Utility.dump(entry.frequencies);
            Utility.dump(result.subProblems, fetchValue);
            System.out.println();
            Assert.assertTrue(result.minAverageSearchTime == entry.minAverageSearchTime);
        }
    }

}
