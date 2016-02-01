package com.xzm.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by Bradish7Y on 15/7/26.
 */
public class Behavior {
    public void before() {
        System.out.println("before sleep");
    }

    public void after() {

        System.out.println("after sleep");
    }

    public void around(ProceedingJoinPoint point, String param) {
        try {
            System.out.println("before around:" + param);
            System.out.println(point.getThis().toString());
            point.proceed();
            System.out.println("after around");

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

}
