package com.xzm.java.algorithm;

/**
 * Created by Bradish7Y on 15/10/21.
 * 右端找比基数小的，左端找比基数大的，大小互换，直到i<j，
 */
public class QuickSort2 {
    int[] num = {7, 3, 5, 8, 19, 34, 56, 2, 2};

    public void quickSort(int left, int right) {

        if (left >= right) {
            return;
        }
        int initLeft = left;
        int initRight = right;
        int pivot = num[left];
        int pivotPOs = left;

        while (left < right) {
            while (left < right) {
                if (pivot > num[right]) {
                    num[pivotPOs] = num[right];
                    num[right] = pivot;
                    pivotPOs = right;
                    break;
                }
                right--;
            }

            while (left < right) {
                if (pivot < num[left]) {
                    num[pivotPOs] = num[left];
                    num[left] = pivot;
                    pivotPOs = left;
                    break;
                }
                left++;
            }

        }
        quickSort(initLeft, left - 1);//right
        quickSort(left + 1, initRight);//left
    }

    public static void main(String[] args) {
        QuickSort2 qs = new QuickSort2();
        qs.quickSort(0, qs.num.length - 1);

        for (int i : qs.num) {
            System.out.print(i + ",");
        }
        System.out.println();
    }
}
