package Util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * linzebin
 * 时间2019/7/13 13:12
 */
public class ConnectionUtil {
        private static ConnectionFactory connectionFactory;
        static{
                connectionFactory=new ConnectionFactory();
                //rabbitmq Url
                connectionFactory.setHost("192.168.225.188");
                //rabbitmq 端口
                connectionFactory.setPort(5672);
                //rabbitmq后台的默认用户名
                connectionFactory.setUsername("guest");
                //默认名
                connectionFactory.setPassword("guest");
                // 虚拟主机（用于分组 避免接受到不同组的队列信息）
                connectionFactory.setVirtualHost("/");
        }
        public static Connection getConnection(){
                Connection connection=null;
                try {
                        connection=connectionFactory.newConnection();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (TimeoutException e) {
                        e.printStackTrace();
                }
                return connection;
        }
}
