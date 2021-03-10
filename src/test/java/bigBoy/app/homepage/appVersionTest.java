package bigBoy.app.homepage;

import bigBoy.app.basicTest;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @program: zaoApiTest
 * @description: app版本更新管理
 * @author: zhuli
 * @create: 2020-12-07 16:49
 **/
public class appVersionTest {

    private final String queryLastInfoServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/app/version/queryLastInfo";

    @Test(priority = 1,description = "查询版本最新信息")
    public void queryLastInfoTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryLastInfoServive);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看版本信息接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
