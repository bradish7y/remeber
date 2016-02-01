package com.xzm.spring.aop.service.impl;

import com.xzm.spring.aop.service.Apology;
import org.springframework.stereotype.Component;

/**
 * Created by Bradish7Y on 15/7/27.
 */
@Component
public class ApologyImpl implements Apology {
    @Override
    public void saySorry(String toWho) {
        System.out.println("aop introduce:sorry to " + toWho);
    }
}
