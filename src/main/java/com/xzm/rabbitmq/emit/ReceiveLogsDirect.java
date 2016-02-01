package com.xzm.rabbitmq.emit;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Bradish7Y on 15/12/28.
 */
public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String QUEUE_NAME = "zoo";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.83");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
        queueName = QUEUE_NAME;

        /*if (argv.length < 1){
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }*/

        String[] args = {"info", "warning", "error"};
        for (String severity : args) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);

        System.in.read();
    }
}
