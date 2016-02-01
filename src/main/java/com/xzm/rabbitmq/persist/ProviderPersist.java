package com.xzm.rabbitmq.persist;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * Created by Bradish7Y on 15/12/25.
 */
public class ProviderPersist {
    public static final String QUEUE_NAME = "farm";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/vhost1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("192.168.1.83");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean durable = true ;
            // 持久化
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            // 发布的时候也要设置
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, "hello".getBytes());

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
