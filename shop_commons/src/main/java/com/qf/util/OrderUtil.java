package com.qf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * linzebin
 * 时间2019/7/26 20:07
 */
@Controller
public class OrderUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 生成订单号
     *
     * 1、全局唯一
     * 2、不能有敏感的业务信息
     * 3、抗并发
     * 4、不宜过长（10~15）
     *
     * uuid
     *
     * 当前时间（年月日）
     * 用户的后4位（不足4位的补0）
     * 流水号1~n位
     *
     * 19072212891
     *
     * redis set number 1  incr number -> 2
     *
     *生成订单号
     * @return
     */
    public String createOrderId(Integer uid){
        StringBuffer stringBuffer=new StringBuffer("");
        //拼接当前时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        stringBuffer.append(sdf.format(new Date()));
        //截取用户id后四位 不足4位补0
        stringBuffer.append(getUid(uid));
        //拼接一个流水号
        String orderNumber=stringRedisTemplate.opsForValue().get("order_number");
        if (orderNumber==null){
            stringRedisTemplate.opsForValue().set("order_number","0");
        }
        //获得自增的流水号  自定义对increment对应的key进行自增 自增后返回
        Long oderNumber = stringRedisTemplate.opsForValue().increment("order_number");
        stringBuffer.append(oderNumber);
        return stringBuffer.toString();
    }
    //获取用户的后四位
    public static String getUid(Integer uid){
        StringBuffer stringBuffer=new StringBuffer();
        String uidStr=uid+"";
        if(uidStr.length()<4){
        //不足4补0
            for (int i=0;i<(4-uidStr.length());i++){
                stringBuffer.append("0");
            }
            stringBuffer.append(uidStr);
        }else{
            //大于4位 截取
            stringBuffer.append(uidStr.substring(uidStr.length()-4));
        }
        return stringBuffer.toString();
    }
    //通过订单号获得用户id
    public  Integer parseOrderId(String orderid){
        String uid=orderid.substring(6,10);
        return Integer.parseInt(uid);
    }
}
