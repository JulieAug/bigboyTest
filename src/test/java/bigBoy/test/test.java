package bigBoy.test;

import bigBoy.app.user.loginTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 测试类
 * @author: zhuli
 * @create: 2020-12-17 14:41
 **/
public class test {

    @Test
    public void test() throws Exception{
        Long num = 888888891L;
        System.out.println(num%8);
    }

    private String project = "1";//Android--iOS
    private String version = "1";//版本号


    private final String apiDomain = "http://bigboy-sit.hupu.com";
    private final String addBigBoyReplyInfoService = "/appapi/"+project+"/"+version+"/bbs/addBigBoyReplyInfo";


    private String token6798 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDk5OTk1MDUsInVzZXJJZCI6NTAwMDAwNX0.9j09jHxUBwMGoo8vwDe9dHeKHGh0PybXX5bV2NqGhI0";

    @Test
    public void addBigBoyReplyInfo() throws Exception{
        for(int j=0;j<30;j++){
            RestClient rc = new RestClient(apiDomain,addBigBoyReplyInfoService);
//        rc.setHeader("token",token6798);
            rc.setHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDkyNDM2MjQsInVzZXJJZCI6NTAwMDAwNn0.gUDD7e6VAVLhuc5e2QpRPoHHZRvKzVttfZKD9OCblQE");
            HashMap<String,Object> map = new HashMap<>();
            map.put("content","我要测试一下回帖是否正常～"+ RandomUtil.getDigits(4));
            List<Object> imgUrlsList = new ArrayList<>();
            for(int i = 0;i<3;i++){
                imgUrlsList.add("http://movie-im).hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
            }
            map.put("imgUrls",imgUrlsList);
            map.put("targetId","100000050");
            map.put("type",0);//0-帖子 1-商品 2-橱窗
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("调用回帖接口，传参信息：" + JSONArray.toJSON(map));
            System.out.println("调用回帖接口，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }


    private final String addBehaviorServive = "/appapi/"+project+"/"+version+"/users/behavior/add";
    @Test
    public void addBehavior() throws Exception{
        for(int i = 0;i<1;i++){
            RestClient rc = new RestClient(apiDomain,addBehaviorServive);
            rc.setHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDkyNDM2MjQsInVzZXJJZCI6NTAwMDAwNn0.gUDD7e6VAVLhuc5e2QpRPoHHZRvKzVttfZKD9OCblQE");
            Map<String,Object> map = new HashMap<>();
            map.put("targetId","100000050");
            map.put("type",1);
            map.put("actionType",0);
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("调用接口，接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }


    private final String queryUserCenterListService = "/appapi/"+project+"/"+version+"/user/queryUserCenterList";

    @Test
    public void queryUserCenterListTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,queryUserCenterListService);
        rc.setHeader("token",token6798);
        rc.params("pageSize",10);
        rc.params("pageNum",1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看消息中心，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    //11117888454消息中心
    private String token1111= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDg0NzQ4ODUsInVzZXJJZCI6NTAwMDAzMX0.uMr-1RlOEmjWhQpjjUnY5_MjDzvZWWKSKZiJWtekXTg";

    @Test
    public void queryUserCenterListTest1111() throws Exception{
        RestClient rc = new RestClient(apiDomain,queryUserCenterListService);
        rc.setHeader("token",token1111);
        rc.params("pageSize",10);
        rc.params("pageNum",1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看消息中心，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
