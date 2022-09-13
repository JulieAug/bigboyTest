package bigBoy.test;

import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;
import util.DateUtil;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 点赞测试
 * @author: zhuli
 * @create: 2021-03-29 16:20
 **/
public class addBehavior {
    private String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTcwMDk2MzEsInVzZXJJZCI6NTAzNTE1NH0.7JPOEI8UwcTagM1mJbawhsdw6YPU9Q82hUC03PpEPh4";
    private String cidV2 ="p2uZE1gDFxTMO%2B8aSynvMistwdOYxqs3w4q6o0kXdY5%2F4VVVOoWy%2BBD7hNpYH7Lu2VpRu3GtOZ%2FQ91TnMPBV5nWRS5zCpnHoS9N2rqQYfBDSVyySpmqrLUqFmWjmMlv1%2B7IO%2Bg6Q1AHbXrWU49Vxq0pakseCnpScazuD8PGUNSQhnGpohUw4F8BRJAa9rZ2kaYgVS5GQvVhSo3zFyn2oGa7wjLhb11bVWDTgTU45ukRtdHyJKBUGf4WAE9LE50N2KVa0AKwICceVm0IfbeDeMiqDFXagFBUoyT1FhM7M5txVqSYZMWPzuWAfG1qaS5akNpxwcUXp7cYiYw%2BDap6mOQ%3D%3D";

    @Test
    public void testBehavior() throws Exception{
        for(int j=1;j<10;j++){
            System.out.println("第" + j + "个循环，时间:" + DateUtil.getDateTime());
            for(int i=1;i<11;i++){
                addBehavior();
                cancelBehavior();
                System.out.println("第" + i + "条，时间:" + DateUtil.getDateTime());
                System.out.println();
            }
            System.out.println("---------------------------------------------------------------------");
            System.out.println();
            Thread.sleep(60000);
        }
    }

    public void addBehavior() throws Exception{
        RestClient rc = new RestClient("http://bigboy.hupu.com","/appapi/1/1.1.8/users/behavior/add");
        rc.setHeader("token",token);
        rc.setHeader("cid_v2",cidV2);
        Map<String,Object> map = new HashMap<>();
        map.put("targetId","100007926");
        map.put("type",1);// 0:商品 1：帖子 2：回帖
        map.put("actionType",0);// 0：点赞 1：收藏  2：阅读  3：浏览  4：想要
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
//        System.out.println("点赞传参：" + JSONArray.toJSON(map));
        System.out.println("点赞接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    public void cancelBehavior() throws Exception{
        RestClient rc = new RestClient("http://bigboy.hupu.com","/appapi/1/1.1.8/users/behavior/add");
        rc.setHeader("token",token);
        rc.setHeader("cid_v2",cidV2);
        Map<String,Object> map = new HashMap<>();
        map.put("targetId","100007926");
        map.put("type",1);// 0:商品 1：帖子 2：回帖
        map.put("actionType",0);// 0：点赞 1：收藏  2：阅读  3：浏览  4：想要
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
//        System.out.println("取消点赞传参：" + JSONArray.toJSON(map));
        System.out.println("取消点赞接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
