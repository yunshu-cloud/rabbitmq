package com.yunshucloud.mq.work;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
public class Producer {
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
        // 4.创建队列，持久化队列
        channel.queueDeclare("work_queue",true,false,false,null);
        // 5.发送大量消息，参数3表示该消息为持久化消息，即除了保存到内存还会保存到磁盘中
        for (int i = 1; i <= 100; i++) {
            channel.basicPublish("","work_queue", MessageProperties.PERSISTENT_TEXT_PLAIN,
                    ("你好，这是今天的第"+i+"条消息").getBytes());
        }
        // 6.关闭资源
        channel.close();
        connection.close();
    }
}
