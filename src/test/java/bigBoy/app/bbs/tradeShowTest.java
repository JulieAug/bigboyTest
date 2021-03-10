package bigBoy.app.bbs;

import bigBoy.app.basicTest;
import bigBoy.app.user.loginTest;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

/**
 * @program: hupuTest
 * @description: 展览页面
 * @author: zhuli
 * @create: 2020-11-09 14:40
 **/
public class tradeShowTest {

    private final String queryAppTradeShowListServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/trade/show/queryAppTradeShowList";

    private final String queryDisplayWindowDetailService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/display/show/queryDisplayInfo";

    private String displayWindowId;

    private String token;

    @BeforeClass
    public void setUp() throws Exception {
        token = new loginTest().checkSecurityCodeSTest();
    }

    /**
     * 展览页面接口
     * @throws Exception
     */
    @Test(priority = 1,description = "查询展览列表")
    public void queryAppTradeShowListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryAppTradeShowListServive);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看展览页面接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        displayWindowId = jsonObj.getJSONArray("data").getJSONObject(0).getString("id");
    }

    /**
     * 查看展览下某一个橱窗的信息
     * @throws Exception
     */
    @Test(priority = 2,description = "查询某个展览下的橱窗作品")
    public void queryDisplayWindowDetailTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryDisplayWindowDetailService);
        rc.params("token",token);
        rc.params("id",displayWindowId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看展览页面接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
