package com.qf.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * linzebin
 * 时间2019/7/13 11:11
 */
public class HttpUtil {
    public static String sendGet(String urlStr) {
        try(
                ByteArrayOutputStream out=new ByteArrayOutputStream();
                ) {
            URL url = new URL(urlStr);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            //发送到指定的服务器
            conn.connect();
            //获得服务器的返回值结果
            InputStream in = conn.getInputStream();
            byte [] bytes=new byte[1024*10];
            int len;
            while ((len=in.read(bytes))!=-1){
                    out.write(bytes,0,len);
            }
            byte[] bytes1 = out.toByteArray();
            return new String(bytes1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
