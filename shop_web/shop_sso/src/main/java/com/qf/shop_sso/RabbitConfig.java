package com.qf.shop_sso;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * linzebin
 * 时间2019/7/18 20:34
 */
@Configuration
public class RabbitConfig {

    @Bean
public Queue getqueue(){
        return new Queue("email_queue");
    }
    @Bean
public FanoutExchange getfanoutExchangge(){
        return new FanoutExchange("email_exchange");
}
    @Bean
    public Binding getBindimg(Queue getqueue,FanoutExchange getfanoutExchangge){
            return BindingBuilder.bind(getqueue).to(getfanoutExchangge);
    }
}
