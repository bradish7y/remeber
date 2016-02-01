package com.xzm.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * Created by Bradish7Y on 15/12/25.
 */
public class ProviderFanout {
    public static final String QUEUE_NAME = "farm-00-";
    public static final String EXCHANGE_NAME = "farm-ex";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("192.168.1.83");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean durable = false;

            // 分发到每个consumer
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // 绑定到指定queue
            channel.queueDeclare(QUEUE_NAME +'1', durable, false, false, null) ;
            channel.queueBind(QUEUE_NAME+'1', EXCHANGE_NAME, "") ;

            channel.queueDeclare(QUEUE_NAME+'2', durable, false, false, null) ;
            channel.queueBind(QUEUE_NAME+'2', EXCHANGE_NAME, "") ;

            // 发布的时候也要设置
            //channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, "hello".getBytes());
            channel.basicPublish(EXCHANGE_NAME, "", durable ? MessageProperties.PERSISTENT_TEXT_PLAIN : null, "hello".getBytes());

            System.in.read();
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                channel.close();
                connection.close();

            } catch (IOException e) {

            }
        }
    }
}
