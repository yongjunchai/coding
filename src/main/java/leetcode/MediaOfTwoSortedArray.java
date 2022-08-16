package leetcode;

public class MediaOfTwoSortedArray {

    private double numberAt(int[] nums, int start, int offset) {
        return nums[start + offset];
    }

    private double medianOf(int[] nums) {
        if (nums.length % 2 == 0) {
            return (double) ((nums[nums.length / 2] + nums[nums.length / 2 - 1])) / 2;
        }
        else {
            return nums[nums.length / 2];
        }
    }

    private double findOffsetOfSortedArrays(int[] nums1, int num1Start, int num1End, int[] nums2, int num2Start, int num2End, int offset) {
        int median1IndexOffset = (num1End - num1Start) / 2;
        int median2IndexOffset = (num2End - num2Start) / 2;
        if (median1IndexOffset > offset && median2IndexOffset > offset) {
            return findOffsetOfSortedArrays(nums1, num1Start, median1IndexOffset, nums2, num2Start, median2IndexOffset, offset);
        }
        if (median1IndexOffset <= offset) {
            if (nums1[median1IndexOffset] <= nums2[num2Start]) {
                if (num1Start + median1IndexOffset + 1 >= nums1.length) {
                    //run out of num1
                    return numberAt(nums2, num2Start, offset - (median1IndexOffset + 1));
                }
                else {
                    return findOffsetOfSortedArrays(nums1, median1IndexOffset + 1, num1End, nums2, num2Start, median2IndexOffset, offset);
                }
            }
            else {
            }

        }


    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (null == nums1 || nums1.length == 0) {
            return medianOf(nums2);
        }
        if (null == nums2 || nums2.length == 0) {
            return medianOf(nums1);
        }
        if ((nums1.length + nums2.length) % 2 == 0) {
            return (findOffsetOfSortedArrays(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, (nums1.length + nums2.length) / 2) +
                    findOffsetOfSortedArrays(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, (nums1.length + nums2.length) / 2 - 1));
        }
        else {
            return findOffsetOfSortedArrays(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, (nums1.length + nums2.length) / 2);
        }
    }

}
