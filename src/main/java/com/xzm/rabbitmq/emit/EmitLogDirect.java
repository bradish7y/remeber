package com.xzm.rabbitmq.emit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by Bradish7Y on 15/12/28.
 */
public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String QUEUE_NAME = "zoo";

    public static void main(String[] argv)
            throws java.io.IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.83");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String severity = "info";

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, severity) ;

        String message = "hello";

        channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
        System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

        channel.close();
        connection.close();
    }
}
