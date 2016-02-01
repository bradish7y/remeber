package com.xzm.spring.aop.service.impl;

import com.xzm.spring.aop.annotation.Must;
import com.xzm.spring.aop.service.Sleep;

/**
 * Created by Bradish7Y on 15/7/26.
 */
public class SleepImpl implements Sleep {

    @Override
    public void wake() {
        System.out.println("wanna to wake up!!!");

    }

    @Must
    @Override
    public void sleep(String who) {
        System.out.println(who + " wanna to sleep!!!");
    }
}
