package bigBoy.app.user;

import bigBoy.app.basicTest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import bigBoyUtils.RestClient;

import java.util.HashMap;
import java.util.Map;

import static bigBoyUtils.RedisTest.connect;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @program: hupuTest
 * @description: 大蓝书登录
 * @author: zhuli
 * @create: 2020-10-22 14:00
 **/
public class loginTest {

    private final String sendSecurityCodeService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/sendSecurityCode";

    private final String checkSecurityCodeService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/checkSecurityCode";

    private final String oauthsLoginService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/oauthsLogin";

//    private String phone = "18260356798";
    private String phone = "13812455872";
//    private String phone = "13262232583";//小明

    public static String token;

    /**
     * 发送短信验证码
     * @throws Exception
     */
    @Test(priority = 1,description = "发送短信验证码")
    public void sendSecurityCodeTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,sendSecurityCodeService);
        Map<String, Object> map = new HashMap<>();
        map.put("phone",phone);
        rc.body(JSONArray.toJSON(map));
        System.out.println("输入手机号为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用发送验证码接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
//        Jedis jedis = connect();
//        String code=jedis.get("security_code_key_"+phone);
//        System.out.println("验证码为："+code);
    }


    /**
     * 验证验证码登录
     * @throws Exception
     */
    @Test(description = "验证验证码登录")
    public String  checkSecurityCodeSTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,checkSecurityCodeService);
        Map<String, Object> map = new HashMap<>();
        map.put("phone",phone);
        Jedis jedis = connect();
        String code=jedis.get("security_code_key:"+phone);
        System.out.println("验证码为："+code);
        map.put("code",code);
        jedis.close();
        rc.body(JSONArray.toJSON(map));
        System.out.println("手机号+验证码为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用验证验证码接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        token = jsonObj.getJSONObject("data").getString("token");
        System.out.println("token："+token);
        return token;
    }

//    @Test(priority = 2,description = "虎扑账号登录")
    public void oauthsLoginTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,oauthsLoginService);
        rc.setHeader("cid","80b81a35e6cc389a");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> hupuLoginDto = new HashMap<>();
        hupuLoginDto.put("ip","172.16.50.147");
        hupuLoginDto.put("passpord","2df2904363bfa47d7793e63c32006005");
        hupuLoginDto.put("userName","18260356798");
        map.put("hupuLoginDto",hupuLoginDto);
        System.out.println(map);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用虎扑授权登录接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}