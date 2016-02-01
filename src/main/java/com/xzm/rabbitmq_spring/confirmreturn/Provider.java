package com.xzm.rabbitmq_spring.confirmreturn;

import com.xzm.rabbitmq_spring.confirmreturn.service.RabbitOperator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Bradish7Y on 16/1/5.
 */
public class Provider {

    /**
     * 测试简单的发布与订阅
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("rabbit/confirm/provider.xml");
        RabbitOperator operator = (RabbitOperator) ctx.getBean("rabbitOperator");
        try {
            operator.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
