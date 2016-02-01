package com.xzm.pattern.decorator;

/**
 * Created by Bradish7Y on 15/7/7.
 */
public class Test {
    public static void main(String[] args) {
        AudiCar audi = new AudiCar() ;

        ColorCarDecorator color = new ColorCarDecorator(new EngineCarDecorator(audi)) ;
        System.out.println("color.getDesc() = " + color.getDesc());
        System.out.println("color.getPrice() = " + color.getPrice());
    }
}
