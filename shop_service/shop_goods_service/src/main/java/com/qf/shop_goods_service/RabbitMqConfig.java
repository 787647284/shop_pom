package com.qf.shop_goods_service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * linzebin
 * 时间2019/7/13 15:35
 */
@Configuration
public class RabbitMqConfig {
        //声明队列1
    @Bean(name = "squeue")
    public Queue getQuere1(){
        return new Queue("seacrch_queue");
    }
    //创建队列2
    @Bean(name = "iqueue")
    public Queue getQueue2(){
        return new Queue("item_queue");
    }
    //创建交换机
    @Bean(name = "gexchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("goods_exchange");
    }
    //进行交换机绑定
    @Bean
    public Binding getBinding1(Queue squeue,FanoutExchange gexchange){
        System.out.println("squeue绑定"+squeue);
        return BindingBuilder.bind(squeue).to(gexchange);
    }
    //进行交换机绑定
    @Bean
    public Binding getBinding2(Queue iqueue,FanoutExchange gexchange){
        System.out.println("iqueue绑定"+iqueue.toString());
        return BindingBuilder.bind(iqueue).to(gexchange);
    }

}
