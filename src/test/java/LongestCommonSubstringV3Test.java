import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubstringV3Test {
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
        entries.add(Entry.create("abc dddddddd", "dddddddd abc", "abc"));
        return entries;
    }

    @Test
    public void longestCommonSubstringTest() {
        List<Entry> entries = getTestData();
        LongestCommonSubstringV3 longestCommonSubstringV3 = new LongestCommonSubstringV3();
        Utility.FetchValue<Integer> fetchValue = a -> Integer.toString(a);
        for (Entry entry : entries) {
            LongestCommonSubstringV3.Result result = longestCommonSubstringV3.longestCommonSubstring(entry.str1.toCharArray(), entry.str2.toCharArray());
            Utility.dump(result.subProblems, fetchValue);
            Assert.assertTrue(result.commonSubstring.equals(entry.lcs));
        }
    }

}
