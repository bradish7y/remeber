package com.xzm.spring;

import com.xzm.spring.bean.Book;
import com.xzm.spring.configuration.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Bradish7Y on 15/11/4.
 */
public class AppMainWithJavaConfig {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        Book book = ctx.getBean(Book.class);

    }
}
