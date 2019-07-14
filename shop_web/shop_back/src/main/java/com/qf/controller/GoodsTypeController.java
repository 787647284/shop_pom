package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IGoodsService;
import com.qf.Service.IGoodsTypeService;
import com.qf.entity.Goods;
import com.qf.entity.GoodsType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/6 11:52
 */
@Controller
@RequestMapping("/type")
public class GoodsTypeController {
        @Reference
        IGoodsTypeService goodsTypeService;
        @RequestMapping("/goodstypelist")
    public String goodsTypename(Model model){
            List<GoodsType> goodsType = goodsTypeService.getGoodsType();
            model.addAttribute("types",goodsType);
            System.out.println("查询分类类型");
            System.out.println(goodsType);
            return "goodstypelist";
        }
        @RequestMapping("listajax")
        @ResponseBody
     public List<GoodsType> listajax(){
            return goodsTypeService.getGoodsType();
     }
     @RequestMapping("insert")
    public String insert(GoodsType goodsType){
            goodsTypeService.insertGoodsType(goodsType);
            return "redirect:/type/goodstypelist";
     }
     @ResponseBody
     @RequestMapping("goodsTypeByid")
     public List<GoodsType>  getGoodsTypeByid(Integer tid){
         System.out.println(tid);
         List<GoodsType> goodslist = goodsTypeService.getGoodsTypeByid(tid);
         System.out.println(goodslist);
         return goodslist;
     }
}
