package com.xzm.pattern.decorator;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public abstract class CarDecorator extends Car {
    private Car car ;

    public CarDecorator(Car car) {
        this.car = car;
    }

    @Override
    public int getPrice() {
        return car.getPrice();
    }

    @Override
    public String getDesc() {
        return car.getDesc();
    }
}
