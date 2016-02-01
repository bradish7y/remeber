package com.xzm.java.optimize;

import java.nio.ByteBuffer;

/**
 * Created by Bradish7Y on 15/7/1.
 */
public class IntOverflow {
    public static void main(String[] args) {
        // error transfer
        long s = 24 * 60 * 60 * 1000 * 1000;
        System.out.println(s);// unexpected

        // solution
        s = 24 * 60 * 60 * 1000 * 1000L;
        System.out.println(s);// expected

    }
}
