package com.xzm.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * Created by Bradish7Y on 15/12/25.
 * 测试不通的routeKey发往不通的queue, 如果没有建立绑定则丢弃, 注意:需要先启动consumer
 */
public class ProviderDirect {
    public static final String QUEUE_NAME = "ha.zoo2";
    public static final String EXCHANGE_NAME = "zoo-direct";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setPort(5672);
        factory.setHost("192.168.1.200");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean durable = false;
            String routeKey = "panda";

            // 分发到每个consumer
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", durable);

            // 声明队列并且绑定到指定queue
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routeKey);

            for (int i = 0; i < 1000; i++) {

                channel.basicPublish(EXCHANGE_NAME, "panda", durable ? MessageProperties.PERSISTENT_TEXT_PLAIN : null, "hello".getBytes());
            }

            System.out.println("[x] sent");
            System.in.read() ;
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
