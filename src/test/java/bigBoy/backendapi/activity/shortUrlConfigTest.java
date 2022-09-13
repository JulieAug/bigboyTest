package bigBoy.backendapi.activity;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @program: zaoApiTest
 * @description: 短链
 * @author: zhuli
 * @create: 2022-06-17 15:28
 **/
public class shortUrlConfigTest {
    @Test(priority = 1)
    public void addShortUrlConfigTest() throws Exception{
        for (int i = 1; i <20; i++) {
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/shortUrlConfig/add");
            HashMap<String,Object> map = new HashMap<>();
            map.put("name","短链名称"+ RandomUtil.getDigits(4));
            map.put("originUrl","https://www.jianshu.com");
            map.put("description","备注");
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("添加短链，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }
}
