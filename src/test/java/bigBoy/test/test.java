package bigBoy.test;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

import static bigBoyUtils.RedisTest.connect;
import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 测试类
 * @author: zhuli
 * @create: 2020-12-17 14:41
 **/
public class test {

    /**
     * 取余
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        Long num = 5001008L;
        System.out.println(num % 10);
    }

    @Test
    /**
     * 新人专区 清除缓存
     */
    public void deleteRedis() {
//        String id = "5001021";
//        String phone = "18260356798";
        String id = "5000085";
        String phone = "17621120794";
        DBUtil.update("update users_2 set create_at='2022-07-04 17:50:59' where id =" + id, "bigBoy.properties");
        Jedis jedis = connect();
        jedis.del("is_new_people_phone:" + phone);
//        jedis.del("is_new_people_cid:ED365265_87FC_4381_AB13_2AC222F61508");
        jedis.del("user:basic_info:" + id);
        jedis.del("user_daily_card_record:" + id);
        jedis.del("user_daily_card_reward:" + id);
        jedis.del("user_daily_sign_in_list:17:" + id);
    }


    private String project = "1";//Android--iOS
    private String version = "1";//版本号


    private final String apiDomain = "http://bigboy-sit.hupu.com";
    private final String addBigBoyReplyInfoService = "/appapi/" + project + "/" + version + "/bbs/addBigBoyReplyInfo";


    private String token6798 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDk5OTk1MDUsInVzZXJJZCI6NTAwMDAwNX0.9j09jHxUBwMGoo8vwDe9dHeKHGh0PybXX5bV2NqGhI0";

    @Test
    public void addBigBoyReplyInfo() throws Exception {
        for (int j = 0; j < 30; j++) {
            RestClient rc = new RestClient(apiDomain, addBigBoyReplyInfoService);
//        rc.setHeader("token",token6798);
            rc.setHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDkyNDM2MjQsInVzZXJJZCI6NTAwMDAwNn0.gUDD7e6VAVLhuc5e2QpRPoHHZRvKzVttfZKD9OCblQE");
            HashMap<String, Object> map = new HashMap<>();
            map.put("content", "我要测试一下回帖是否正常～" + RandomUtil.getDigits(4));
            List<Object> imgUrlsList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                imgUrlsList.add("http://movie-im).hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
            }
            map.put("imgUrls", imgUrlsList);
            map.put("targetId", "100000050");
            map.put("type", 0);//0-帖子 1-商品 2-橱窗
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("调用回帖接口，传参信息：" + JSONArray.toJSON(map));
            System.out.println("调用回帖接口，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }


    private final String addBehaviorServive = "/appapi/" + project + "/" + version + "/users/behavior/add";

    @Test
    public void addBehavior() throws Exception {
        for (int i = 0; i < 1; i++) {
            RestClient rc = new RestClient(apiDomain, addBehaviorServive);
            rc.setHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDkyNDM2MjQsInVzZXJJZCI6NTAwMDAwNn0.gUDD7e6VAVLhuc5e2QpRPoHHZRvKzVttfZKD9OCblQE");
            Map<String, Object> map = new HashMap<>();
            map.put("targetId", "100000050");
            map.put("type", 1);
            map.put("actionType", 0);
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("调用接口，接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }


    private final String queryUserCenterListService = "/appapi/" + project + "/" + version + "/user/queryUserCenterList";

    @Test
    public void queryUserCenterListTest() throws Exception {
        RestClient rc = new RestClient(apiDomain, queryUserCenterListService);
        rc.setHeader("token", token6798);
        rc.params("pageSize", 10);
        rc.params("pageNum", 1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看消息中心，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    //11117888454消息中心
    private String token1111 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDg0NzQ4ODUsInVzZXJJZCI6NTAwMDAzMX0.uMr-1RlOEmjWhQpjjUnY5_MjDzvZWWKSKZiJWtekXTg";

    @Test
    public void queryUserCenterListTest1111() throws Exception {
        RestClient rc = new RestClient(apiDomain, queryUserCenterListService);
        rc.setHeader("token", token1111);
        rc.params("pageSize", 10);
        rc.params("pageNum", 1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看消息中心，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    @Test(priority = 2, description = "下单")
    public void submitOrderTest() throws Exception {
        for (int i = 0; i < 30; i++) {
            RestClient rc = new RestClient("http://msv-zuul-sit.hupu.io:8769/bigboy-app-api", "/appapi/2/1.2.0/order/submitOrder");
            rc.setHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTg4MzU2MzcsInVzZXJJZCI6NTAwMDA0OH0.qfUoU79SEI88GsTX9qqHOfYgYrb55yjCblzRS3WD8zY");
            rc.setHeader("flag", "1");
            HashMap<String, Object> map = new HashMap<>();
            List<Object> orderItemsList = new ArrayList<>();
            HashMap<String, Object> orderItems = new HashMap<>();
            orderItems.put("goodsId", 92);
            orderItems.put("goodsCount", 1);
            orderItemsList.add(orderItems);
            map.put("orderItems", orderItemsList);

            map.put("expressCode", "normal");
            map.put("receiveAddressId", 89);
            map.put("payChannel", 0);//1-微信 0-支付宝
            map.put("lastPagePayAmount", "0.0");
            map.put("remark", "备注" + i);
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("接口返回：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }

    }


    private String token;


    @Test
    public void addBigBoyThreadInfo() throws Exception {
        for (int i = 0; i < 10; i++) {
            RestClient rc = new RestClient(basicTest.apiDomain, "/appapi/" + basicTest.project + "/" + basicTest.version + "/bbs/addBigBoyThreadInfo");
            rc.setHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDA3NDgwMTQsInVzZXJJZCI6NTAwMTAwOH0.du3BjTHhVq878521Db_-ysqfff359vJ3gmTWrOlsiNQ");
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", "帖子" + i);
            map.put("content", "测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n" + RandomUtil.getDigits(4));
            List<Object> imgUrlsList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                HashMap<String, Object> imgUrls = new HashMap<>();
                imgUrls.put("url", "https://img.bigboy.club/manage-img/8333708f-73e9-4b24-a616-5e746b9a95a5_width_1080_height_1101");
                imgUrls.put("height", "800");
                imgUrls.put("width", "400");
                imgUrlsList.add(imgUrls);
            }
            map.put("images", imgUrlsList);

            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("发帖传参：" + JSONArray.toJSON(map));
            System.out.println("调用发帖接口，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }



    @Test(description = "获取验证码")
    public void getSecurityCodeTest() throws Exception {
        Jedis jedis = connect();
        String code = jedis.get("security_code_key:16781698258");
        System.out.println("验证码为：" +code);
    }

}
