package com.xzm.rabbitmq_spring.base;

import com.xzm.rabbitmq_spring.domain.Person;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/12/31.
 */
public class PandaMessageListener implements MessageListener {

    @Resource
    private MessageConverter converter;

    @Override
    public void onMessage(Message message) {
        //converter = new Jackson2JsonMessageConverter() ;
        Person p = (Person) converter.fromMessage(message);
        System.out.println("p = " + p);
    }

}
