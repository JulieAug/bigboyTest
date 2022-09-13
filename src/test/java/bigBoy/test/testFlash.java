package bigBoy.test;

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
 * @description: 关闭秒杀
 * @author: zhuli
 * @create: 2022-03-29 20:04
 **/
public class testFlash {
    @Test
    public void addTopicCategoryTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/flashSaleActivity/closeFlashSaleActivity");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",197);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        System.out.println("线程1");
    }

    @Test
    public void addTopicCategoryTest2() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/flashSaleActivity/closeFlashSaleActivity");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",197);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        System.out.println("线程2");
    }
}
