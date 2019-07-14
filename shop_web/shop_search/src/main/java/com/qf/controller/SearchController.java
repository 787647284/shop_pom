package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.ISearchService;
import com.qf.entity.Goods;
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
        System.out.println(keyword);
        List<Goods> goods = searchService.searchByKey(keyword);
        System.out.println(goods);
        model.addAttribute("goodsList",goods);
        return  "searchlist";

    }
}
