package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IGoodsService;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.nio.cs.ext.MacArabic;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * linzebin
 * 时间2019/7/12 11:13
 */
@Controller
@RequestMapping("/item")
public class ItmeController {
    @Reference
    private IGoodsService goodsService;
    @Autowired
    private Configuration configuration;

    @RequestMapping("/createhtml")
    @ResponseBody
    public void createHtml(Integer gid, HttpServletRequest request) throws IOException {
        System.out.println("执行 le");
        //获得商品
        Goods goods = goodsService.getGoodsByid(gid);
        //获得freemarker生成商品的静态页面页面
        Template template = configuration.getTemplate("goodsitem.ftl");
        //map集合用于传值给前端取值
        Map<String, Object> map = new HashMap<>();
        map.put("goods", goods);
        //传递入前端前先按空格拆分后用于展示
        System.out.println("图片拆分前" + goods.getGimage());
        map.put("images", goods.getGimage().split("\\|"));
        map.put("contextpath", request.getContextPath());
        //获得classpath路径 用于在运行时 储存创建的静态页面
        String path = ItmeController.class.getResource("/static").getPath().replace("20%", "");
        System.out.println("获得得路径为" + path);
        File file = new File(path + "/page");
        if (!file.exists()) {  //如果该路径不存在
            file.mkdirs();//创建路径
        }
        //拼接绝对路径  加上文件名字
        try(
                Writer writer = new FileWriter(file.getAbsolutePath() + "/" + gid + ".html")
                ) {
            System.out.println("存入的路径为"+writer.toString());
            //将页面静态化后存入到writer路径中
            template.process(map,writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
