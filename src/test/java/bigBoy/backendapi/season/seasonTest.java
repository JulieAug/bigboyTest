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
import static org.testng.AssertJUnit.assertEquals;


/**
 * @program: zaoApiTest
 * @description: 赛季接口
 * @author: zhuli
 * @create: 2021-03-10 17:07
 **/
public class seasonTest {

    private String title = "赛季"+ RandomUtil.getDigits(3);
    private BigInteger seasonId;
    private Integer online;

    /**
     * 添加赛季
     * @throws Exception
     */
    @Test(priority = 1)
    public void addSeasonTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/add");
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        map.put("startTime",DateUtil.getStartTime(new Date()));
        map.put("endTime","2022-03-10 00:00:00");
        map.put("inviteNewUserLimit",100);//拉新用户数限制
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增赛季：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        seasonId = DBUtil.getForValue("select id from season where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
        online = DBUtil.getForValue("select online from season where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 更新赛季
     * @throws Exception
     */
    @Test(priority = 2)
    public void updateSeasonTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/update");
        Map<String, Object> map = new HashMap<>();
        map.put("id",seasonId);
        map.put("title",title);
        map.put("startTime",DateUtil.getStartTime(new Date()));
        map.put("endTime","2021-03-31 00:00:00");
        map.put("inviteNewUserLimit",999);//拉新用户数限制
        rc.body(JSONArray.toJSON(map));
        System.out.println("修改赛季上下线" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查询某个赛季详情
     * @throws Exception
     */
    @Test(priority = 3)
    public void querySeasonDetailTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/queryDetail");
        rc.params("id",seasonId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询资源位接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertEquals(jsonObj.getJSONObject("data").getString("title"),title);
    }

    /**
     * 修改赛季上下线
     * @throws Exception
     */
    @Test(priority = 4)
    public void onlineSeasonTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/online");
        Map<String, Object> map = new HashMap<>();
        map.put("id",seasonId);
        if(online==1){
            map.put("online",0);
        }else{
            map.put("online",1);
        }
        rc.body(JSONArray.toJSON(map));
        System.out.println("修改赛季上下线" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


//    @Test
    public void test() {
        System.out.println(DateUtil.getStartTime(new Date()));
        System.out.println(DateUtil.addMonth(DateUtil.getStartTime(new Date()),3));
    }
}
