package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * linzebin
 * 时间2019/7/21 18:57
 */
@Aspect
@Component
public class LoginAop {
    @Autowired
   private  RedisTemplate redisTemplate;
        //切点表达式 给所有携带islogin注解的方法进行增强
        @Around("@annotation(IsLogin)")
        public Object handlLogin(ProceedingJoinPoint proceedingJoinPoint) {
            System.out.println("进入了aop");
        //1 通过request对象获得所有的cookie 找到登录凭证 loginToken
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request =requestAttributes.getRequest();
        String loginToken=null;
        Cookie[] cookies =request.getCookies();
            System.out.println("cookie"+ Arrays.toString(cookies));
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                System.out.println("Cookie"+cookie.getName());
                if("logintoken".equals(cookie.getName())){
                    loginToken=cookie.getValue();
                    break;
                }
            }
        }
        User user=null;
            System.out.println("loginToken"+loginToken);
        //2 通过用户凭证   去redis中寻找该用户信息
        if(loginToken!=null){
             user= (User) redisTemplate.opsForValue().get(loginToken);
        }
            System.out.println("Aop用户"+user);
        //3判断是否已经登录 通过签名  获得方法  通过方法 获得注解 判断是否需要强制登录 默认为false
            if(user==null){
                //获取签名
                MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                Method method = signature.getMethod();
                IsLogin isLogin = method.getAnnotation(IsLogin.class);
                if (isLogin.mustLogin()){
                        //需要强制登录
                    String returnUrl = request.getRequestURL().toString();
                    System.out.println("retrunutlAop"+returnUrl);
                    try {
                        returnUrl= URLEncoder.encode(returnUrl,"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return "redirect:http://127.0.0.1:8081/sso/tologin?returnUrl="+returnUrl;
                }
            }
        //4  如果登录了 给方法形参赋值 User
        Object[] args = proceedingJoinPoint.getArgs();
                for (int i = 0; i < args.length; i++) {
                    if (args[i]!=null&&args[i].getClass()==User.class){
                        args[i]=user;
                        break;

                }
            }
        //5 用自己赋值后的形参  调用新的实参方法
        Object resulit=null;
        System.out.println("resulit为"+resulit);
        try {
                resulit=proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return resulit;
    }
}
