package com.xzm.rabbitmq_spring.transaction.service.impl;

import com.xzm.rabbitmq_spring.domain.Person;
import com.xzm.rabbitmq_spring.transaction.service.RabbitOperator;
import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
        SimpleResourceHolder.bind(template.getConnectionFactory(), "f1");
        template.convertAndSend("monkey", new Person("ronaldo", "1"));
        SimpleResourceHolder.unbind(template.getConnectionFactory());
        throw new RuntimeException("error......") ;

    }
}
