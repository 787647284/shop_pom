package com.qf.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IBackService;
import com.qf.entity.BackUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 * linzebin
 * 时间2019/7/1 19:53
 */
@Controller
@SessionAttributes("loginUser")
public class LoginController {
    @Reference
    IBackService backService;
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }
  /*  @RequestMapping("/login")
    public String login(String username,String password,Model model){
        System.out.println(username+""+password);
        BackUser user = backService.login(username, password);
        System.out.println(user);
        if(user!=null){
            model.addAttribute("loginUser",user);
            return "index";
        }
        return "redirect:/tologin?error=1";
    }*/
    @RequestMapping("/noperssion")
    public String tonoperssion(){
        return "noperssion";
    }
}
