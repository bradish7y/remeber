package com.xzm.spring.aop;

import com.xzm.spring.aop.service.Sleep;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Bradish7Y on 15/7/26.
 */
public class TestAop {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        Sleep s = (Sleep) ctx.getBean("sleep");
        s.sleep("ronaldo");
        //s.wake();
    }
}
