package bigBoy.backendapi.activity;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: zaoApiTest
 * @description: 福袋测试
 * @author: zhuli
 * @create: 2021-10-29 11:43
 **/
public class luckyBagActivityTest {
    private BigInteger luckyBagId;
    private String luckyBagName = RandomUtil.getDigits(2)+"新版福袋测试";

    /**
     * 添加福袋活动
     * @throws Exception
     */
    @Test(priority = 1)
    public void test() throws Exception{
        for (int i = 0; i < 1; i++) {
            addLuckyBag();
//            batchAddGoodsToAwardGroupTest();
//            initAwardGroupSellSeqTest();
//            openLuckyBagTest();
//            updateLuckyBagTest();
        }
    }

    public void addLuckyBag() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/luckyBag/add");
        HashMap<String,Object> map = new HashMap<>();
        map.put("luckyBagActivityId",32);//福袋活动id
        map.put("categoryId",8);//福袋分类

        map.put("name",luckyBagName);
        map.put("price",0.01);
        map.put("purchaseLimit",10);//限购
        map.put("killedFrom",1);//垫刀起始值
        map.put("killedTo",10);
        map.put("manualSoldOutDate","2022-08-21 00:00:00");//售罄时间
        map.put("image","https://img.bigboy.club/manage-img/1660198562_25_width_354_height_354.png");
        map.put("detailImage","https://img.bigboy.club/manage-img/1660198567_289_width_72_height_72.png");
        map.put("valImg","https://img.bigboy.club/manage-img/1660198557_566_width_562_height_140.png");

        map.put("online",1);//1：上线 0：下线
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加福袋，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        luckyBagId = DBUtil.getForValue("select id from lucky_bag where name ='"+luckyBagName +"' order by create_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 新建奖品组
     */
    @Test(priority = 2)
    public void batchAddGoodsToAwardGroupTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/luckyBag/batchAddGoodsToAwardGroup");
        HashMap<String,Object> map = new HashMap<>();
        map.put("luckyBagId",luckyBagId);//福袋id
        map.put("groupId",131);//奖品组id
        map.put("grade","欧皇款");//奖品组等级

        List<Object> itemList = new ArrayList<>();
        HashMap<String,Object> item = new HashMap<>();
        item.put("goodsId",985);
        item.put("goodsName","PG 普通版 00高达七剑");
        item.put("count",10);
        itemList.add(item);

        map.put("itemList",itemList);
//        map.put("groupId",204);//奖品组id
//        map.put("grade","造极款");//奖品组等级
//
//        List<Object> itemList = new ArrayList<>();
//        HashMap<String,Object> itemA = new HashMap<>();
//        itemA.put("goodsId",19);
//        itemA.put("goodsName","（次元仓+自营）HGUC 普通版 夜莺");
//        itemA.put("count",1);
//        itemList.add(itemA);
//
//        HashMap<String,Object> itemB = new HashMap<>();
//        itemB.put("goodsId",92);
//        itemB.put("goodsName","helloworld?");
//        itemB.put("count",1);
//        itemList.add(itemB);
//
//        HashMap<String,Object> itemC = new HashMap<>();
//        itemC.put("goodsId",985);
//        itemC.put("goodsName","PG 普通版 00高达七剑");
//        itemC.put("count",1);
//        itemList.add(itemC);
//
//        HashMap<String,Object> itemD = new HashMap<>();
//        itemD.put("goodsId",23628);
//        itemD.put("goodsName","Harmonia bloom系列 普通版 百合");
//        itemD.put("count",1);
//        itemList.add(itemD);
//
//        map.put("itemList",itemList);

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("复用奖品组，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 初始化福袋
     * @throws Exception
     */
    @Test(priority = 3)
    public void initAwardGroupSellSeqTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/luckyBag/initAwardGroupSellSeq");
        HashMap<String,Object> map = new HashMap<>();
        map.put("luckyBagId",luckyBagId);//福袋id
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("初始化福袋，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 开福袋
     * @throws Exception
     */
    @Test(priority = 4)
    public void openLuckyBagTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/luckyBag/openLuckyBag");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",luckyBagId);//福袋id
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("开福袋，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 上线福袋
     * @throws Exception
     */
    @Test(priority = 4)
    public void updateLuckyBagTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/luckyBag/update");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",luckyBagId);//福袋id
        map.put("online",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("上线福袋，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
