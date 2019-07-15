package com.qf.controoler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IGoodsService;
import com.qf.Service.IGoodsTypeService;
import com.qf.entity.GoodsType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * linzebin
 * 时间2019/7/14 11:20
 */
@Controller
@RequestMapping("/front")
public class GoodsController {
    @Reference
    IGoodsService goodsService;
    @Reference
    IGoodsTypeService goodsTypeService;
    @RequestMapping("/goodstype")
    public Object getGoodsTypelist(Model model){
        List<GoodsType> goodsType = goodsTypeService.getGoodsType();
        List<GoodsType> goodsType2 = goodsTypeService.getGoodsType();
        List<GoodsType> goodsType3 = goodsTypeService.getGoodsType();
        model.addAttribute("goodsTypes",goodsType);
        model.addAttribute("goodsTypes2",goodsType);
        model.addAttribute("goodsTypes3",goodsType);
        return "index";
    }
    @RequestMapping("/goodslist")
    public Object goodslist( Integer id,Model model){
        System.out.println("进入了");
        System.out.println(id);
        List<GoodsType> goodsandType = goodsTypeService.getGoodsandType(id);
        for (GoodsType goodsType : goodsandType) {
            model.addAttribute("goodslist",goodsType.getGoods());
        }
        return "goodstypelist";
    }
}
