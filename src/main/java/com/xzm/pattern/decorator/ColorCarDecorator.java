package com.xzm.pattern.decorator;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public class ColorCarDecorator extends CarDecorator {
    public ColorCarDecorator(Car car) {
        super(car);
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 100;
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " Color";
    }
}
