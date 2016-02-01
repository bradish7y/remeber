package com.xzm.spring.aop.introduce;

import com.xzm.spring.aop.service.Apology;
import com.xzm.spring.aop.service.Greeting;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Bradish7Y on 15/7/27.
 */
public class TestIntro {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Greeting greet  = (Greeting)ctx.getBean("greetingImpl") ;
        greet.sayHello("ronaldo");

        Apology apology = (Apology) greet ;
        apology.saySorry("beckham");

    }
}
