package bigBoy.app.homepage;

import bigBoy.app.basicTest;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @program: zaoApiTest
 * @description: 开机广告图
 * @author: zhuli
 * @create: 2021-01-29 11:34
 **/
public class lastInfo {
    private final String queryLastInfoService = "/appapi/" + basicTest.project + "/" + basicTest.version + "/boot/queryLastInfo";

    @Test(priority = 1,description = "查询开机广告图")
    public void queryLastInfoTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryLastInfoService);
        rc.params("scale",1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
