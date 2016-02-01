package com.xzm.java.generic.javaproxy;

/**
 * Created by Bradish7Y on 15/6/19.
 */
public class HelloWorldImpl implements HelloWorld {
    public void hello(String msg) {
        System.out.println("msg = " + msg);
    }
}
