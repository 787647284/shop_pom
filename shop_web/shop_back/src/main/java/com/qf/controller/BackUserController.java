package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IBackService;
import com.qf.Service.IRoleService;
import com.qf.entity.BackUser;
import com.qf.entity.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/1 19:59
 */
@Controller
@RequestMapping("/buser")
public class BackUserController {
    @Reference
    IBackService backService;
        @RequestMapping("/list")
    public String userlist(Model model){
            List<BackUser> backUsers = backService.queryAll();
            model.addAttribute("users",backUsers);
            return "buserlist";
        }
        @RequestMapping("/insert")
     public String insert(BackUser backUser){
            backService.addBackUser(backUser);
            return "redirect:/buser/list";
     }
     @RequestMapping("/updaterole")
    public String updaterole(Integer uid,Integer [] rid){
         System.out.println("uid"+uid+"rid"+ Arrays.toString(rid));
            backService.updateUserRelos(uid,rid);
         return "redirect:/buser/list";
     }
}
