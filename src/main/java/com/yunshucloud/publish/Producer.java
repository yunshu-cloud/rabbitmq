package com.yunshucloud.publish;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        /**
         * 参数1：交换机名
         * 参数2：交换机类型
         * 参数3：交换机持久化
         */
        channel.exchangeDeclare("exchange_fanout", BuiltinExchangeType.FANOUT,true);
        // 5.创建队列
        channel.queueDeclare("SEND_MAIL",true,false,false,null);
        channel.queueDeclare("SEND_MESSAGE",true,false,false,null);
        channel.queueDeclare("SEND_STATION",true,false,false,null);
        // 6.交换机绑定队列
        /**
         * 参数1：队列名
         * 参数2：交换机名
         * 参数3：路由关键字，发布订阅模式写""即可
         */
        channel.queueBind("SEND_MAIL","exchange_fanout","");
        channel.queueBind("SEND_MESSAGE","exchange_fanout","");
        channel.queueBind("SEND_STATION","exchange_fanout","");
        // 7.发送消息
        for (int i = 1; i <= 10 ; i++) {
            channel.basicPublish("exchange_fanout","",null,
                    ("你好，尊敬的用户，秒杀商品开抢了！"+i).getBytes(StandardCharsets.UTF_8));
        }
        // 8.关闭资源
        channel.close();
        connection.close();
    }
}
