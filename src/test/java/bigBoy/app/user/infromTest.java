package bigBoy.app.user;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import bigBoyUtils.RestClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sun.security.pkcs11.Secmod;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: hupuTest
 * @description: 举报管理
 * @author: zhuli
 * @create: 2020-11-09 15:16
 **/
public class infromTest {

    private final String addInformChannelServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/inform/addInformChannel";

    private final String addInformThreadServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/inform/addInformThread";

    private final String addInformReplyServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/inform/addInformReply";

    private String token;

    private Long channelId = DBUtil.getForValue("select id from channel_pro where goods_id is not null limit 1;","bigBoyData.properties");

    private BigInteger threadId = DBUtil.getForValue("select target_id from reply_info_1  where audit_status =1 ORDER BY create_dt desc limit 1 ;","bigBoy.properties");

    private Long replyId = DBUtil.getForValue("select reply_id from reply_info_1  where target_id = "+threadId,"bigBoy.properties");

    private String informReason ="我想测试一下举报功能是不是OK哟!"+ RandomUtil.getDigits(2);

    @BeforeClass
    public void setUp() throws Exception {
        token = new loginTest().checkSecurityCodeSTest();
    }

    @Test(priority = 1,description = "举报渠道")
    public void addInformChannelTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,addInformChannelServive);
        rc.setHeader("token",token);
        Map<String,Object> map = new HashMap<>();
        map.put("reason",informReason);
        map.put("channelId",channelId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        String reason = DBUtil.getForValue("select reason from inform_list where target_id = '"+channelId +"' order by update_dt desc limit 1","bigBoy.properties");
        Assert.assertEquals(informReason,reason);
    }


    @Test(priority = 2,description = "举报主贴")
    public void addInformThreadTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,addInformThreadServive);
        rc.setHeader("token",token);
//        rc.setHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDk4Mjc3MjEsInVzZXJJZCI6NTAwMDAwNX0.YHlfu_BMUVhXsk6u4HsmR19gh4VPDXhPHw7Gt5sxCb0");
        Map<String,Object> map = new HashMap<>();
        map.put("id",threadId);
        map.put("reason",informReason);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 3,description = "举报评论")
    public void addInformReplyTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,addInformReplyServive);
        rc.setHeader("token",token);
//        rc.setHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDk4Mjc3MjEsInVzZXJJZCI6NTAwMDAwNX0.YHlfu_BMUVhXsk6u4HsmR19gh4VPDXhPHw7Gt5sxCb0");
        Map<String,Object> map = new HashMap<>();
        map.put("threadId",threadId);
        map.put("replyId",replyId);
        map.put("reason",informReason);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @AfterClass
    public void tearDown() {
        DBUtil.update("delete from inform_list where target_id = '"+channelId +"' order by update_dt desc limit 1","bigBoy.properties");
    }
}
