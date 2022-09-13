package bigBoy.backendapi.activity;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import util.DateUtil;

import java.util.*;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 打卡接口测试
 * @author: zhuli
 * @create: 2021-04-23 15:05
 **/
public class clockActivityTest {
    private Long signClockId;
    private Long behaviorIdMax;
    private Long behaviorIdMin;
    private String dateStart = DateUtil.getNowTime("yyyy-MM-dd");
    private String dateEnd =  DateUtil.getNextMonthEnd();

    /**
     * 新建打卡活动
     *
     * @throws Exception
     */
    @Test(priority = 1)
    public void addClockCongigTest() throws Exception {
        for(int i=0;i<1;i++){
            RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/sign/clock/config/add");
            Map<String, Object> map = new HashMap<>();
//            map.put("name", "打卡活动" + RandomUtil.getDigits(4));
            map.put("name", "购买商品打卡测试");
            map.put("isShow",0);//是否在个人中心展示 0-展示1-不展示
            map.put("startDt",dateStart);
            map.put("endDt",dateEnd);
            map.put("operate", "zhuli0513");
            rc.body(JSONArray.toJSON(map));
            System.out.println("新增打卡：" + map);
            JSONObject jsonObj = rc.post();
            System.out.println("接口返回信息：" + jsonObj);
            AssertJUnit.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }

//        signClockId = DBUtil.getForValue("select id from sign_clock_config order by create_dt desc limit 1", "bigBoy.properties");
//        behaviorIdMin = DBUtil.getForValue("select id from sign_clock_behavior where sign_clock_id ='"+signClockId+"' order by create_dt asc limit 1", "bigBoy.properties");
//        behaviorIdMax = DBUtil.getForValue("select id from sign_clock_behavior order by id desc limit 1", "bigBoy.properties");
//
//        System.out.println(behaviorIdMax);
//        System.out.println(behaviorIdMin);
    }

    /**
     * 更新打卡和行为的关联
     * @throws Exception
     */
    @Test(priority = 2)
    public void updateClockBehavoirConfigTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/sign/clock/behavior/config/update");
        Integer idMax=behaviorIdMax.intValue();
        Integer idMin=behaviorIdMin.intValue();

        for(int id = idMin; id<idMax+1; id++){
            Map<String, Object> map = new HashMap<>();
            map.put("id",id);
            map.put("signClockId", signClockId);
            Integer behaviorId = Integer.valueOf(RandomUtil.getDigits(1));
            if(behaviorId ==0){
                map.put("behaviorId",12);
            }else{
                map.put("behaviorId",behaviorId);
            }
            map.put("couponDetail","优惠券");
            map.put("couponId",99);
            map.put("couponType",1);
            rc.body(JSONArray.toJSON(map));
            System.out.println("关联打卡&打卡行为：" + map);
            JSONObject jsonObj = rc.post();
            System.out.println("接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }

    /**
     * 新增打卡行为
     *
     * @throws Exception
     */
//    @Test(priority = 3)
    public void addClockBehaviorTest() throws Exception {
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/sign/clock/behavior/add");
        HashMap<String, Object> map = new HashMap<>();
        map.put("behaviorName","购买优选商品");
        map.put("behaviorNums",3);
        //0-登录 1-关注用户 2-关注话题 3-浏览日志 4-点赞日志 5-评论日志 6-收藏日志 7-分享日志 8-浏览商品 9-购买优选商品
        map.put("behaviorType",9);
        map.put("link",0);//跳转地址 0-不跳转 1-首页 2-商城页
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增打卡行为+：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 获取打卡时间段中打卡每一天
     */
//    @Test
    public void getDate(){

        Date startDt = DateUtil.string2Date(dateStart);
        Date endDt = DateUtil.string2Date(dateEnd);

        while (startDt.compareTo(endDt) <= 0){
            System.out.println(DateUtil.date2String(startDt));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDt);
            calendar.add(Calendar.DATE, 1);
            startDt = calendar.getTime();
        }
    }


}
