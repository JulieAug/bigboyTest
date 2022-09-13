package bigBoy.app.goods;

import bigBoy.app.basicTest;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static bigBoy.app.basicTest.appapi;

/**
 * @program: zaoApiTest
 * @description: 购物车
 * @author: zhuli
 * @create: 2021-05-19 16:37
 **/
public class cartTest {
    private String token;

    @BeforeClass
    public void setUp() throws Exception {
//        token = new loginTest().checkSecurityCodeSTest();
        token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MjE0MTUzNDUsInVzZXJJZCI6NTAwMDA0OH0.5OVg2o2IuO476oflxA3lccV503_38gcJDxkxDucxW44";
    }

    /**
     * 商品加入购物车
     * @throws Exception
     */
    @Test(priority = 1)
    public void addGoodsToCartTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,basicTest.appapi+"/order/addGoodsToCart");
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();
        map.put("count",1);
        map.put("goodsId",19);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参信息：" + JSONArray.toJSON(map));
        System.out.println("接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查询购物车商品种类
     * @throws Exception
     */
    @Test(priority = 2)
    public void countGoodsInCartTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain, appapi+"/order/countGoodsInCart");
        rc.setHeader("token",token);
        JSONObject jsonObj = rc.get();
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        System.out.println("接口返回数据：" + jsonObj);
    }


    /**
     * 查询购物车列表页面
     * @throws Exception
     */
    @Test(priority = 3)
    public void goodsInCartTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain, basicTest.appapi+"/order/goodsInCart");
        rc.setHeader("token",token);
        JSONObject jsonObj = rc.get();
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        if(jsonObj.getJSONArray("data").size() != 0) {
            System.out.println("接口返回数据：" + jsonObj);
        }else {
            System.out.println("购物车内未添加对应商品...");
        }
    }


}
