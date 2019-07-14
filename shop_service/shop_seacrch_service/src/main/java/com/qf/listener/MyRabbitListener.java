package com.qf.listener;

import com.qf.Service.ISearchService;
import com.qf.entity.Goods;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * linzebin
 * 时间2019/7/13 16:00
 */
//消费者  监听通道
@Component
public class MyRabbitListener {
        @Autowired
        private ISearchService searchService;
        @RabbitListener(queues = "seacrch_queue")
        public void magHander(Goods goods){
            System.out.println("消费者执行了~~~~~~~~~~~~~~~~~~~~~~~"+goods);
            //同步到索引库
            searchService.addGoods(goods);
    }
}
