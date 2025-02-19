package com.wenzhi.user_service.solution;

import java.util.Arrays;

public class Solution {
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        for (int i=0; i<k; i++) {
            
            if (i != k-1) {
                Arrays.sort(nums);
                System.out.println(Arrays.toString(nums));
                nums[0] *= multiplier;
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] nums = {2,1,3,5,6};
        int k = 5;
        int multiplier = 2;
        int[] result = new Solution().getFinalState(nums, k, multiplier);
        System.out.println(Arrays.toString(result));
    }
}
