package com.qf.demo2;

import Util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * linzebin
 * 时间2019/7/13 14:35
 */
public class demo {
    public static void main(String[] args) throws IOException {
        //获得rabbitmq的连接对象
        Connection connection = ConnectionUtil.getConnection();
        //获得操作对象
        Channel channel = connection.createChannel();
        //创建交换机
        channel.exchangeDeclare("myexchange","fanout");
        //给交换机发送消息
        String content="Hellow Rabbitmq";
        channel.basicPublish("myexchange","",null,content.getBytes());
        connection.close();
    }
}
