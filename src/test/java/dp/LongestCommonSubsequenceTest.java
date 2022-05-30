package dp;

import dp.LongestCommonSubsequence;
import dp.LongestCommonSubsequenceV2;
import dp.SequenceAlignmentV2;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubsequenceTest {

    private static class Entry {
        public String str1;
        public String str2;
        public String lcs;

        static public Entry create(final String str1, final String str2, final String lcs) {
            Entry entry = new Entry();
            entry.str1 = str1;
            entry.str2 = str2;
            entry.lcs = lcs;
            return entry;
        }
    }

    private List<Entry> getTestData() {
        List<Entry> entries = new ArrayList<>();
        entries.add(Entry.create("abababababddd", "aaaaaaa", "aaaaa"));
        entries.add(Entry.create("abchihowareyoudef", "kkk abc are you def good", "abcareyoudef"));
        entries.add(Entry.create("abchihowareyoudef", "", ""));
        entries.add(Entry.create("abc", "def", ""));
        return entries;
    }

    @Test
    public void testLcs() {
        List<Entry> entries = getTestData();
        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        Utility.FetchValue<LongestCommonSubsequence.Note> fetchValue = (LongestCommonSubsequence.Note a) -> {return Integer.toString(a.subsequenceLen);} ;
        for (Entry entry : entries) {
            LongestCommonSubsequence.Result result = longestCommonSubsequence.findLongestCommonSubsequence(entry.str1.toCharArray(), entry.str2.toCharArray());
            Assert.assertTrue(entry.lcs.equals(result.commonSubsequence));
            System.out.println(entry.str1);
            System.out.println(entry.str2);
            System.out.println(result.commonSubsequence);
            Utility.dump(result.notes, fetchValue);
        }
    }

    @Test
    public void testLcsV2() {
        List<Entry> entries = getTestData();
        LongestCommonSubsequenceV2 longestCommonSubsequence = new LongestCommonSubsequenceV2();
        Utility.FetchValue<Integer> fetchValue = (a) -> {return Integer.toString(a);} ;
        for (Entry entry : entries) {
            LongestCommonSubsequenceV2.Result result = longestCommonSubsequence.findLongestCommonSubsequence(entry.str1.toCharArray(), entry.str2.toCharArray());
            System.out.println(entry.str1);
            System.out.println(entry.str2);
            if (Utility.isEmpty(entry.str1) || Utility.isEmpty(entry.str2)) {
                Assert.assertTrue(null == result);
                continue;
            }
            System.out.println(result.commonSubsequence);
            Utility.dump(result.subProblems, fetchValue);
            Assert.assertTrue(entry.lcs.equals(result.commonSubsequence));
        }
    }

    private String getCommonSubsequence(final char[] leftAligned, final char[] rightAligned) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = leftAligned.length - 1; i >= 0; --i) {
            if (leftAligned[i] == rightAligned[i]) {
                stringBuilder.append(leftAligned[i]);
            }
        }
        char[] reversedStr = stringBuilder.toString().toCharArray();
        int lastIndex = stringBuilder.length() - 1;
        char[] commonStr = new char[stringBuilder.length()];

        for (int i = lastIndex; i >=0; -- i) {
            commonStr[lastIndex - i] = reversedStr[i];
        }
        return new String(commonStr);
    }

    @Test
    public void testLcsWithSubsequenceAlignment() {
        List<Entry> entries = getTestData();
        SequenceAlignmentV2 sequenceAlignmentV2 = new SequenceAlignmentV2();
        Utility.FetchValue<Integer> fetchValue = (a) -> {return Integer.toString(a);} ;

        for (Entry entry: entries) {
            SequenceAlignmentV2.Result result = sequenceAlignmentV2.sequenceAlign(entry.str1.toCharArray(), entry.str2.toCharArray(), 1, 2);
            System.out.println(entry.str1);
            System.out.println(entry.str2);
            if (Utility.isEmpty(entry.str1) || Utility.isEmpty(entry.str2)) {
                Assert.assertTrue(null == result);
                continue;
            }
            String commonSubsequence = getCommonSubsequence(result.leftAligned, result.rightAligned);
            System.out.println(commonSubsequence);
            Utility.dump(result.subProblems, fetchValue);
            Assert.assertTrue(entry.lcs.equals(commonSubsequence));
        }
    }
}
