public class OptBSTV2 {

    static public class Result {
        public double minAverageSearchTime;
        public double[][] subProblems;
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

    private double getMinOfSubProblems(final int start, final int end, final double[][] subProblems) {
        double minAverageSearchTime = Double.MAX_VALUE;
        for (int root = start; root <= end; ++ root) {
            double subProblemSum = 0;
            if (root > start) {
                subProblemSum += subProblems[start][root - 1];
            }
            if (root < end) {
                subProblemSum += subProblems[root + 1][end];
            }
            if (subProblemSum < minAverageSearchTime) {
                minAverageSearchTime = subProblemSum;
            }
        }
        return minAverageSearchTime;
    }

    public Result getOptBST(final int[] keys, final double[] frequencies) {
        if (null == keys || null == frequencies || keys.length != frequencies.length || keys.length == 0) {
            return null;
        }

        double[][] frequencySum = getFrequencyRangeSum(frequencies);

        /**
         * subProblems[i][j] = sum(i to j of frquency) + min of sumProblem[i][r - 1] + subProblem[i + 1][j]  (i <= j)
         * */
        double[][] subProblems = new double[keys.length][keys.length];

        //base case
        for (int i = 0; i < keys.length; ++ i) {
            subProblems[i][i] = frequencies[i];
        }
        int maxSteps = keys.length - 1;
        for (int step = 1; step <= maxSteps; ++ step) {
            int end = keys.length - 1 - step;
            for (int i = 0; i <= end; ++ i) {
                subProblems[i][i + step] = frequencySum[i][i + step] + getMinOfSubProblems(i, i + step, subProblems);
            }
        }
        Result result = new Result();
        result.subProblems = subProblems;
        result.minAverageSearchTime = subProblems[0][keys.length - 1];
        return result;
    }
}
