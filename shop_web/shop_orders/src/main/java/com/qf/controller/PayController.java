package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.Service.IOrderService;
import com.qf.entity.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.apache.catalina.manager.host.Constants.CHARSET;

/**
 * linzebin
 * 时间2019/7/27 19:42
 */
@Controller
@RequestMapping("/pay")
public class PayController {
        @Reference
        IOrderService orderService;


    @RequestMapping("/alipay")
        public void  aliPay(String orderid, HttpServletResponse response) throws IOException {
            Order order = orderService.queryByOid(orderid);
            AlipayClient alipayClient = new DefaultAlipayClient(
                    "https://openapi.alipaydev.com/gateway.do",
                    "2016100900648755",
                    "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCJqo4omNh/4x2lEWAtQvqpB5HW8KdBxvay7/PAPIeTFSFnnNHKGWEwOe3wUCeZW/uA2zE/1LbgL8zC37yGGYdAJafzN5M7vLnkV91VZK8/RHjwiOjWG+1u3MOCoXYIYEdP52pDFxhr1qVXf9vSqmS7zS5l0NMyaFTIIdEv3gv6ByN7l6eXwTCBRingv/KLprF5K/WAUF2K0s5hRz8iCKX1imjcXzlJefBeUAzi1f9QlIBVtBKI0OUTFvTwVHV0QWodEKWYbwPIGmLEy6mnN8LImWkaCZrNLnaknok8jcgJw2H0yRjHZdZT0Nma4bHPWq5mcgyEGTrJWkVcZBpAkViLAgMBAAECggEAXoWykgFQNY71Xc8uYCVrAf/mjNQzoyA/ZuEGVem5lK2fgArWqJMAfiJoUuZWKiBT8UeO6yrcXdW0G+GPCewhR1QTmxV/jDaq1DPYDrfQ8/nJqC344HnQbpw5yUmfR2UD0cMs3N/Qrsp0QD3T6zN/cssQnmvhduzXuYounVUyl/qhTHPKypk5Nyoz0A4hoYXBI3ZbxJhGmIhDKP7nmTwW5racxl6hkvJb/QhSsuexNyn7FSdAtnAu3RJvWcswoVITODQ5L4Lukc2QCUD5AklzbuCkk2tZJE32Uf4T9hLPdp9kXFzjNGlMpQ3Wm2ByQ7ZEb0ZfZs64vC0qDl1ZdwTlUQKBgQDvkrL8K/BiVdVh/6ntZKGYb2jrVYN2gAxYvgpki18sKIcCBhFRkmzmTkaMVJh7rFm/9M8X8WocgpttRVjyYuQbKWl58HE9T2sMXPJxLfhd3A5kXxRnko92xHCQIw6mZ72NH4m2ZMnRY0O8tWkZSvqFc5xX65WE5w1ODZFFA9OCTwKBgQCTGw3Lh0hhe2v8JkzpJwX+MJlME39XYvx2bU3lKHzlF+5AnKR+g4q1Blo4o0yczi+QsnDRmOMPoEG/pdM1K1B4ex1ZTrGhn9Y6uqGUTPMJQjiY6Mo7hpZtmMRn1Qpb0x1+G+8Lcp2m2cH9XDhg96hJOzrvJy+jkjt0G4uGtrEjBQKBgQCbdJfL5j8fsA68foenUWtCXNi6DE038bMRSo+32nAxWULx3zoRsnATqatBmAsyBQ5f7t0K7k4qnd15NEyZS+40adTVmV4BqK0P4qg3JNtLHGYCX2gAJSwOpz1bakQxWpO3JUXZIuGKi10C8Lx3+x1Ax1ol+uqUyNDrWwZuwCLZJQKBgFvmx+yCHL8B60mA6AW04nKWC/9SIvkGFzEpQkPK0pQRKkBlXALJzSsgx9agdml0CpU8VW0sNZZ6iPjg8R1DbSeHyqMTnbeacq0IEY/jruX24ALvVAByKaJYkUNI/gq1zrQNYX7mYQVu/1l4dqRltFXj0gK8mW94GPqFhtpnYiy9AoGBAKCs6+0UYYNhwmI03i3897sf9Tgxm0pKfPw3On7Kqfhrr6aPLn0zlR+WXF9P+46yX4PK3DPqJhQgegScbSWr0XwzJE6wz5CscJqW3jorEkTMWxDG/0jKsJ3+boVMpTUD5uB7KgP0sjkErlM69sp2iB2nrCJQnJwnes59bA6at9C7",
                    "json",
                    "UTF-8",
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg/gFmERZlgrjtwClEcgwXFb6fz/lZqJsu7x/y/iKEz7b7mxtDnQeYGEiaOEwxdvmRiljYqlWRe+dYkZmRsPlmhcRi+qA7fivqp+7ksI7YSqL4GNWcK5J0k9rPOLBh4jXpT1F+Bjbeb1WFrq5ENZ4VN8qeWyEuOF8zbfa4pGPtPXSdEvwNdtFX2UZuSCIDvAb0Z/h6hBE9mhpzP1OcVWKbdozESSK61JUhAnVnAb+bm5JjXxKCty0EAbE7v8ZkbVJDcnkKt8t2bfN1yU99zGpUf2nYyONDgFBZRfXVM++K6iC8WPZR4f3vpKJa3o0Igk/1juNYRaNrYX3ENXyTeZ+wwIDAQAB",
                    "RSA2"); //获得初始化的AlipayClient
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
            alipayRequest.setReturnUrl("http://127.0.0.1:8084");
            alipayRequest.setNotifyUrl("http://259q52p965.wicp.vip/pay/paycompent");//在公共参数中设置回跳和通知地址
        //支付请求体
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderid + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orderid + "\"," +
                "    \"body\":\"" + orderid + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
            String form="";
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=UTF-8");
            try {
                //将支付的页面写入用户的浏览器中
                response.getWriter().write(form);//直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    /**
     * 支付完成后的回调方法 --- 一定一定要验证下请求是否来自支付宝
     */
    @RequestMapping("/paycompent")
    @ResponseBody
    public void payCompent(String out_trade_no, String trade_status){
        //获得支付的结果
        if(trade_status.equals("TRADE_SUCCESS")){
            //交易成功
            //修改订单对应的状态
            orderService.updateOrderStatus(out_trade_no, 1);
        }
    }


}
