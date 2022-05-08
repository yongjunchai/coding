public class SequenceAlignment {

    public static class Result {
        public char[] leftAligned;
        public char[] rightAligned;
        public int totalPenality;
        public Note[][] subProblems;
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
        if (null == left || null == right || left.length == 0 || right.length == 0) {
            return null;
        }
        Note[][] subProblem = new Note[left.length + 1][right.length + 1];
        for (int i = 0; i <= left.length; ++ i) {
            for (int j = 0; j <= right.length; ++j) {
                subProblem[i][j] = new Note();
            }
        }
        //base case. sequence is empty
        for (int i = 0; i <= left.length; ++ i) {
            subProblem[i][0].penality = i * gapPenality;
        }
        for (int j = 0; j <= right.length; ++ j) {
            subProblem[0][j].penality = j * gapPenality;
            subProblem[0][j].leftGapAdded = j;
        }
        for (int i = 1; i <= left.length; ++ i) {
            for (int j = 1; j <= right.length; ++ j) {
                int case1Penality =  subProblem[i - 1][j -1].penality + (left[i - 1] == right[j - 1] ? 0 : mismatchPenality);
                int case2Penality = subProblem[i - 1][j].penality + gapPenality;
                int case3Penality = subProblem[i][j - 1].penality + gapPenality;
                int minPenality = Math.min(Math.min(case1Penality, case2Penality), case3Penality);
                if (minPenality == case1Penality) {
                    subProblem[i][j].leftGapAdded = subProblem[i - 1][j -1].leftGapAdded;
                }
                else if (minPenality == case2Penality) {
                    subProblem[i][j].leftGapAdded = subProblem[i - 1][j].leftGapAdded;
                }
                else {
                    subProblem[i][j].leftGapAdded = subProblem[i][j -1].leftGapAdded + 1;
                }
                subProblem[i][j].penality = minPenality;
            }
        }
        return reconstruct(left, right, gapPenality, mismatchPenality, subProblem);
    }

    private Result reconstruct(final char[] left, final char[] right, final int gapPenality, final int mismatchPenality, final Note[][] suProblems) {
        //reconstruct sequence alignment
        int alignedLength = left.length + suProblems[left.length][right.length].leftGapAdded;
        char[] leftAligned = new char[alignedLength];
        char[] rightAligned = new char[alignedLength];
        int i = left.length;
        int j = right.length;
        int curtIndex = leftAligned.length - 1;
        while (i > 0 && j > 0) {
            int case1Penality =  suProblems[i - 1][j -1].penality + (left[i - 1] == right[j - 1] ? 0 : mismatchPenality);
            int case2Penality = suProblems[i - 1][j].penality + gapPenality;
            if (case1Penality == suProblems[i][j].penality) {
                leftAligned[curtIndex] = left[i - 1];
                rightAligned[curtIndex] = right[j - 1];
                i -= 1;
                j -= 1;
            }
            else if (case2Penality == suProblems[i][j].penality) {
                leftAligned[curtIndex] = left[i - 1];
                rightAligned[curtIndex] = '-';
                i -= 1;
            }
            else {
                leftAligned[curtIndex] = '-';
                rightAligned[curtIndex] = right[j - 1];
                j -= 1;
            }
            curtIndex -= 1;
        }
        int iIndex = curtIndex;
        int jIndex = curtIndex;
        while (i > 0) {
            leftAligned[iIndex] = left[i - 1];
            rightAligned[iIndex] = '-';
            i -= 1;
            iIndex -= 1;
        }
        while (j > 0) {
            leftAligned[jIndex] = '-';
            rightAligned[jIndex] = right[j - 1];
            jIndex -= 1;
            j -= 1;
        }
        Result result = new Result();
        result.leftAligned = leftAligned;
        result.rightAligned = rightAligned;
        result.totalPenality = suProblems[left.length][right.length].penality;
        result.subProblems = suProblems;
        return result;
    }
}
