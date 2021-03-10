package bigBoy.backendapi;

import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

/**
 * @program: hupuTest
 * @description: 举报管理
 * @author: zhuli
 * @create: 2020-11-05 19:58
 **/
public class infromTest {
    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    /**
     * 查询举报渠道列表
     * @throws Exception
     */
    @Test(priority = 1)
    public void queryInformChannelListTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/inform/queryInformChannelList");
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查询举报评论列表
     * @throws Exception
     */
    @Test(priority = 2)
    public void queryInformReplyListTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/inform/queryInformReplyList");
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
