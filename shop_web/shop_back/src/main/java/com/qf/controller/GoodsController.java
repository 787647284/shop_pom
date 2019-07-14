package com.qf.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.Service.IGoodsService;
import com.qf.entity.Goods;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * linzebin
 * 时间2019/7/5 15:08
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
        @Reference
        IGoodsService iGoodsService;
        //获取配置文件的值（用于存放文件的路径）
    //fast分布式文件管理对象
    @Autowired
    FastFileStorageClient fastFileStorageClient;
        @Value("${path.img}")
        private String uploadPath;
    //文件上传 uploadImg
    @RequestMapping("/uploadImg")  //Spring提供的文件上传对象
    @ResponseBody
    public String  uploadImg(MultipartFile file){

        String uploadFile=" ";
        System.out.println("配置文件的文件路径"+uploadPath);
        //获取上传的文件名
        String originalFilename = file.getOriginalFilename();
        //通过上传的文件名，获得后缀
        int index= originalFilename.lastIndexOf(".");
        //截取.本身以及后面的后缀名
        String houzhui = originalFilename.substring(index+1);
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    houzhui,
                    null
            );
            //获得文件存取的路径
            uploadFile=storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
      /*  //生成随机的文件名
        String filename = UUID.randomUUID().toString() + houzhui;
        //设置上传的全路径
        uploadFile=uploadPath+filename;
        try(
                InputStream inputStream = file.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        ) {
            IOUtils.copy(inputStream,fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(uploadFile);*/
        return "{\"filepath\":\"" + uploadFile + "\"}";
    }
/*    //文件下载
    @RequestMapping("/getImg")
    public void getImg(String imgpath, HttpServletResponse response){
        System.out.println();
        File file=new File(imgpath);
        try (
                InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream= response.getOutputStream();  //赋值到响应流中
        ){
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
        @RequestMapping("/goodList")
    public String goodList(Model model){
            List<Goods> goodsList =iGoodsService.getGoodsList();
            System.out.println(goodsList);
            model.addAttribute("goods",goodsList);
            return "goodslist";
        }
    @RequestMapping("/insert")
    public Object insert(Goods goods){
        System.out.println("商品对象"+goods);
        iGoodsService.innsertGoods(goods);
        return "redirect:/goods/goodList";
    }
       @RequestMapping("/goodsTypeByid")
       @ResponseBody
    public Integer  goodsTypeByid(Integer typeid,Integer id){
           System.out.println("执行了");
           System.out.println("typeid"+typeid+"id"+id);
           Goods goods = iGoodsService.getGoodsByid(id);
           goods.setTid(typeid);
           int i = iGoodsService.updateGoods(goods);
           return i;
           //获得该商品
       }
       @RequestMapping("/deletegoods")
       @ResponseBody
    public Object deletegoods(Integer id){
           System.out.println("删除得id"+id);
           int delete = iGoodsService.delete(id);
           return delete;
       }
}
