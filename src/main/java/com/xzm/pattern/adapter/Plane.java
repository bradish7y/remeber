package com.xzm.pattern.adapter;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public class Plane implements  Fly {
    @Override
    public void fly () {
        System.out.println ("Plane can fly");
    }
}
