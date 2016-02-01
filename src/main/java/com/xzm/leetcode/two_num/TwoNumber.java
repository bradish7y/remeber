package com.xzm.leetcode.two_num;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/6/30.
 * <p/>
 * Given an array of integers, find two numbers such that they add up to a specific target number.
 * <p/>
 * The function twoSum should return indices of the two numbers such that they add up to the target,
 * where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
 * <p/>
 * You may assume that each input would have exactly one solution.
 * <p/>
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=1, index2=2
 */
public class TwoNumber {
    public static void main(String[] args) {
        TwoNumber t = new TwoNumber();

        Stopwatch s = Stopwatch.createStarted();
        int[] resp = t.twoNum(t.assembleArray(2, 7, 11, 15), 9);
        System.out.println("cost time:" + s.elapsed(TimeUnit.MICROSECONDS) + "us");

        System.out.println("index1:" + resp[0] + ",index2:" + resp[1]);
    }

    public int[] assembleArray(int... args) {

        int[] ret = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            ret[i] = args[i];
        }

        return ret;
    }

    public int[] twoNum(int[] nums, int target) {

        int[] ret = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int f = nums[i];
            if (f > target) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] > target) {
                    continue;
                }
                if (f + nums[j] == target) {
                    ret[0] = i + 1;
                    ret[1] = j + 1;
                    return ret ;
                }
            }
        }
        return ret;
    }
}
