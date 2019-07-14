package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.Service.ISearchService;
import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * linzebin
 * 时间2019/7/11 19:51
 */
@Service
public class SeacrchServiceImpl implements ISearchService {
    @Autowired
    SolrClient solrClient;
    @Override
    public List<Goods> searchByKey(String keyword) {
        //设置solr查询对象
        SolrQuery solrQuery;
        if (keyword==null||keyword.trim()==""){
            solrQuery=new SolrQuery("*:*");
        }else{
       /*     String str="gname:%s||ginfo:%s";*/
            String str ="gname:%s || ginfo:%s";
            String s=String.format(str,keyword,keyword);
            solrQuery=new SolrQuery(s);
        }
        //设置查询高亮
        solrQuery.setHighlight(true);
        //设置高亮的前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //设置高亮的后缀
        solrQuery.setHighlightSimplePost("</font>");
        //设置需要高亮的字段
        solrQuery.addHighlightField("gname");
        List<Goods> goodslist=new ArrayList<>();
        try {
            QueryResponse query =solrClient.query(solrQuery);
            //通过响应对象 获得无高亮的结果集
            SolrDocumentList results = query.getResults();
            //通过响应对象 获得高亮的结果集
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
            //遍历响应出来的对象 给集合赋值
            for (SolrDocument result : results) {
                Goods goods=new Goods();
                //在sorl检索库中 id为Sting类型  所以需要先转化为String类型(拿出来的属性为obj 需要先转 才有值)
                // 再转化为Integer
                Integer id=Integer.parseInt(result.getFieldValue("id")+"");
                String gname = result.getFieldValue("gname")+"";
                String gimage=result.getFieldValue("gimage")+"";
                BigDecimal gprice=new BigDecimal(result.getFieldValue("gprice")+"");
                Integer gsave = (Integer) result.getFieldValue("gsave");
                goods.setGimage(gimage);
                goods.setGname(gname);
                goods.setGprice(gprice);
                goods.setGsave(gsave);
                goods.setId(id);
                //处理高亮结果
                if(highlighting.containsKey(id+"")){
                    //说明当前查出来的是有高亮结果的
                    Map<String, List<String>> map = highlighting.get(id + "");
                    if (map.containsKey("gname")){
                       String gaolian= map.get("gname").get(0);
                        goods.setGname(gaolian);
                    }
                }
                goodslist.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodslist;
    }

    @Override
    public int addGoods(Goods goods) {
        System.out.println("addgoods执行"+goods);
        SolrInputDocument document=new SolrInputDocument();
        document.setField("gimage",goods.getGimage());
        document.setField("ginfo",goods.getGinfo());
        document.setField("gsave",goods.getGsave());
        document.setField("id",goods.getId());
        document.setField("gprice",goods.getGprice().floatValue());
        document.setField("gname",goods.getGname());
        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
