package com.qf.listhehr;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * linzebin
 * 时间2019/7/18 21:44
 */
@Component
public class MyRebbtmqHerder {

        //获得线程池
    ExecutorService executorService= Executors.newFixedThreadPool(5);

    //获得邮箱对象
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    //发件人
    private String fromEamil;
    @RabbitListener(queues = "email_queue")
    public void Handler(Email email){
        executorService.submit(()->{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage);
            try {
                messageHelper.setSubject(email.getSubject());
                messageHelper.setFrom(fromEamil);
                messageHelper.setTo(email.getTo());
                messageHelper.setText(email.getContent(), true);
                messageHelper.setSentDate(new Date());
                javaMailSender.send(mimeMessage);
                //发送邮件
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
