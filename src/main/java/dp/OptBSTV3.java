package dp;

public class OptBSTV3 {

    static public class Note {
        public double value;
        public int rootNodeIndex;
    }

    static public class Result {
        public double minAverageSearchTime;
        public Note[][] subProblems;
    }

    private double[][] getFrequencyRangeSum(final double[] frequencies) {
        // subProblem[i][j] is the sum of elements in frequencies[i] to frequencies[j] inclusive, i <= j
        // subProblem[i][j] = subProblem[i][j - 1] + frequencies[j]
        double[][] subProblems = new double[frequencies.length][frequencies.length];
        //base case
        for(int i = 0; i < frequencies.length; ++ i) {
            subProblems[i][i] = frequencies[i];
        }

        //systemically solve the sub problems
        for (int i = 0; i < frequencies.length; ++ i) {
            for (int j = i + 1; j < frequencies.length; ++ j) {
                subProblems[i][j] = subProblems[i][j - 1] + frequencies[j];
            }
        }
        return subProblems;
    }

    private Note getMinOfSubProblems(final int start, final int end, final Note[][] subProblems) {
        double minAverageSearchTime = Double.MAX_VALUE;
        int rootSelected = -1;
        int rootMin = subProblems[start][end - 1].rootNodeIndex;
        int rootMax = subProblems[start + 1][end].rootNodeIndex;
        for (int root = rootMin; root <= rootMax; ++ root) {
            double subProblemSum = 0;
            if (root > start) {
                subProblemSum += subProblems[start][root - 1].value;
            }
            if (root < end) {
                subProblemSum += subProblems[root + 1][end].value;
            }
            if (subProblemSum < minAverageSearchTime) {
                minAverageSearchTime = subProblemSum;
                rootSelected = root;
            }
        }
        Note note = new Note();
        note.rootNodeIndex = rootSelected;
        note.value = minAverageSearchTime;
        return note;
    }

    public Result getOptBST(final int[] keys, final double[] frequencies) {
        if (null == keys || null == frequencies || keys.length != frequencies.length || keys.length == 0) {
            return null;
        }

        double[][] frequencySum = getFrequencyRangeSum(frequencies);

        /**
         * subProblems[i][j] = sum(i to j of frquency) + min of sumProblem[i][r - 1] + subProblem[i + 1][j]  (i <= j)
         * */
        Note[][] subProblems = new Note[keys.length][keys.length];

        //base case
        for (int i = 0; i < keys.length; ++ i) {
            for (int j = i; j < keys.length; ++ j) {
                subProblems[i][j] = new Note();
            }
        }
        for (int i = 0; i < keys.length; ++ i) {
            subProblems[i][i].value = frequencies[i];
            subProblems[i][i].rootNodeIndex = i;
        }
        int maxSteps = keys.length - 1;
        //systemically solve the sub problems
        for (int step = 1; step <= maxSteps; ++ step) {
            int end = keys.length - 1 - step;
            for (int i = 0; i <= end; ++ i) {
                Note note = getMinOfSubProblems(i, i + step, subProblems);
                subProblems[i][i + step].value = frequencySum[i][i + step] + note.value;
                subProblems[i][i + step].rootNodeIndex = note.rootNodeIndex;
            }
        }
        Result result = new Result();
        result.subProblems = subProblems;
        result.minAverageSearchTime = subProblems[0][keys.length - 1].value;
        return result;
    }
}
