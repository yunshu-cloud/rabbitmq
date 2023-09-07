package com.yunshucloud.mq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 生产者
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
        // 4.创建队列，如果队列已存在，则使用该队列
        /**
         * 参数1：队列名
         * 参数2：是否持久化，true表示MQ重启后队列还在。
         * 参数3：是否私有化，false表示所有消费者都可以访问，true表示只有第一次拥有它的消费者才能访问
         * 参数4：是否自动删除，true表示不再使用队列时自动删除队列
         * 参数5：其他额外参数
         */
        channel.queueDeclare("simple_queue",false,false,false,null);
        // 5.发送消息
        String message = "hello!rabbitmq!";
        /**
         * 参数1：交换机名，""表示默认交换机
         * 参数2：路由键，简单模式就是队列名
         * 参数3：其他额外参数
         * 参数4：要传递的消息字节数组
         */
        channel.basicPublish("","simple_queue",null,message.getBytes());
        // 6.关闭信道和连接
        channel.close();
        connection.close();
        System.out.println("===发送成功===");
    }
}
