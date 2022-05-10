
public class LongestCommonSubstringV2 {
    static public class Result {
        public String commonString;
        public int[][] subProblems;

        static public Result create() {
            Result result = new Result();
            result.commonString = "";
            return result;
        }
    }

    private boolean isEmpty(final char[] sz) {
        return null == sz || sz.length == 0;
    }
    /**
     * subProblems[i][j] = :
     *      case 1:  if left[i - 1] == right[j - 1]
     *          subProblems[i][j] = subProblems[i - 1][j - 1] + 1
     *      case 2: if left[i - 1] != right[j - 1]
     *          subProblems[i][j] = 0
     *
     * save the common string length inside the table,
     * tracking the longest common string index in separate variables
     * */
    public Result longestCommonSubstring(final char[] left, final char[] right) {
        if (isEmpty(left) || isEmpty(right)) {
            return Result.create();
        }
        int[][] subProblems = new int[left.length + 1][right.length + 1];
        //base case, prefix string length is 0
        for (int i = 0; i <= left.length; ++ i) {
            //right string is 0 length
            subProblems[i][0] = 0;
        }
        for (int j = 0; j <= right.length; ++ j) {
            //left string is 0 length
            subProblems[0][j] = 0;
        }
        int lenLcs  = 0;
        int iLcs = -1;
        for (int i = 1; i <= left.length; ++ i) {
            for (int j = 1; j <= right.length; ++ j) {
                if (left[i - 1] == right[j - 1]) {
                    subProblems[i][j] = subProblems[i - 1][j - 1] + 1;
                }
                else {
                    subProblems[i][j] = 0;
                }
                if (subProblems[i][j] > lenLcs) {
                    lenLcs = subProblems[i][j];
                    iLcs = i - 1;
                }
            }
        }
        if (lenLcs == 0) {
            return Result.create();
        }
        return reconstructLcs(left, lenLcs, iLcs, subProblems);
    }

    private Result reconstructLcs(final char[] left, final int lenLcs, final int iLcs, final int[][] subProblems) {
        char[] lcs = new char[lenLcs];
        int i = iLcs;
        int leftLen = lenLcs;
        while (leftLen > 0) {
            lcs[leftLen - 1] = left[i];
            i -= 1;
            leftLen -= 1;
        }
        Result result = Result.create();
        result.commonString = new String(lcs);
        result.subProblems = subProblems;
        return result;
    }
}
