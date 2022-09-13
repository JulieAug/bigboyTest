package bigBoy.backendapi.season;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 裂变活动任务管理
 * @author: zhuli
 * @create: 2021-03-11 10:56
 **/
public class taskTest {
    private BigInteger taskId;
    private String name = "任务名称"+RandomUtil.getDigits(3);

    /**
     * 新增任务
     * @throws Exception
     */
    @Test(priority = 1)
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
     * 更新任务
     * @throws Exception
     */
    @Test(priority = 2)
    public void updateTaskTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/tasks/update");
        Map<String, Object> map = new HashMap<>();
        map.put("id",taskId);
        //1、成功邀请新用户 2、新用户登录 3、浏览帖子 4、点赞帖子
        //5、评论帖子 6、发布帖子 7、连续登录 8、购买自营商品
        map.put("action",1);
        map.put("effect",3);//受益方 1:当前用户自己 2:邀请当前用户来的那个老用户 3:双方
        map.put("needCount",1);//需要几次才算完成
        rc.body(JSONArray.toJSON(map));
        System.out.println("更新任务：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查看任务列表
     * @throws Exception
     */
    @Test(priority = 3)
    public void queryTasksTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/tasks/query");
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",1);
        map.put("pageSize",100);
        rc.body(JSONArray.toJSON(map));
        System.out.println("查询任务：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertTrue(jsonObj.getString("data").contains(name));
    }

    /**
     * 删除任务
     * @throws Exception
     */
    @Test(priority = 4)
    public void deleteTaskTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/tasks/delete");
        Map<String, Object> map = new HashMap<>();
        map.put("id",taskId);
        rc.body(JSONArray.toJSON(map));
        System.out.println("删除任务：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
