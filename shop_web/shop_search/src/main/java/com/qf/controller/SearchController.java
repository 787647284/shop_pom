package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.ISearchService;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/11 19:08
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Reference
    ISearchService searchService;
    @RequestMapping("/searchByKey")
    public String searchByKey(String keyword, Model model){
        Page<Goods> page=new Page<>();
        System.out.println(keyword);
        Page<Goods> goods = searchService.searchByKey(keyword,page);
        System.out.println(goods);
        model.addAttribute("goodsList",goods);
        return  "searchlist";
    }
    @RequestMapping("/page")
    public String page(Integer pageNum){
        System.out.println("执行了");
        System.out.println("pageNUm"+pageNum);
        return null;
    }

}
