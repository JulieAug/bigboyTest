package bigBoy.app.user;

import bigBoy.app.basicTest;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @program: zaoApiTest
 * @description: 用户中心
 * @author: zhuli
 * @create: 2020-12-20 13:04
 **/
public class userCenter {

    private String queryUserCenterListService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/user/queryUserCenterList";

    private String token;

    @BeforeClass
    public void setUp() throws Exception {
        token = new loginTest().checkSecurityCodeSTest();
    }

    @Test
    public void queryUserCenterListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryUserCenterListService);
        //18260356798
        rc.setHeader("token",token);
        rc.params("pageSize",10);
        rc.params("pageNum",1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看消息中心，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
