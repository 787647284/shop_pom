package com.qf.shop_back;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopBackApplicationTests {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Test
    public void contextLoads() {
        System.out.println("开始上传图片");
        File file=new File("C:\\Users\\bin\\Desktop\\aaaa.png");
        try {
            StorePath sorepath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    new FileInputStream(file),
                    file.length(),
                    "jpg",
                    null
            );
            System.out.println("上传的路径为"+sorepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
