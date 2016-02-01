package com.xzm.java.enumtest;

import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * Created by Bradish7Y on 15/5/22.
 */
public class TestMain {
    public static void main(String[] args) {
        double ret = Operation.PLUS.eval(3, 4);
        System.out.println("ret = " + ret);

        String s = "a" ;
        if(s!=null && !s.isEmpty())
            System.out.println(1);
        UnpooledByteBufAllocator.DEFAULT.directBuffer();
        Unpooled.directBuffer() ;

    }
}
