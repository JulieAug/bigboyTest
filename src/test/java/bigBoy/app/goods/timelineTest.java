package bigBoy.app.goods;

import bigBoy.app.basicTest;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @program: zaoApiTest
 * @description: 发售日历
 * @author: zhuli
 * @create: 2021-01-19 13:44
 **/
public class timelineTest {

    private final String timelineService = "/appapi/" + basicTest.project + "/" + basicTest.version + "/preSale/timeline";

    @Test(priority = 1,description = "查询指定月份的发售时间线")
    public void timelineTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain, timelineService);
        rc.params("year", 2020);
        rc.params("month", 1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
