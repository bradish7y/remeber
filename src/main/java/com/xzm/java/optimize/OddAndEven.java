package com.xzm.java.optimize;

/**
 * Created by Bradish7Y on 15/7/1.
 *
 * 判断奇偶
 */
public class OddAndEven {
    public static void main(String[] args) {
        //判断奇数
        System.out.println(-1 % 2 == 1);// 不推荐
        System.out.println(-1 % 2 != 0);// 推荐

        //高效方式
        System.out.println((-1 & 1) == 1);

        // 偶数
        System.out.println((-18 & 1) != 1);

    }
}
