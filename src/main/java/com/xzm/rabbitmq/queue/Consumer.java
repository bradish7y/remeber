package com.xzm.rabbitmq.queue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/12/25.
 */
public class Consumer {
    public static final String QUEUE_NAME = "farm";

    public static void main(String[] args) throws Exception {
        // 1.连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/vhost1");
        factory.setHost("192.168.1.83");


        Connection connection = null;

        final Channel channel;
        // 2.创建连接
        connection = factory.newConnection();

        // 3.创建通道
        channel = connection.createChannel();

        //保证同一时间在没有接收到ack之前不发送消息
        //channel.basicQos(2);
        // 4.consumer(本地consumer队列)
        //QueueingConsumer consumer = new QueueingConsumer(channel);
        //channel.basicConsume(QUEUE_NAME, false, consumer);
        // 5.获取mq数据
        //QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        //System.out.println(new String(delivery.getBody()));

        // 4.Consumer 接口的默认实现
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received " + envelope.getRoutingKey() + " :" + message + "'");

                    /*try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                //channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 5.开始接收mq消息,开启监听器生
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);

        System.in.read();
        channel.close();
        connection.close();

    }
}
