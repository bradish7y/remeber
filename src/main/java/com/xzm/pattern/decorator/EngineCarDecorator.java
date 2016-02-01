package com.xzm.pattern.decorator;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public class EngineCarDecorator extends CarDecorator {
    public EngineCarDecorator(Car car) {
        super(car);
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 50;
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " Engine";
    }
}
