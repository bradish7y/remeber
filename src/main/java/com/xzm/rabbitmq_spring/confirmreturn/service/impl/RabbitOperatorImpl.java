package com.xzm.rabbitmq_spring.confirmreturn.service.impl;

import com.xzm.rabbitmq_spring.confirmreturn.service.RabbitOperator;
import com.xzm.rabbitmq_spring.domain.Person;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Bradish7Y on 16/1/4.
 */
@Service("rabbitOperator")
@Transactional(rollbackFor = Exception.class)
public class RabbitOperatorImpl implements RabbitOperator {

    @Resource
    private RabbitTemplate template;

    @Override
    public void update() throws Exception {
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("has received ack message");
            }
        });

        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("an error occurs....");
            }
        });

        SimpleResourceHolder.bind(template.getConnectionFactory(), "f1");
        template.convertAndSend("monkey2", new Person("ronaldo", "1"));
        SimpleResourceHolder.unbind(template.getConnectionFactory());
    }
}
