package dp;

import java.util.Deque;
import java.util.LinkedList;

public class Knapsack {

    public static class Result {
        public int totalValue;
        public int[] itemSelected;
        public int[][] subProblems;
    }

    public Result solveKnapsack(final int[] sizes, final int[] values, final int capacity) {
        if (null == sizes || sizes.length == 0 || null == values || values.length == 0 || sizes.length != values.length || capacity <= 0) {
            return null;
        }
        int[][] subProblems = new int[capacity + 1][sizes.length + 1];
        //base case
        for (int i = 0; i <= capacity; ++ i) {
            subProblems[i][0] = 0;
        }
        for (int j = 0; j <= sizes.length; ++ j) {
            subProblems[0][j] = 0;
        }
        //systemically solve all sub problems
        for (int i = 1; i <= capacity; ++ i) {
            for (int j = 1; j <= sizes.length; ++ j) {
                int case1Value = 0;
                int case2Value = 0;
                if (i >= sizes[j - 1]) {
                    case1Value = subProblems[i - sizes[j - 1]][j - 1] + values[j - 1];
                }
                case2Value = subProblems[i][j - 1];
                subProblems[i][j] = Math.max(case1Value, case2Value);
            }
        }
        //reconstruct
        return reconstructSolution(sizes, values, capacity, subProblems);
    }

    private Result reconstructSolution(final int[] sizes, final int[] values, final int capacity, final int[][] subProblems) {
        int i = capacity;
        int j = sizes.length;
        Deque<Integer> selectedItems = new LinkedList<>();
        while (i > 0 && j > 0) {
            int case1Value = -1;
            int case2Value = -1;
            if (i >= sizes[j - 1]) {
                case1Value = subProblems[i - sizes[j - 1]][j - 1] + values[j - 1];
            }
            case2Value = subProblems[i][j - 1];
            if (subProblems[i][j] == case1Value) {
                selectedItems.addFirst(j - 1);
                i -= sizes[j - 1];
                j -= 1;
            }
            else {
                j -= 1;
            }
        }
        Result result = new Result();
        result.totalValue = subProblems[capacity][sizes.length];
        result.itemSelected = new int[selectedItems.size()];
        for (int index = 0; index < result.itemSelected.length; ++ index) {
            result.itemSelected[index] = selectedItems.removeFirst();
        }
        result.subProblems = subProblems;
        return  result;
    }
}
