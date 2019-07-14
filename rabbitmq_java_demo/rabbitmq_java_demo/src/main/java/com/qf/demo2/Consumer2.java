package com.qf.demo2;

import Util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * linzebin
 * 时间2019/7/13 13:34
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException {
        //连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();
        //连接channel
        Channel channel = connection.createChannel();
        channel.queueDeclare("myqueue2",false,false,false,null);
        //绑定交换机
        channel.queueBind("myqueue2","myexchange","");
        //监听队列
        channel.basicConsume("myqueue2",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1获得消息：" + new String(body));
            }
        });
    }
}
