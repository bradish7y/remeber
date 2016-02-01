package com.xzm.leetcode.reverse_integer;

/**
 * Created by Bradish7Y on 15/6/30.
 */
public class ReverseInteger {
    public static void main(String[] args) {
        ReverseInteger r = new ReverseInteger();
        System.out.println(r.reverse(321));
        System.out.println(11 & 0xff);

        System.out.println(Integer.toBinaryString(321) ) ;
    }

    public int reverse(int x) {
        int resp = 0;
        for (; ; ) {
            resp = resp << 8;
            resp |= x & 0xff;
            x = x >> 8;
            if (x == 0) {
                break;
            }
        }

        return resp;
    }
}
