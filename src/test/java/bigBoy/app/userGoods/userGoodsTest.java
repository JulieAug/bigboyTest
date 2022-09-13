package bigBoy.app.userGoods;

import bigBoy.app.basicTest;
import bigBoyUtils.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: zaoApiTest
 * @description: 用户发布商品
 * @author: zhuli
 * @create: 2021-06-22 10:38
 **/
public class userGoodsTest {

    private Integer goodsId = 92;
    private Long userGoodsId;
    private String token;

    @BeforeClass
    public void setUp() throws Exception {
//        token = new loginTest().checkSecurityCodeSTest();
        //18260356798
        token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mzk5ODUyMzEsInVzZXJJZCI6NTAwMDA0OH0.DiZz8EIyTUbgwOyWHt0wt8gZYWsoXyTLlraMUY-AG5c";
        //11117888454
    }

    //发布商品时查询成色标签
    @Test(priority = 1)
    public void listFinenessTags() throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/userGoods/listFinenessTags");
        rc.setHeader("token",token);

        JSONObject jsonObj = rc.get();
        System.out.println("搜素商品成色标签：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



    /**
     * 用户发布商品
     * @throws Exception
     */
    @Test(priority = 2)
    public void addUserGoods() throws Exception {
        for (int i =0;i<1;i++){
            userGoods();
            auditStatus();
        }
    }

    public void userGoods() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/userGoods");
        rc.setHeader("token",token);

        HashMap<String,Object> map = new HashMap<>();
        map.put("describe",DateUtil.getCurrentDate()+"测试安卓下单测试安卓下单测试安卓下单测试安卓下单");

        //添加商品图片
        List<Object> picsList = new ArrayList<>();
        for(int i = 0;i<5;i++){
            picsList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1610358188_828_width_820_height_512.jpg");
        }
        map.put("pics",picsList);

        List<Object> finenessTagsList = new ArrayList<>();
        finenessTagsList.add("全新未拆");  //添加商品成色标签
        map.put("finenessTags",finenessTagsList);
        map.put("goodsId",goodsId);
        map.put("price",1);
        map.put("shippingFee",0);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("调用用户发布商品接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        userGoodsId = DBUtil.getForValue("SELECT id FROM user_sell_goods order by create_at desc limit 1","bigBoy.properties");
    }

    /**
     * 审核通过商品
     * @throws Exception
     */
    @Test
    public void auditStatus() throws Exception {
        RestClientExtent rc = new RestClientExtent(basicTest.backendDomain,"/backendapi/user_goods/auditStatus");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",userGoodsId);
        map.put("pass",true);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.put();
        System.out.println(jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 查询用户自己的商品列表
     * @throws Exception
     */
    @Test(priority = 3)
    public void listSelfGoods() throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/userGoods/listSelfGoods");
        rc.setHeader("token",token);
        rc.params("pageNum",1);
        rc.params("pageSize",100);
        rc.params("orderBy",1);//0-价格 1-创建时间
        rc.params("asc",true);

        JSONObject jsonObj = rc.get();
        System.out.println("查看用户自己的商品列表：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查询他人的商品列表
     * @throws Exception
     */
    @Test(priority = 4)
    public void listUserGoods() throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/userGoods/listUserGoods");
        rc.setHeader("token",token);
        rc.params("pageNum",1);
        rc.params("pageSize",100);
        rc.params("orderBy",1);//0-价格 1-创建时间
        rc.params("asc",true);
        rc.params("userId","5000031");

        JSONObject jsonObj = rc.get();
        System.out.println("查看用户自己的商品列表：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查看某个商品的商品详情
     * @throws Exception
     */
    @Test(priority = 5)
    public void getById() throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/userGoods/getById");
        rc.setHeader("token",token);
        rc.params("id",39);

        JSONObject jsonObj = rc.get();
        System.out.println("查看某个商品的商品详情：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查询某个商品下用户售卖的个人商品
     * @throws Exception
     */
    @Test(priority = 6)
    public void listGoodsIdUserGoods() throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/userGoods/listGoodsIdUserGoods");
        rc.setHeader("token",token);
        rc.params("goodsId",goodsId);
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        rc.params("orderBy",1);//0-价格 1-创建时间
        rc.params("asc",true);
        JSONObject jsonObj = rc.get();
        System.out.println(jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 删除个人商品
     * @throws Exception
     */
    @Test(priority = 7)
    public void deleteById() throws Exception {
        RestClientExtent rc = new RestClientExtent(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/userGoods/byId");
        rc.setHeader("token",token);
        rc.params("id",40);
        JSONObject jsonObj = rc.delete();
        System.out.println(jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * c2c下单
     * @throws Exception
     */
    @Test(priority = 8)
    public void submitC2cOrder() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/order/submitC2cOrder");
        rc.setHeader("token",token);

        HashMap<String,Object> map = new HashMap<>();
        map.put("receiveAddressId",91);
        map.put("remark","");
        map.put("userCellGoodsId",5);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 确认收货
     * @throws Exception
     */
    @Test(priority = 8)
    public void confirmReceive() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/order/confirmReceive");
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();
        map.put("orderId","606968430108930048");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
