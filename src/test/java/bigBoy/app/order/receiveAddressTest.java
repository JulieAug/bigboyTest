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

import java.util.HashMap;


/**
 * @program: zaoApiTest
 * @description: 收货地址
 * @author: zhuli
 * @create: 2021-01-26 16:34
 **/
public class receiveAddressTest {

    private final String addAddress = "/appapi/" + basicTest.project + "/" + basicTest.version + "/receive/address/add";
    private final String updateAddress = "/appapi/" + basicTest.project + "/" + basicTest.version + "/receive/address/update";
    private final String deleteAddress = "/appapi/" + basicTest.project + "/" + basicTest.version + "/receive/address/delete";
    private final String queryAddress = "/appapi/" + basicTest.project + "/" + basicTest.version + "/receive/address/queryList";

    private Integer userId = 5000005;

    private Long addressId;

    private String token;

    @BeforeClass
    public void setUp(){
//        token = new loginTest().checkSecurityCodeSTest();
        //18260356798
        token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTI1MTU0NTYsInVzZXJJZCI6NTAwMDAwNX0.cObgxAa0hQsFPLnz59f1gxoA4phWowEim0d6vBqGOug";
    }

    @Test(priority = 1,description = "添加收货地址")
    public void addAddressTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,addAddress);
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();
        map.put("isDefault",0);//0-非默认地址 1-默认地址
        map.put("receiverAddress","上海市虹口区中山北一路121号");//详细地址
        map.put("receiverProvince","上海市");//省份
        map.put("receiverCity","上海市");//城市
        map.put("receiverDistrict","虹口区");//区/县
        map.put("receiverMobile","18260356798");//收货移动电话
        map.put("receiverName","收货人姓名"+ RandomUtil.getDigits(3));//收货姓名
//        map.put("receiverPhone","");//收货固定电话
//        map.put("receiverZip","");//邮编
        map.put("userId",userId);//用户id
        map.put("userPhone","18260356798");//用户手机号
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        addressId = DBUtil.getForValue("select id from receive_address order by create_dt desc limit 1","bigBoy.properties");
        System.out.println(addressId);
    }

    @Test(priority = 2,description = "修改收货地址")
    public void updateAddressTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,updateAddress);
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",addressId);
        map.put("isDefault",1);//0-非默认地址 1-默认地址
        map.put("receiverAddress","虎扑体育");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        String receiverAddress = DBUtil.getForValue("select receiver_address from receive_address where id = "+addressId,"bigBoy.properties");
        Assert.assertTrue(receiverAddress.contains("虎扑体育"));
    }

    @Test(priority = 3,description = "查询收货地址列表")
    public void queryListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryAddress);
        rc.setHeader("token",token);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertTrue(jsonObj.getString("data").contains("虎扑体育"));
    }

//    @Test(priority = 4,description = "删除收货地址")
    public void deleteAddressTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,deleteAddress);
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",addressId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        //查询表里最新一条数据的id
        Integer status = DBUtil.getForValue("select status from receive_address where id = "+addressId,"bigBoy.properties");
        Assert.assertEquals(String.valueOf(status),"1");//校验新增的数据是否成功删除
    }
}
