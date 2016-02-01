package com.xzm.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Bradish7Y on 16/1/3.
 */
@Component
public class RabbitService {

    @RabbitListener(queues = "ha.zoo2")
    public void process(String data){
        System.out.println("data = " + data);
    }
}
