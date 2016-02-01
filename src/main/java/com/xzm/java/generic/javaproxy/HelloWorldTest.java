package com.xzm.java.generic.javaproxy;

import java.lang.reflect.Proxy;

/**
 * Created by Bradish7Y on 15/6/19.
 */
public class HelloWorldTest {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorldImpl();
        HelloWorldHandler handler = new HelloWorldHandler(helloWorld) ;

        HelloWorld  hello = (HelloWorld) Proxy.newProxyInstance(helloWorld.getClass().getClassLoader(),
                helloWorld.getClass().getInterfaces(), handler) ;

        hello.hello("123");

    }
}
