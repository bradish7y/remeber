package com.xzm.rabbitmq.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Bradish7Y on 15/12/25.
 */
public class ConsumerFanout {
    public static final String QUEUE_NAME = "farm2";
    public static final String EXCHANGE_NAME = "farm-ex" ;


    public static void main(String[] args) throws Exception {
        // 1.连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("192.168.1.83");


        Connection connection = null;

        final Channel channel;
        // 2.创建连接
        connection = factory.newConnection();

        // 3.创建通道
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout") ;

        String qName = channel.queueDeclare().getQueue() ;
        qName = QUEUE_NAME ;

        // 随机队列
        channel.queueBind(qName, EXCHANGE_NAME, "") ;
        //channel.basicQos(1);

        // 4.Consumer 接口的默认实现
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");

                    /*try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 5.开始接收mq消息,开启监听器生
        channel.basicConsume(qName, false, defaultConsumer);

        System.in.read();
        channel.close();
        connection.close();
;
    }
}
