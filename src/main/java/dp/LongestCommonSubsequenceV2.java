package dp;

public class LongestCommonSubsequenceV2 {
    static public class Result {
        public String commonSubsequence;
        public int[][] subProblems;
    }

    public Result findLongestCommonSubsequence(final char[] left, final char[] right) {
        if (null == left || left.length == 0 || right == null || right.length == 0) {
            return null;
        }
        int [][] subProblems = new int[left.length + 1][right.length + 1];
        //base case
        for (int i = 0; i <= left.length; ++ i) {
            subProblems[i][0] = 0;
        }
        for (int j = 0; j <= right.length; ++ j) {
            subProblems[0][j] = 0;
        }
        for (int i = 1; i <= left.length; ++ i) {
            for (int j = 1; j <= right.length; ++j) {
                int case1Len = 0;
                int case2Len = 0;
                int case3Len = 0;
                if (left[i - 1] == right[j - 1]) {
                    case1Len = subProblems[i - 1][j - 1] + 1;
                }
                else {
                    case1Len = subProblems[i - 1][j - 1];
                }
                case2Len = subProblems[i - 1][j];
                case3Len = subProblems[i][j - 1];
                subProblems[i][j] = Math.max(Math.max(case1Len, case2Len), case3Len);
            }
        }
        return reconstruct(left, right, subProblems);
    }

    private Result reconstruct(final char[] left, final char[] right, int[][] subProblems) {
        char[] commonSubsequence = new char[subProblems[left.length][right.length]];
        int i = left.length;
        int j = right.length;
        int index = subProblems[left.length][right.length] - 1;
        while (i > 0 && j > 0) {
            int case1Len = 0;
            int case2Len = 0;

            if (left[i - 1] == right[j - 1]) {
                case1Len = subProblems[i - 1][j - 1] + 1;
            }
            else {
                case1Len = subProblems[i - 1][j - 1];
            }
            case2Len = subProblems[i - 1][j];
            if (subProblems[i][j] == case1Len) {
                if (left[i - 1] == right[j - 1]) {
                    commonSubsequence[index] = left[i - 1];
                    index -= 1;
                }
                i -= 1;
                j -= 1;
            } else if (subProblems[i][j] == case2Len) {
                i -= 1;
            }
            else {
                //case 3
                j -= 1;
            }
        }
        Result result = new Result();
        result.commonSubsequence = new String(commonSubsequence);
        result.subProblems = subProblems;
        return result;
    }
}
