package bigBoy.dataapi;

import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hupuTest
 * @description: 搜索商品接口
 * @author: zhuli
 * @create: 2020-10-26 16:21
 **/
public class searchGoodsTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-data-api";

    private BigInteger goodsId;

    /**
     * 根据商品id获取商品信息
     * @throws Exception
     */
    @Test(priority = 1)
    public void getGoodsByIdTest() throws Exception{
        goodsId = DBUtil.getForValue("select id from goods_pro order by ct asc limit 1;","bigBoyData.properties");
        System.out.println(goodsId);
        RestClient rc = new RestClient(apiDomain, "/api/goods/v1/"+goodsId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询商品接口，接口返回信息：" + jsonObj);
        Assert.assertTrue(jsonObj.getString("data").contains("EG 高达系列 圣诞什锦点心套装 RX-78-2 圣诞色"));
    }

    /**
     * 根据keyword搜索商品信息
     * @throws Exception
     */
    @Test(priority = 2)
    public void searchGoodsTest() throws Exception{
        RestClient rc  = new RestClient(apiDomain,"/api/goods/v1/search");
        Map<String,Object> map = new HashMap<>();
        List<Object> keywordsList = new ArrayList<>();
        keywordsList.add("高达系列");
        map.put("keywords",keywordsList);
        rc.body(map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertTrue(jsonObj.getString("data").contains("高达系列"));
//        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 根据关键词&类型查询商品信息
     * @throws Exception
     */
    @Test(priority = 3)
    public void categorySearchTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/api/goods/v1/category_search");
        Map<String,Object>  map = new HashMap<>();
        map.put("category","高达");
        map.put("pageSize",1);
        map.put("pageNum",20);
        List<Object> keywordsList = new ArrayList<>();
        keywordsList.add("高达系列");
        map.put("keywords",keywordsList);
        rc.body(map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertTrue(jsonObj.getString("data").contains("高达系列"));
    }
}