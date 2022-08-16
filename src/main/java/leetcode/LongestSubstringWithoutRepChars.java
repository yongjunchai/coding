package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepChars {

    public int lengthOfLongestSubstring(String s) {
        if (null == s || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int longestSubstringNoRepCharLen = 1;
        int startIndex = 0;
        charIndexMap.put(s.charAt(0), 0);

        int len = s.length();
        for (int i = 1; i < len; ++ i) {
            Integer charIndex = charIndexMap.get(s.charAt(i));
            if (null == charIndex) {
                charIndexMap.put(s.charAt(i), i);
                continue;
            }
            if (charIndexMap.size() > longestSubstringNoRepCharLen) {
                longestSubstringNoRepCharLen = charIndexMap.size();
            }
            //remove all chars from the map up to and include the repeated char
            if (charIndex == i - 1) {
                charIndexMap.clear();
            }
            else {
                for (int j = startIndex; j <= charIndex; ++ j) {
                    charIndexMap.remove(s.charAt(j));
                }
            }
            startIndex = charIndex + 1;
            charIndexMap.put(s.charAt(i), i);
        }
        if (charIndexMap.size() > longestSubstringNoRepCharLen) {
            longestSubstringNoRepCharLen = charIndexMap.size();
        }
        return longestSubstringNoRepCharLen;
    }
}
