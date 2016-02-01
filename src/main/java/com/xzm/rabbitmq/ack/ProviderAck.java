package com.xzm.rabbitmq.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by Bradish7Y on 15/12/25.
 */
public class ProviderAck {
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
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicPublish("", QUEUE_NAME, null, "hello".getBytes());

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
