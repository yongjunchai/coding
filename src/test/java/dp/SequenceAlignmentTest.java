package dp;

import dp.SequenceAlignment;
import dp.SequenceAlignmentV2;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class SequenceAlignmentTest {

    private static class Entry {
        public String left;
        public String right;
        public int gapPenality;
        public int mismatchPenality;
        public int minPenality;

        static public Entry create(final String left, final String right, final int gapPenality, final int mismatchPenality, final int minPenality) {
            Entry entry = new Entry();
            entry.left = left;
            entry.right = right;
            entry.gapPenality = gapPenality;
            entry.mismatchPenality = mismatchPenality;
            entry.minPenality = minPenality;
            return entry;
        }
    }

    private List<Entry> getTestData() {
        List<Entry> entries = new ArrayList<>();
        entries.add(Entry.create("", "bcd", 1, 2, -1));
        entries.add(Entry.create("d", "bcd", 1, 2, 2));
        entries.add(Entry.create("abc", "bcd", 1, 2, 2));
        entries.add(Entry.create("how you", "hi how are you", 1, 2, 7));
        entries.add(Entry.create("hi how are you", "how you", 1, 2, 7));
        entries.add(Entry.create("def", "e", 1, 2, 2));
        entries.add(Entry.create("abcdef", "def", 1, 2, 3));
        entries.add(Entry.create("abcdef", "acf", 1, 2, 3));
        entries.add(Entry.create("hi how are you. today is a good day", "how you! is a day", 1, 2, 20));
        return entries;
    }

    @Test
    public void sequenceAlignment() {

        List<Entry> entries = getTestData();
        SequenceAlignment sequenceAlignment = new SequenceAlignment();
        Utility.FetchValue<SequenceAlignment.Note> fetchValue = (a) -> {return Integer.toString(a.penality);} ;
        for (Entry entry : entries) {
            SequenceAlignment.Result result = sequenceAlignment.sequenceAlign(entry.left.toCharArray(), entry.right.toCharArray(), entry.gapPenality, entry.mismatchPenality);
            if (entry.left.length() == 0 || entry.right.length() == 0) {
                Assert.assertTrue(null == result);
                continue;
            }
            System.out.println(result.leftAligned);
            System.out.println(result.rightAligned);
            Utility.dump(result.subProblems, fetchValue);
            Assert.assertTrue(result.totalPenality == entry.minPenality);
        }
    }

    @Test
    public void sequenceAlignmentV2() {

        List<Entry> entries = getTestData();
        SequenceAlignmentV2 sequenceAlignmentV2 = new SequenceAlignmentV2();
        Utility.FetchValue<Integer> fetchValue = (a) -> {return Integer.toString(a);} ;
        for (Entry entry : entries) {
            SequenceAlignmentV2.Result result = sequenceAlignmentV2.sequenceAlign(entry.left.toCharArray(), entry.right.toCharArray(), entry.gapPenality, entry.mismatchPenality);
            if (entry.left.length() == 0 || entry.right.length() == 0) {
                Assert.assertTrue(null == result);
                continue;
            }
            System.out.println(result.leftAligned);
            System.out.println(result.rightAligned);
            Utility.dump(result.subProblems, fetchValue);
            Assert.assertTrue(result.totalPenality == entry.minPenality);
        }
    }
}
