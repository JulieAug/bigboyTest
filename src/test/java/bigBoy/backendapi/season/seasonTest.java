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
    private String name = "任务名称"+RandomUtil.getDigits(3);
    private BigInteger seasonId;
    private BigInteger taskId;
    private BigInteger seasonTasksId;
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
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1610358188_828_width_820_height_512.jpg");
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
//    @Test(priority = 2)
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
//    @Test(priority = 3)
    public void querySeasonDetailTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/queryDetail");
        rc.params("id",seasonId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询赛季详情接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertEquals(jsonObj.getJSONObject("data").getString("title"),title);
    }

    /**
     * 修改赛季上下线
     * @throws Exception
     */
//    @Test(priority = 4)
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

    /**
     * 新增任务
     * @throws Exception
     */
//    @Test(priority = 5)
    public void addTaskTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/tasks/add");
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        //1、成功邀请新用户 2、新用户登录 3、浏览帖子 4、点赞帖子
        //5、评论帖子 6、发布帖子 7、连续登录 8、购买自营商品
        map.put("action",1);
        map.put("effect",1);//受益方 1:当前用户自己 2:邀请当前用户来的那个老用户 3:双方
        map.put("needCount",2);//需要几次才算完成
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增任务：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        taskId = DBUtil.getForValue("select id from tasks where name ='"+name +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 添加赛季任务
     * @throws Exception
     */
    @Test(priority = 6)
    public void addSeasonTaskTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/addTask");
        Map<String, Object> map = new HashMap<>();
        map.put("seasonId",seasonId);
        map.put("taskId",taskId);
        map.put("score",200);
        rc.body(JSONArray.toJSON(map));
        System.out.println("添加赛季任务：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        seasonTasksId = DBUtil.getForValue("select id from season_tasks where season_id ='"+seasonId +"' and task_id = '"+taskId +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 更新赛季任务
     * @throws Exception
     */
//    @Test(priority = 6)
    public void updateSeasonTaskTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/season/updateTask");
        Map<String, Object> map = new HashMap<>();
        map.put("id",seasonTasksId);
        map.put("taskId",taskId);
        map.put("score",300);
        rc.body(JSONArray.toJSON(map));
        System.out.println("添加赛季任务：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        seasonTasksId = DBUtil.getForValue("select id from season_tasks where season_id ='"+seasonId +"' and task_id = '"+taskId +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 后台查询赛季任务
     * @throws Exception
     */
//    @Test(priority = 7)
    public void querySeasonTaskTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/b00000000000000000ckendapi/season/queryTask");
        rc.params("seasonId",seasonId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询赛季任务详情接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertTrue(jsonObj.getString("data").contains(String.valueOf(seasonId)));
    }

}
