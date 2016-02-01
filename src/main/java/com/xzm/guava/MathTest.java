package com.xzm.guava;

import com.google.common.math.IntMath;

import java.math.RoundingMode;

/**
 * Created by Bradish7Y on 15/4/24.
 */
public class MathTest {
    public static void main(String[] args) {
        System.out.println(IntMath.divide(3, 5, RoundingMode.FLOOR)) ;
        System.out.println(IntMath.isPowerOfTwo(3)) ;
        System.out.println(IntMath.binomial(3, 2) );
        System.out.println(IntMath.mod(5, 2));
    }
}
