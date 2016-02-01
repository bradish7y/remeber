package com.xzm.java.generic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bradish7Y on 15/6/15.
 */
public class TestMain {
    public void upperBound(List<? extends Date> list, Timestamp date)
    {
        Date now = list.get(0);
        System.out.println("now==>" + now);
        //list.add(date); //这句话无法编译
        list.add(null);//这句可以编译，因为null没有类型信息
    }
    public static void main(String[] args) {
        Animal al = new Dog() ;
        Dog b = (Dog) al ;
        b.eat();

        List<? extends Date> list = new ArrayList<Timestamp>() ;

    }
}
