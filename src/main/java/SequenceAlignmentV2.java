
public class SequenceAlignmentV2 {

    static public class Result {
        public char[] leftAligned;
        public char[] rightAligned;
        public int totalPenality;
        public int[][] subProblems;
    }

    private int getPenality(char left, char right, int mismatchPenality) {
        return left == right ? 0 : mismatchPenality;
    }

    public Result sequenceAlign(final char[] left, final char[] right, final int gapPenality, final int mismatchPenality){
        if (left == null || left.length == 0 || right == null || right.length == 0) {
            return null;
        }
        /**
         * probelm[i][j] = min
         *      case 1: problem[i - 1][j - 1] + penality(left(i), right(j))
         *      case 2: problem[i - 1][j] + gapPenality
         *      case 3: problem[i][j -1]  + gapPenality
         * */
        //base case
        //empty string
        int[][] subProblems = new int[left.length + 1][right.length + 1];
        for(int i = 0; i <= left.length; ++ i) {
            subProblems[i][0] = gapPenality * i;
        }
        for (int j = 0; j <= right.length; ++ j) {
            subProblems[0][j] = gapPenality * j;
        }
        for(int i = 1; i <= left.length; ++ i) {
            for (int j = 1; j <= right.length; ++ j) {
                int case1Penality = subProblems[i - 1][j - 1] + getPenality(left[i - 1], right[j -1], mismatchPenality);
                int case2Penality = subProblems[i - 1][j] + gapPenality;
                int case3Penality = subProblems[i][j - 1] + gapPenality;
                subProblems[i][j] = Math.min(Math.min(case1Penality, case2Penality), case3Penality);
            }
        }
        //reconstruct alignment
        return reconstruct(left, right, gapPenality, mismatchPenality, subProblems);
    }

    private Result reconstruct(final char[] left, final char[] right, final int gapPenality, final int mismatchPenality, final int[][] subProblems) {
        StringBuilder leftAlignmentBuilder = new StringBuilder();
        StringBuilder rightAlignmentBuilder = new StringBuilder();
        int i = left.length;
        int j = right.length;
        while (i > 0 && j > 0) {
            int case1Penality = subProblems[i - 1][j - 1] + getPenality(left[i - 1], right[j -1], mismatchPenality);
            int case2Penality = subProblems[i - 1][j] + gapPenality;
            if (subProblems[i][j] == case1Penality) {
                leftAlignmentBuilder.append(left[i - 1]);
                rightAlignmentBuilder.append(right[j - 1]);
                i -= 1;
                j -= 1;
            }
            else if (case2Penality == subProblems[i][j]) {
                leftAlignmentBuilder.append(left[i - 1]);
                rightAlignmentBuilder.append("-");
                i -= 1;
            }
            else {
                //case 3
                leftAlignmentBuilder.append("-");
                rightAlignmentBuilder.append(right[j - 1]);
                j -= 1;
            }
        }
        while (i > 0) {
            leftAlignmentBuilder.append(left[i - 1]);
            rightAlignmentBuilder.append("-");
            i -= 1;
        }
        while (j > 0) {
            rightAlignmentBuilder.append(right[j - 1]);
            leftAlignmentBuilder.append("-");
            j -= 1;
        }
        char[] reversedLeftAlignment = leftAlignmentBuilder.toString().toCharArray();
        char[] reversedRightAlignment = rightAlignmentBuilder.toString().toCharArray();
        Result result = new Result();
        result.leftAligned = new char[reversedLeftAlignment.length];
        result.rightAligned = new char[reversedRightAlignment.length];
        int lastIndex = reversedLeftAlignment.length - 1;
        for (int index = lastIndex; index >= 0; -- index) {
            result.leftAligned[lastIndex - index] = reversedLeftAlignment[index];
            result.rightAligned[lastIndex - index] = reversedRightAlignment[index];
        }
        result.totalPenality = subProblems[left.length][right.length];
        result.subProblems = subProblems;
        return result;
    }
}
