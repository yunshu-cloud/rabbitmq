package com.yunshucloud.mq.routing;
import com.rabbitmq.client.BuiltinExchangeType;
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
        // 4.创建交换机
        channel.exchangeDeclare("exchange_routing", BuiltinExchangeType.DIRECT,true);
        // 5.创建队列
        channel.queueDeclare("SEND_MAIL2",true,false,false,null);
        channel.queueDeclare("SEND_MESSAGE2",true,false,false,null);
        channel.queueDeclare("SEND_STATION2",true,false,false,null);
        // 6.交换机绑定队列
        channel.queueBind("SEND_MAIL2","exchange_routing","import");
        channel.queueBind("SEND_MESSAGE2","exchange_routing","import");
        channel.queueBind("SEND_STATION2","exchange_routing","import");
        channel.queueBind("SEND_STATION2","exchange_routing","normal");
        // 7.发送消息
        channel.basicPublish("exchange_routing","import",null,
                "双十一大促活动".getBytes());
        channel.basicPublish("exchange_routing","normal",null,
                "小心促销活动".getBytes());
        // 8.关闭资源
        channel.close();
        connection.close();
    }
}

