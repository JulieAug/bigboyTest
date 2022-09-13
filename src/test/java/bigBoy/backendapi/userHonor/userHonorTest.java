package bigBoy.backendapi.userHonor;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @program: zaoApiTest
 * @description: 官方认证
 * @author: zhuli
 * @create: 2021-10-13 17:18
 **/
public class userHonorTest {

    /**
     * 添加官方认证
     */
    @Test
    public void insertUserHonorTest() throws Exception{
        for (int i = 1; i < 21; i++) {
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/userHonor/insertUserHonor");
            HashMap<String,Object> map = new HashMap<>();
            map.put("honorName","官方认证名称"+i);
            map.put("honorUrl","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
            map.put("remark","官方认证备注"+i);
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("添加官方认证，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }

    }
}
