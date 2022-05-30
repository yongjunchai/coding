package dp;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubstringTest {

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
        entries.add(Entry.create("d helloa f ld", "ppp chhhelloadef k ld helloa f ld", "d helloa f ld"));
        entries.add(Entry.create("", "chhhelloadef", ""));
        entries.add(Entry.create("abc", "def", ""));
        entries.add(Entry.create("hi how are you.", "i am fine, and you.", " you."));
        entries.add(Entry.create("hi how are you. good", "i am fine, and you. see you. bye.", "e you. "));
        return entries;
    }

    @Test
    public void testLcs() {
        List<Entry> entries = getTestData();
        LongestCommonSubstring longestCommonSubstring = new LongestCommonSubstring();
        Utility.FetchValue<LongestCommonSubstring.Note> fetchValue = (LongestCommonSubstring.Note a) -> {return Integer.toString(a.substringLen);} ;
        for (Entry entry: entries) {
            LongestCommonSubstring.Result result = longestCommonSubstring.longestCommonSubstring(entry.str1.toCharArray(), entry.str2.toCharArray());
            System.out.println(entry.str1);
            System.out.println(entry.str2);
            System.out.println(result.commonSubstring);
            Utility.dump(result.notes, fetchValue);
            Assert.assertTrue(entry.lcs.equals(result.commonSubstring));
        }
    }

    @Test
    public void testLcsV2() {
        List<Entry> entries = getTestData();
        LongestCommonSubstringV2 longestCommonSubstringV2 = new LongestCommonSubstringV2();
        Utility.FetchValue<Integer> fetchValue = (a) -> {return Integer.toString(a);} ;
        for (Entry entry: entries) {
            LongestCommonSubstringV2.Result result = longestCommonSubstringV2.longestCommonSubstring(entry.str1.toCharArray(), entry.str2.toCharArray());
            System.out.println(entry.str1);
            System.out.println(entry.str2);
            System.out.println(result.commonSubstring);
            Utility.dump(result.subProblems, fetchValue);
            Assert.assertTrue(entry.lcs.equals(result.commonSubstring));
        }
    }
}
