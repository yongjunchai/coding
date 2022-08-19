package leetcode;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class MedianOfTwoSortedArrayTest {

    private static class Entry {
        public int[] num1;
        public int[] num2;
        public double median;

        public static Entry create(int[] num1, int[] num2, double median) {
            Entry entry = new Entry();
            entry.num1 = num1;
            entry.num2 = num2;
            entry.median = median;
            return entry;
        }
    }

    private List<Entry> getTestData() {
        List<Entry> entries = new ArrayList<>();
        entries.add(Entry.create(new int[] {1, 2, 2}, new int[] {}, 2.0));
        entries.add(Entry.create(new int[] {1, 2, 2}, new int[] {1, 2, 3}, 2.0));
        entries.add(Entry.create(new int[] {3}, new int[] {-2, -1}, -1));
        entries.add(Entry.create(new int[] {-2, -1}, new int[] {3}, -1));
        entries.add(Entry.create(new int[] {7, 8, 11, 100}, new int[] {6, 9, 10, 22}, 9.5));
        entries.add(Entry.create(new int[] {6, 9, 10, 22}, new int[] {7, 8, 11, 100}, 9.5));
        return entries;
    }

    @Test
    public void test() {
        MedianOfTwoSortedArray medianOfTwoSortedArray = new MedianOfTwoSortedArray();
        List<Entry> entries = getTestData();
        for (Entry entry : entries) {
            double median = medianOfTwoSortedArray.findMedianSortedArrays(entry.num1, entry.num2);
            Assert.assertTrue(median == entry.median);
        }

    }
}
