package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IPowerService;
import com.qf.Service.IRoleService;
import com.qf.entity.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * linzebin
 * 时间2019/7/2 16:24
 */
@Controller
@RequestMapping("role")
public class RoleController {
    @Reference
    IRoleService roleService;
    @Reference
    IPowerService powerService;
    @RequestMapping("/list")
    public String rlelist(Model model){
        List<Role> roleList =roleService.getRoleList();
        model.addAttribute("roles",roleList);
        return "rolelist";
    }
    @RequestMapping("/roleinsert")
    public String roleinsert(Role role){
        System.out.println("执行了添加");
        roleService.innsertRole(role);
        return "redirect:/buser/rlelist";
    }
    @RequestMapping("listajax")
    @ResponseBody
    public List<Role> listajax(Integer uid){
        List<Role> roles = roleService.queryListByUid(uid);
        System.out.println("查询到的身份为"+roles);
        return roles;
    }
    @RequestMapping("updatePower")
    @ResponseBody
    public String updatePower(Integer rid,@RequestParam("pids[]") Integer pids[]){
        System.out.println("rid"+rid+"pids"+ Arrays.toString(pids));
        powerService.updatePower(rid,pids);
        return "succ";
    }
}
