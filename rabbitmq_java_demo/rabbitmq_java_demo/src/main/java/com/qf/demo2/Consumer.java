package com.qf.demo2;

import Util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * linzebin
 * 时间2019/7/13 13:34
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        //连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();
        //连接channel
        Channel channel = connection.createChannel();
        channel.queueDeclare("myqueue1",false,false,false,null);
        //绑定交换机
        channel.queueBind("myqueue1","myexchange","");
        //监听队列
        channel.basicConsume("myqueue1",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1获得消息：" + new String(body));
            }
        });
    }
}
