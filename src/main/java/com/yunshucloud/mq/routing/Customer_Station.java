package com.yunshucloud.mq.routing;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
// 站内信消费者
public class Customer_Station {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("121.43.236.238");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");// 默认虚拟机
        //2.创建连接
        Connection conn = connectionFactory.newConnection();
        //3.建立信道
        Channel channel = conn.createChannel();
        // 4.监听队列
        channel.basicConsume("SEND_STATION2", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("发送站内信："+message);
            }
        });
    }
}
