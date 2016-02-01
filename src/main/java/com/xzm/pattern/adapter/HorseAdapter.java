package com.xzm.pattern.adapter;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public class HorseAdapter implements Fly {
    private Horse horse ;
    public HorseAdapter(Horse horse) {
        this.horse = horse ;
    }

    @Override
    public void fly() {
        horse.notFly();
    }
}
