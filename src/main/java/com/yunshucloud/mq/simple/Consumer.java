package com.yunshucloud.mq.simple;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
// 消费者
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("121.43.236.238");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        // 2.创建连接
        Connection connection = connectionFactory.newConnection();
        // 3.建立信道
        Channel channel = connection.createChannel();
        // 4.监听队列
        /**
         * 参数1：监听的队列名
         * 参数2：是否自动签收，如果设置为false，则需要手动确认消息已收到，否则MQ会一直发送消息
         * 参数3：Consumer的实现类，重写该类方法表示接受到消息后如何消费
         */
        channel.basicConsume("simple_queue",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("接受消息，消息为："+message);
            }
        });
    }
}

