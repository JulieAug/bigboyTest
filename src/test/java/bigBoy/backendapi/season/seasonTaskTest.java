package bigBoy.backendapi.season;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.DateUtil;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.testng.AssertJUnit.assertEquals;


/**
 * @program: zaoApiTest
 * @description: 赛季接口
 * @author: zhuli
 * @create: 2021-03-10 17:07
 **/
public class seasonTaskTest {

    private String title = "赛季"+ RandomUtil.getDigits(3);
    private String name = "任务名称"+RandomUtil.getDigits(3);
    private BigInteger seasonId;
    private BigInteger taskId;
    private BigInteger seasonTasksId;
    private Integer online;

    @Test(priority = 1, description = "添加赛季活动")
    public void addSeasonTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/add");
        Map<String, Object> map = new HashMap<>();
        map.put("startTime",DateUtil.getStartTime(new Date()));
        map.put("endTime","2022-03-10 00:00:00");
        map.put("title",title);
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1616053125_119_width_548_height_552.png");
        map.put("inviteNewUserLimit",100);//拉新用户数限制
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增赛季：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        seasonId = DBUtil.getForValue("select id from season where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
        online = DBUtil.getForValue("select online from season where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
    }


    @Test(priority = 2,description = "关联赛季任务")
    public void addSeasonTaskTest() throws Exception{
        for(int id=35;id<43;id++){
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/addTask");
            Map<String, Object> map = new HashMap<>();
            map.put("seasonId",seasonId);
            map.put("taskId",id);
            map.put("score",200);
            rc.body(JSONArray.toJSON(map));
            System.out.println("关联赛季任务：" + map);
            JSONObject jsonObj = rc.post();
            System.out.println("接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }

    @Test(priority = 3,description = "关联赛季优惠券")
    public void addSeasonCouponTest() throws Exception{
        for(int couponId=75;couponId<81;couponId++){
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/addCoupon");
            Map<String, Object> map = new HashMap<>();
            map.put("seasonId",seasonId);
            map.put("couponId",couponId);
            map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1615948745_991_width_430_height_430.png");
            map.put("name","奖品名称"+RandomUtil.getDigits(2));
            map.put("stock",RandomUtil.getDigits(2));
            map.put("price",RandomUtil.getDigits(3));
            int score = parseInt(RandomUtil.getDigits(3));
            if(score!=0) {
                map.put("score",score);
            }else {
                map.put("score",500);
            }
            rc.body(JSONArray.toJSON(map));
            System.out.println("关联赛季优惠券：" + map);
            JSONObject jsonObj = rc.post();
            System.out.println("接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }




}
