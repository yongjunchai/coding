package leetcode;

public class MedianOfTwoSortedArray {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] numsShort = nums1.length < nums2.length ? nums1 : nums2;
        int[] numsLong = nums1.length < nums2.length ? nums2 : nums1;
        int total = nums1.length + nums2.length;
        int half = total / 2;
        int l = 0;
        int r = numsShort.length - 1;
        while (true) {
            int iShort = (r + l) == -1 ? -1 : (r + l) / 2;
            int iLong = half - (iShort + 1) - 1;
            int shortLeft = iShort >= 0 ? numsShort[iShort] : Integer.MIN_VALUE;
            int shortRight = (iShort + 1) < numsShort.length ? numsShort[iShort + 1] : Integer.MAX_VALUE;
            int longLeft = iLong >= 0 ? numsLong[iLong] : Integer.MIN_VALUE;
            int longRight = (iLong + 1) < numsLong.length ? numsLong[iLong + 1] : Integer.MAX_VALUE;

            if (shortLeft <= longRight && longLeft <= shortRight) {
                if (total % 2 == 0) {
                    return (Math.max(shortLeft , longLeft) + Math.min(shortRight, longRight)) / 2.0;
                }
                else {
                    return Math.min(shortRight, longRight);
                }
            }
            else if (shortLeft > longRight) {
                r = iShort - 1;
            }
            else {
                l = iShort + 1;
            }
        }
    }

}
