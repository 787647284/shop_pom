package com.qf.demo1;

import Util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * linzebin
 * 时间2019/7/13 14:24
 */
public class Consumer2 {
    private static ExecutorService executorService= Executors.newFixedThreadPool(20);
    public static void main(String[] args) throws IOException {
        //连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();
        //连接channel
        Channel channel = connection.createChannel();
        channel.queueDeclare("myqueue",false,false,false,null);
        //监听队列
        channel.basicConsume("myqueue",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                executorService.submit(() ->{
                    try {
                        System.out.println("接受的消息为"+new String(body,"UTF-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });


    }
}
