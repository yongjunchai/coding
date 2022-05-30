package dp;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class KnapsackTest {

    public static class Entry {
        public int[] sizes;
        public int[] values;
        public int capacity;
        public int maxValue;
    }

    private List<Entry> getTestData() {
        List<Entry> entries = new ArrayList<>();
        Entry entry1 = new Entry();
        entry1.sizes = new int[]{4, 3, 2, 3};
        entry1.values = new int[] {3, 2, 4, 4};
        entry1.capacity = 6;
        entry1.maxValue = 8;
        entries.add(entry1);

        Entry entry2 = new Entry();
        entries.add(entry2);

        return entries;
    }

    @Test
    public void knapsackTest() {
        List<Entry> entries = getTestData();
        Knapsack knapsack = new Knapsack();
        Utility.FetchValue<Integer> fetchValue = a -> Integer.toString(a);
        for (Entry entry : entries) {
            Knapsack.Result result = knapsack.solveKnapsack(entry.sizes, entry.values, entry.capacity);
            if (entry.sizes == null || entry.sizes.length == 0 || entry.values == null || entry.values.length == 0 || entry.sizes.length != entry.values.length
            || entry.capacity <= 0) {
                Assert.assertTrue(null == result);
                continue;
            }
            Utility.dump(entry.sizes);
            Utility.dump(entry.values);
            Utility.dump(result.itemSelected);
            Utility.dump(result.subProblems, fetchValue);
            Assert.assertTrue(result.totalValue == entry.maxValue);
        }
    }
}
