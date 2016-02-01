package com.xzm.spring.aop.service.impl;

import com.xzm.spring.aop.service.Greeting;
import org.springframework.stereotype.Component;

/**
 * Created by Bradish7Y on 15/7/27.
 */

@Component
public class GreetingImpl implements Greeting {
    @Override
    public void sayHello(String toWho) {
        System.out.println("aop introduce:hello " + toWho);
    }
}
