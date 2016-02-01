package com.xzm.spring;

import com.xzm.spring.bean.Book;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by Bradish7Y on 15/11/4.
 */
public class AppMainWithXml {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


    }
}
