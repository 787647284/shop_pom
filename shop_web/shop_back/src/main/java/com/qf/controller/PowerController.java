package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IPowerService;
import com.qf.entity.Power;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/2 16:46
 */
@Controller
@RequestMapping("power")
public class PowerController {
        @Reference
        IPowerService powerService;
        @RequestMapping("/list")
        public String power(Model model){
            List<Power> power = powerService.getPower();
            model.addAttribute("power",power);
            return "powerlist";
        }
        @ResponseBody
        @RequestMapping("/listajax")
        public List<Power> listajax(Model model){
        return powerService.getPower();
        }
        @RequestMapping("/insert")
        public String insert(Power power){
            System.out.println("添加执行了");
            System.out.println(power);
            powerService.insert(power);
            return "redirect:/power/list";
        }
        @RequestMapping("queryPowersByRid")
        @ResponseBody
        public List<Power>  queryPowersByRid(Integer rid){
            /*查询该角色所拥有的权限，以及其他权限，拥有的进行勾选*/
            System.out.println(rid);
            List<Power> powers = powerService.rolePower(rid);
            System.out.println("查询到的权限为"+powers);
            return powers;

        }
}
