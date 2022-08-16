package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        //<value, list of index>
        Map<Integer, List<Integer>> valIndicesMap = new HashMap<>();
        for (int i = 0; i < nums.length; ++ i) {
            List<Integer> indices = valIndicesMap.get(nums[i]);
            if (indices == null) {
                indices = new ArrayList<>();
                valIndicesMap.put(nums[i], indices);
            }
            indices.add(i);
        }
        for (int i = 0; i < nums.length; ++ i) {
            int expectedVal = target - nums[i];
            List<Integer> indices = valIndicesMap.get(expectedVal);
            if (null == indices) {
                continue;
            }
            for (Integer index : indices) {
                if (index == i) {
                    continue;
                }
                int[] vals = new int[2];
                vals[0] = i;
                vals[1] = index;
                return vals;
            }
        }
        throw new IllegalArgumentException("no solution found");
    }
}
