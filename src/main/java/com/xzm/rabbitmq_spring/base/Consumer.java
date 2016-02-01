package com.xzm.rabbitmq_spring.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.net.URL;

/**
 * Created by Bradish7Y on 16/1/5.
 */
public class Consumer {
    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("rabbit/base/consumer.xml") ;

    }
}
