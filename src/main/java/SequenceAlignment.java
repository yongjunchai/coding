public class SequenceAlignment {

    public static class Result {
        public char[] leftAligned;
        public char[] rightAligned;
        public int totalPenality;
        public Note[][] notes;
    }

    static public class Note
    {
        public int penality = 0;
        public int leftGapAdded = 0;
    }
    /**
     * Problem:
     *  align two string. char can be aligned with other char or a gap. There is a penality for gap alignment or char mismatch.
     *  find the alignment with two string with the minimal penality.
     *  prefix sequence alignment.
     *  problem[i][j] = min {
     *      case 1: the last character of two sequences align with each others.
     *              problem[i - 1][j - 1] + penality(i, j)
     *      case 2: the last character of first sequence align with a gap
     *              problem[i - 1][j] + gap penality
     *      case 3: the last character of second sequence align with a gap
     *              problem[i][j - 1] + gap penality
     *  }
     * */
    public Result sequenceAlign(final char[] left, final char[] right, final int gapPenality, final int mismatchPenality) {
        if (null == left || null == right) {
            return null;
        }
        Note[][] problem = new Note[left.length + 1][right.length + 1];
        for (int i = 0; i <= left.length; ++ i) {
            for (int j = 0; j <= right.length; ++j) {
                problem[i][j] = new Note();
            }
        }
        //base case. sequence is empty
        for (int i = 0; i <= left.length; ++ i) {
            problem[i][0].penality = i * gapPenality;
        }
        for (int j = 0; j <= right.length; ++ j) {
            problem[0][j].penality = j * gapPenality;
        }
        for (int i = 1; i <= left.length; ++ i) {
            for (int j = 1; j <= right.length; ++ j) {
                int case1Penality =  problem[i - 1][j -1].penality + (left[i - 1] == right[j - 1] ? 0 : mismatchPenality);
                int case2Penality = problem[i - 1][j].penality + gapPenality;
                int case3Penality = problem[i][j - 1].penality + gapPenality;
                int minPenality = Math.min(Math.min(case1Penality, case2Penality), case3Penality);
                if (minPenality == case1Penality) {
                    problem[i][j].leftGapAdded = problem[i - 1][j -1].leftGapAdded;
                }
                else if (minPenality == case2Penality) {
                    problem[i][j].leftGapAdded = problem[i - 1][j].leftGapAdded;
                }
                else {
                    problem[i][j].leftGapAdded = problem[i][j -1].leftGapAdded + 1;
                }
                problem[i][j].penality = minPenality;
            }
        }
        return reconstruct(left, right, gapPenality, mismatchPenality, problem);
    }

    private Result reconstruct(final char[] left, final char[] right, final int gapPenality, final int mismatchPenality, final Note[][] problem) {
        //reconstruct sequence alignment
        char[] leftAligned = new char[left.length + problem[left.length][right.length].leftGapAdded];
        char[] rightAligned = new char[left.length + problem[left.length][right.length].leftGapAdded];
        int i = left.length;
        int j = right.length;
        int curIndex = leftAligned.length - 1;
        while (i > 0 && j > 0) {
            int case1Penality =  problem[i - 1][j -1].penality + (left[i - 1] == right[j - 1] ? 0 : mismatchPenality);
            int case2Penality = problem[i - 1][j].penality + gapPenality;
            if (case1Penality == problem[i][j].penality) {
                leftAligned[curIndex] = left[i - 1];
                rightAligned[curIndex] = right[j - 1];
                i -= 1;
                j -= 1;
            }
            else if (case2Penality == problem[i][j].penality) {
                leftAligned[curIndex] = left[i - 1];
                rightAligned[curIndex] = '-';
                i -= 1;
            }
            else {
                leftAligned[curIndex] = '-';
                rightAligned[curIndex] = right[j - 1];
                j -= 1;
            }
            curIndex -= 1;
        }
        while (i > 0) {
            leftAligned[curIndex] = left[i - 1];
            rightAligned[curIndex] = '-';
            i -= 1;
            curIndex -= 1;
        }
        while (j > 0) {
            leftAligned[curIndex] = '-';
            rightAligned[curIndex] = right[j - 1];
            curIndex -= 1;
            j -= 1;
        }
        Result result = new Result();
        result.leftAligned = leftAligned;
        result.rightAligned = rightAligned;
        result.totalPenality = problem[left.length][right.length].penality;
        result.notes = problem;
        return result;
    }
}
