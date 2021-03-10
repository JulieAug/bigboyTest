package bigBoy.app.order;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * @program: zaoApiTest
 * @description: 订单管理
 * @author: zhuli
 * @create: 2021-01-26 17:50
 **/
public class orderTest {
    private final String queryPreOrderDetailService = "/appapi/" + basicTest.project + "/" + basicTest.version + "/order/queryPreOrderDetail";
    private final String submitOrderService = "/appapi/" + basicTest.project + "/" + basicTest.version + "/order/submitOrder";
    private final String queryListService = "/appapi/" + basicTest.project + "/" + basicTest.version + "/order/queryList";
    private final String cancelOrderService = "/appapi/" + basicTest.project + "/" + basicTest.version + "/order/cancelOrder";

    private String token;

    private Integer goodsId = DBUtil.getForValue("select goods_id from channel_pro where category = 2 and goods_id is not null limit 1;","bigBoyData.properties");

    private String goodsName = DBUtil.getForValue("select product_name from goods_pro where id ="+goodsId,"bigBoyData.properties");

    private Long addressId = DBUtil.getForValue("select id from receive_address order by create_dt desc limit 1","bigBoy.properties");

    private Integer orderPrimaryId;

    @BeforeClass
    public void setUp(){
//        token = new loginTest().checkSecurityCodeSTest();
        //18260356798
        token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTQ1Njc3NjYsInVzZXJJZCI6NTAwMDAwNX0.5JzvMoA-yQXfsvgtxJUBhAP1wP81GB7IotloblkCysg";
    }

    @Test(priority = 1,description = "下单前查询订单详情")
    public void queryPreOrderDetailTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryPreOrderDetailService);
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();

        List<Object> orderItemDtoList = new ArrayList<>();
        HashMap<String,Object> orderItemDto = new HashMap<>();
        orderItemDto.put("goodsId",goodsId);
        orderItemDto.put("goodsName",goodsName);
        orderItemDto.put("goodsCount",2);
        orderItemDto.put("goodsPrice",90);
        orderItemDto.put("goodsUrl","https://bigboy-img.hoopchina.com.cn/manage-img/1610358188_828_width_820_height_512.jpg");
        orderItemDtoList.add(orderItemDto);
        map.put("orderItemDtoList",orderItemDtoList);

        map.put("receiveAddressId",addressId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");

    }

    @Test(priority = 2,description = "下单")
    public void submitOrderTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,submitOrderService);
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();

        List<Object> orderItemsList = new ArrayList<>();
        HashMap<String,Object> orderItems = new HashMap<>();
        orderItems.put("goodsId",goodsId);
        orderItems.put("goodsName",goodsName);
        orderItems.put("goodsCount",2);
        orderItems.put("goodsPrice",90);
        orderItems.put("goodsUrl","https://bigboy-img.hoopchina.com.cn/manage-img/1610358188_828_width_820_height_512.jpg");
        orderItemsList.add(orderItems);
        map.put("orderItems",orderItemsList);

        map.put("receiveAddressId",addressId);
//        map.put("orderPrimaryId","");//首次下单的时候不用传—— 待付款的订单 支付的时候 再传
        map.put("payChannel",1);//1-微信 0-支付宝
        map.put("createPay",true);
        map.put("remark","备注"+ RandomUtil.getDigits(2));
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
//        orderPrimaryId = parseInt(jsonObj.getString("data"));
    }


//    @Test(priority = 3,description = "查询订单列表")
    public void queryListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryListService);
        rc.setHeader("token",token);
        rc.params("status",null);//状态 null—全部 0-未支付 1-支付成功 2-已经发货 3-交易完成 4-取消订单 5-退款中 6退款完成 7-删除不可见
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

//    @Test(priority = 4,description = "取消订单")
    public void cancelOrderTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,cancelOrderService);
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();
        map.put("orderPrimaryId",orderPrimaryId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
