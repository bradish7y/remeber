package com.xzm.java.optimize;

import java.math.BigDecimal;

/**
 * Created by Bradish7Y on 15/7/1.
 *
 * 精度计算
 * (2.0 - 1.1) =
 * unexpected: 0.8999999999999999
 * expected: 1.0
 */
public class PrecisionCal {
    public static void main(String[] args) {
        //unexpect: 0.8999999999999999
        System.out.println(2.0 - 1.1);

        //solution
        BigDecimal b1 = new BigDecimal("2.0") ;
        BigDecimal b2 = new BigDecimal("1.1") ;
        System.out.println(b1.subtract(b2));//expected

    }
}
