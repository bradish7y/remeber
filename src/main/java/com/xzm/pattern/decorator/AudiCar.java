package com.xzm.pattern.decorator;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public class AudiCar extends Car {
    @Override
    public int getPrice() {
        return 998;
    }

    @Override
    public String getDesc() {
        return "AudiCar" ;
    }
}
