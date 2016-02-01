package com.xzm.rabbitmq_spring.autodelete;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Bradish7Y on 16/1/5.
 */
public class Consumer {
    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("rabbit/autodelete/consumer.xml") ;

    }
}
