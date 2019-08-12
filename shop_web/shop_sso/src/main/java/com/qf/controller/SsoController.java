package com.qf.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.qf.Service.ICartService;
import com.qf.Service.IUserService;
import com.qf.entity.Email;
import com.qf.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * linzebin
 * 时间2019/7/18 18:48
 */
@Controller
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Reference
    IUserService userService;
    @Autowired
    private Producer captchaProducer = null;

    @Reference
    ICartService cartService;
        //去登录
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }
    //去注册
    @RequestMapping("/toregister")
    public String toregister(){
        return "register";
    }
    //发送邮件
    @RequestMapping("sendCode")
    @ResponseBody
    public  String sendCode(String email){
        System.out.println("邮箱"+email);
        //邮件内容
        String context="注册的验证码为: %d,如果不是本人操作，请忽略";
        //生成的四位随机验证码
       int code= (int) ((Math.random()*9000)+1000);
       //C写法 给%d辅助
        context=String.format(context,code);
        //收件人 标题 内容
        Email email1=new Email(email,"拼夕夕注册",context);
        //将验证码存入redis
        redisTemplate.opsForValue().set(email+"_code",code);
        Integer s = (Integer) redisTemplate.opsForValue().get(email + "_code");
        System.out.println("存入redis验证码为"+s);
        //放入rabbitmq
        rabbitTemplate.convertAndSend("email_exchange","",email1);
        return "succ";
    }
    @RequestMapping("/redister")
    public String redister(User user,int code){
        System.out.println("进入了");
        System.out.println(user+""+code);
        //将redis的验证码和前端传入的验证码进行比较
        Integer s = (Integer) redisTemplate.opsForValue().get(user.getEmail() + "_code");
        System.out.println("取出来的密码"+s);
        if (s==null||code!=s){
            System.out.println("redis存入的验证码"+s);
            return "redirect:/sso/toregister?error=-3";//验证码错误
        }
        int register = userService.register(user);
        if(register>0){
            return "redirect:/sso/tologin";
        }
        return "redirect:/sso/toregister?error=" + register;
    }
    //去修改密码的页面
    @RequestMapping("/toforGetPassword")
    public String toforGetPassword(){
        return "forGetPassword";
    }
    @RequestMapping("/sendPassMail")
    @ResponseBody
    public Map<String,Object> sendPassMail(String username){
        Map<String,Object> map=new HashMap<>();
       //判断该用户是否存在
        User user = userService.getUserByUsername(username);
        if (user==null){
            //用户名不存在
            map.put("code","1000");
            return map;
        }
        //获取uuid
        String token = UUID.randomUUID().toString();
        //发送到reids 当做凭证
        redisTemplate.opsForValue().set(username+"_token",token);
        //设置过期时间 避免长期后还能进行修改密码
        redisTemplate.expire(username+"_token",5, TimeUnit.MINUTES);
        //发送邮件 调用邮箱服务 并且将username和token凭证携带过去
        String url="http://127.0.0.1:8081/sso/tochangePassword?username="+username+"&token="+token;
        Email email=new Email(user.getEmail(),"拼夕夕找回密码","请点击<a href='"+url+"'>找回密码</a>");
        //通过rabbitmq发送
        rabbitTemplate.convertAndSend("email_exchange","",email);
        //设置回显的信息  邮箱的一部分
        String email1= user.getEmail();
        int index = email1.indexOf("@");
        String email2=email1.replace(email1.substring(3,index),"******");
        //设置去邮箱的连接
        String gomail="mail."+email1.substring(index+1);
        map.put("code","0000");
        map.put("emailStr",email2);
        map.put("gomail",gomail);
        return map;
    }
    @RequestMapping("/tochangePassword")
    public String tochangePassword(){
        return "changePassword";
    }
    @RequestMapping("/changePassword")
    public  String changePassword(String username,String password,String token){
        System.out.println("进入了修改密码");
        System.out.println("username"+username+""+password+""+token);
        //获取redis中的验证
        String  tokenredis = (String) redisTemplate.opsForValue().get(username + "_token");
        if(tokenredis.equals(token)){
            //修改密码
            int update = userService.update(username, password);
            if(update>0){
                redisTemplate.delete(username+"_token");
                return "redirect:/sso/tologin";
            }
        }
        return "scc";
    }
    @RequestMapping("/login")
