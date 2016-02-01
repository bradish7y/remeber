package com.xzm.java.algorithm;

/**
 * Created by Bradish7Y on 15/10/21.
 * 右端找比基数小的，左端找比基数大的，大小互换，直到i<j，
 */
public class QuickSort {


    public void quickSort(int[] num, int left, int right) {

        if (left < right) {
            int initLeft = left;
            int initRight = right;
            int pivot = num[left];

            while (left < right) {
                while (pivot <= num[right] && left < right) {
                    right--;
                }
                num[left] = num[right];
                while (pivot >= num[left] && left < right) {
                    left++;
                }
                num[right] = num[left];

            }

            num[left] = pivot;


            quickSort(num, initLeft, left - 1);//left
            quickSort(num, left + 1, initRight);//right
        }

    }

    public static void main(String[] args) {
        int[] num = {7, 2, 2, 1};
        QuickSort qs = new QuickSort();
        qs.quickSort(num, 0, num.length - 1);

        for (int i : num) {
            System.out.print(i + ",");
        }
        System.out.println();
    }

}
