package bigBoy.backendapi.lottery;

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
import java.util.Map;

/**
 * @program: zaoApiTest
 * @description: 抽奖模块
 * @author: zhuli
 * @create: 2021-01-08 13:33
 **/
public class lotterTest {
    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private final String addLotterService = "/backendapi/lottery/add";
    private final String editLotterService = "/backendapi/lottery/edit";
    private final String updateLotterService = "/backendapi/lottery/update";
    private final String offlineLotterService = "/backendapi/lottery/offline";
    private final String queryLotterService = "/backendapi/lottery/query";
    private BigInteger lotteryId;

    @Test(priority = 1,description = "添加抽奖")
    public void addLotterTest() throws Exception{
        for(int i=0;i<1;i++){
            addLotter();
        }
    }

    public void addLotter() throws Exception{
        RestClient rc = new RestClient(apiDomain,addLotterService);
        Map<String,Object> map = new HashMap<>();
        map.put("name","指定中奖"+RandomUtil.getDigits(4));
        map.put("startTime","2021-01-12 10:00:00");
        map.put("endTime","2021-01-25 14:00:00");
        map.put("awardGoodsId",207);
        map.put("goodsName","MG MGEX 沙特皇室定制版 独角兽高达 Ver.Ka");
        map.put("awardName", RandomUtil.getDigits(4)+"奖品名称");
        map.put("awardPicture","http://bigboy-img.hoopchina.com.cn/manage-img/e566900a-6507-4caf-af6e-665567b5fcfe_width_1120_height_660.png");
        map.put("awardPrice",1188);
        map.put("editUser","zhuli0513");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        lotteryId = DBUtil.getForValue("select id from lottery ORDER BY create_dt desc limit 1;","bigBoy.properties");
    }

    @Test(priority = 2,description = "更新抽奖基本信息")
    public void updateLotterTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,updateLotterService);
        Map<String,Object> map = new HashMap<>();
        map.put("id",lotteryId);
        map.put("name","抽奖名称更新"+RandomUtil.getDigits(4));
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

//   @Test(priority = 3,description = "编辑抽奖码方式")
    public void editLotterTest() throws Exception{
       RestClient rc = new RestClient(apiDomain,editLotterService);
       Map<String,Object> map = new HashMap<>();
       map.put("editUser","zhuli0513");
       map.put("lotteryId","");
       List<Object> rulesList = new ArrayList<>();
       Map<String,Object> rules = new HashMap<>();
       rules.put("getWay","");
       rules.put("id","");
       rulesList.add(rules);
       map.put("rules",rulesList);
       rc.body(JSONArray.toJSON(map));
       JSONObject jsonObj = rc.post();
       System.out.println( "传参："+JSONArray.toJSON(map));
       System.out.println("接口返回：" + jsonObj);
       Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 4,description = "上下线抽奖")
    public void offlineLotterTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,offlineLotterService);
        Map<String,Object> map = new HashMap<>();
        map.put("id",lotteryId);
        map.put("online",0);//上下线状态 1：上线 0：下线
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}