/*    @CrossOrigin*/
    public String login(User user,
                        String returnUrl,
                        HttpServletResponse response,
                        @CookieValue(name = "token",required = false) String token1,
                        String code,
                        Model model,
                        @CookieValue(name="cartToken",required = false) String cartToken
    ){
        String o = (String) redisTemplate.opsForValue().get(token1);
        System.out.println("传入的验证码"+code+"oooooo"+o);
        System.out.println("token"+token1);
        System.out.println("~~~~~"+returnUrl);
        System.out.println(user);
      if(code.equalsIgnoreCase(o)){
          //判断用户是否存在
          User user1 = userService.login(user);
          System.out.println("等待的returnurl"+returnUrl);
          if(user1==null){
              //登录失败
              return "redirect:/sso/tologin";
          }
          //判断returnUrl是否存在 如果不存在 默认跳回首页
          if (returnUrl==null ||"".equals(returnUrl)){
              returnUrl="http://127.0.0.1:8084";
          }
          //登录成功 生成令牌
          String token=UUID.randomUUID().toString();
          System.out.println("存入的token"+token);
          System.out.println("存入的用户"+user1);
          redisTemplate.opsForValue().set(token,user1);
          redisTemplate.expire(token,7,TimeUnit.DAYS);
          //将令牌写入用户的cookie  下一次可以根据令牌找到是否存在对应的用户
          Cookie cookie=new Cookie("logintoken",token);
          cookie.setMaxAge(60*60*24*7);
          cookie.setPath("/");
          response.addCookie(cookie);
          int i = cartService.mergeCarts(cartToken, user1);
          if(i>0){
              Cookie cookie1=new Cookie("cartToken","");
              cookie1.setPath("/");
              cookie1.setMaxAge(0);
               response.addCookie(cookie1);
          }
          return "redirect:"+returnUrl;
      }else{
          model.addAttribute("scc","验证码错误");
          return "redirect:/sso/tologin?returnUrl="+returnUrl;
      }
    }
        @RequestMapping("checkLogin")   //   如果为 Null会报错    非必须的
        @ResponseBody
    public String checkLogin(@CookieValue (name ="logintoken",required = false) String logintoken, String callback ){
            System.out.println("logintoken"+logintoken);
            User user=null;
            //判断用户是否登录
            if(logintoken!=null){
                user  =(User) redisTemplate.opsForValue().get(logintoken);
            }
            System.out.println("user"+user);
            //jsonp必须返回一个字符串 所以将对象转为字符串
            String userjson=user!=null? JSON.toJSONString(user):null;
            System.out.println("userjson"+userjson);
            System.out.println("callback"+callback);
             return callback!=null?callback+"("+userjson+")":userjson;
        }
        @RequestMapping("/logout")
    public String logout(@CookieValue(name = "logintoken",required = false)String logintoken,HttpServletResponse response){
        //删除redis
            if(logintoken!=null){
                redisTemplate.delete(logintoken);
            }
            //清空cookie
            Cookie cookie=new Cookie("logintoken","");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
             return "redirect:/sso/tologin";
        }
        //验证码
    @RequestMapping("/kaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        String uuid= UUID.randomUUID().toString();
        System.out.println("生成的随机验证码为"+capText);
        /*问题1*/
        Cookie cookie=new Cookie("token",uuid);
        cookie.setMaxAge(60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        redisTemplate.opsForValue().set(uuid,capText);
        redisTemplate.expire(uuid,30,TimeUnit.MINUTES);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
