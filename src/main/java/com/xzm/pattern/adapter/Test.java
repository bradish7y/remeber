package com.xzm.pattern.adapter;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public class Test {
    public static void main (String[] args) {
        Fly f = new Plane () ;
        f.fly ();

        // 为了能统一调用
        f = new HorseAdapter(new Horse()) ;
        f.fly();
    }
}
