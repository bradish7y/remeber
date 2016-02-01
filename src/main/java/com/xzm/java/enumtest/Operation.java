package com.xzm.java.enumtest;

/**
 * 枚举实现加减乘除
 * <p/>
 * Created by Bradish7Y on 15/5/22.
 */
public enum Operation {

    PLUS {
        double eval(double x, double y) {
            return x + y;
        }
    },
    MINUS {
        double eval(double x, double y) {
            return x - y;
        }
    },
    TIMES {
        double eval(double x, double y) {
            return x * y;
        }
    },
    DIVIDE {
        double eval(double x, double y) {
            return x / y;
        }
    };

    abstract double eval(double x, double y);

}

