package bigBoy.app.season;

import bigBoy.app.basicTest;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

import static bigBoyUtils.RedisTest.connect;
import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: H5验证码
 * @author: zhuli
 * @create: 2021-03-29 17:36
 **/
public class checkUserRelationsCodeTest {

private String phone;
private String cidV2 ="fn%2BOQ4oVxomQ4VanhwoqkASEDZBsslMesIJo8oHtVd%2FvFOv5H30BVHM8Z3aDmeRIOuY%2FKzQtbjxByQY1%2FaRmljyyEime8aa%2BDKARCpV57SIV5cvB76EAb%2B2%2FPJt8SkybGLsFziLqMj7gtqYH%2Fwutivrtth0qqx446k2kmOlptrKnvDJkPYjis8BVTVWSRZWYl1lbzVpAMvcJyKtAsaeZKPUwA7Eys3GCzx9d3n24bA60W%2FXQDswmdVIK5lzoBAig%2FY7louQxzNDZuxXI%2BR55wPCPxlwrDgpVoYo7nNTn6v%2BOmJzISGOLBWpgdFSTh27Xydsd6%2B8oyXCTAujivTF0SA%3D%3D";


@Test
public void sendSecurityCodeTest() throws Exception {
    for (int i = 1; i < 2; i++) {
        System.out.println("拉的第"+i+"个人");
        phone = "1312354505" + i;
        RestClient rc = new RestClient("http://bigboy-sit.hupu.com", "/appapi/3/1.1.8/sendSecurityCode");
        rc.setHeader("cid_v2",cidV2);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        rc.body(JSONArray.toJSON(map));
        System.out.println("输入手机号为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用发送验证码接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Jedis jedis = connect();
        Thread.sleep(1000);
        String code = jedis.get("security_code_key:" + phone);
//        System.out.println("验证码为：" + code);

        //助力绑定
//        RestClient rc1 = new RestClient("https://172.16.56.170:8081", "/appapi/3/1.1.6/season/checkUserRelationsCode");
        RestClient rc1 = new RestClient("https://activity-sit.bigboy.club", "/appapi/3/1.1.8/season/checkUserRelationsCode");
        rc.setHeader("cid_v2",cidV2);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("phone", phone);
        map1.put("code", code);
        map1.put("userId", "5000030");
        map1.put("seasonId", "38");
        rc1.body(JSONArray.toJSON(map1));
//        System.out.println("助力传参为：" + map1);
        JSONObject jsonObj1 = rc1.post();
        System.out.println("调用助力接口，接口返回信息：" + jsonObj1);
        Assert.assertEquals(jsonObj1.getString("code"), "SUCCESS");
        System.out.println("---------------------------------------------------------------------");
    }
}
}
