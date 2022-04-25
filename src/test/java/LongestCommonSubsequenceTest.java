import org.testng.Assert;
import org.testng.annotations.Test;

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

    @Test
    public void testLcs() {
        List<Entry> entries = new ArrayList<>();
        entries.add(Entry.create("abababababddd", "aaaaaaa", "aaaaa"));
        entries.add(Entry.create("abchihowareyoudef", "kkk abc are you def good", "abcareyoudef"));
        entries.add(Entry.create("abchihowareyoudef", "", ""));
        entries.add(Entry.create("abc", "def", ""));
        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        for (Entry entry : entries) {
            String lcs = longestCommonSubsequence.findLongestCommonSubsequence(entry.str1.toCharArray(), entry.str2.toCharArray());
            Assert.assertTrue(entry.lcs.equals(lcs));
        }
    }
}
