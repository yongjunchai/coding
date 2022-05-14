public class OptBST {

    static public class Result {
        public double[][] subProblems;
        public double weightedSearchTime;
    }

    private boolean isEmpty(int[] values) {
        return null == values || 0 == values.length;
    }

    private boolean isEmpty(double[] values) {
        return null == values || 0 == values.length;
    }

    private double calMinSubProblems(final int start, final int end, double[][] subProblems) {
        if (start == 1 && end == 3) {
            System.out.println();
        }
        double minWeightedSearch = Double.MAX_VALUE;
        for(int root = start; root <= end; ++ root) {
            double subProblemSum = subProblems[start][root - 1];
            if (root < end) {
                subProblemSum += subProblems[root + 1][end];
            }
            minWeightedSearch = Math.min(minWeightedSearch, subProblemSum);
        }
        return minWeightedSearch;
    }

    private double frequencySum(final int start, final int end, final double[] frequencies) {
        if (start == 1 && end == 3) {
            System.out.println();
        }

        double frequencySum = 0;
        for(int i = start - 1; i < end; ++ i) {
            frequencySum += frequencies[i];
        }
        return frequencySum;
    }

    /**
     * @param keys sored list
     * */
    public Result getOptBST(final int[] keys, final double[] frequencies) {
        if (isEmpty(keys) || isEmpty(frequencies) || keys.length != frequencies.length) {
            return null;
        }
        double[][] subProblems = new double[keys.length + 1][keys.length + 1];
        //key started from index 1, biggest step is (n - 1)
        //the biggest sub problem is: [1][ 1 + (n - 1)] = [1][n]

        //base case
        for(int i = 1; i <= keys.length; ++ i) {
            subProblems[i][i - 1] = 0;
            subProblems[i][i] = frequencies[i - 1];
        }

        //systemically solve the sub problems
        int maxStep = keys.length - 1;
        int lastKeyIndex = keys.length;
        for(int step = 1; step <= maxStep; ++ step) {
            int endKeyIndex = lastKeyIndex - step;
            for(int i = 1; i <= endKeyIndex; ++ i) {
                double minSubProblemsSum = calMinSubProblems(i, i + step, subProblems);
                double fSum = frequencySum(i, i + step, frequencies);
                subProblems[i][i + step] =  (minSubProblemsSum + fSum);
            }
        }
        Result result = new Result();
        result.weightedSearchTime = subProblems[1][keys.length];
        result.subProblems = subProblems;
        return result;
    }
}
