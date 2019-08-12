package com.qf.controoler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IGoodsTypeService;
import com.qf.entity.GoodsType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/15 23:10
 */
@Controller
public class IndexController {
    @Reference
    IGoodsTypeService goodsTypeService;
    @RequestMapping("/")
    public Object getGoodsTypelist(Model model){
        System.out.println("进入了");
        List<GoodsType> goodsType = goodsTypeService.getGoodsType();
        List<GoodsType> goodsType2 = goodsTypeService.getGoodsType();
        List<GoodsType> goodsType3 = goodsTypeService.getGoodsType();
        model.addAttribute("goodsTypes",goodsType);
        model.addAttribute("goodsTypes2",goodsType);
        model.addAttribute("goodsTypes3",goodsType);
        return "index";
    }
}
