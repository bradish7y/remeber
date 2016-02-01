package com.xzm.java.generic.javaproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Bradish7Y on 15/6/19.
 */
public class HelloWorldHandler implements InvocationHandler {
    private Object obj ;
    public HelloWorldHandler(Object obj) {
        this.obj = obj ;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obj, args);
    }
}
