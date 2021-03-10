package bigBoy.app.lottery;

import bigBoy.app.basicTest;
import bigBoy.app.user.loginTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.DateUtil;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: zaoApiTest
 * @description: 抽奖活动
 * @author: zhuli
 * @create: 2021-01-08 16:23
 **/
public class lotteryTest {
    private final String addLotterService = "/backendapi/lottery/add";
    private final String drawService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/lottery/draw";
    private final String infoService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/lottery/info";
    private final String invitedToDrawService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/lottery/invitedToDraw";
    private final String listService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/lottery/list";
    private final String queryAllService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/lottery/queryAll";
    private final String shareService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/lottery/share";

    private BigInteger lotteryId;
    private Long userId = 5000005L;
    private String token;
    private String awardName = "奖品"+RandomUtil.getDigits(3);

    @BeforeClass
    public void setUp() throws Exception{
//        token = new loginTest().checkSecurityCodeSTest();
        //18260356798
        token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTE2NTYzMjcsInVzZXJJZCI6NTAwMDAwNX0.jlKz63zYI6JM5__C6Lw2t2QL3h1neWKrdgCOEf2S-2w";
    }

    /**
     * 新建一个抽奖
     * @throws Exception
     */
    @Test(priority = 1)
    public void addLotter() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,addLotterService);
        Map<String,Object> map = new HashMap<>();
        map.put("name","造物抽奖活动"+ RandomUtil.getDigits(4));
        map.put("startTime", DateUtil.getDateTime());
        map.put("endTime","2022-01-21 11:00:00");
        map.put("awardGoodsId",207);
        map.put("goodsName","MG MGEX 沙特皇室定制版 独角兽高达 Ver.Ka");
        map.put("awardName",awardName);
        map.put("awardPicture","https://bigboy-img.hoopchina.com.cn/manage-img/1610358188_828_width_820_height_512.jpg");
        map.put("awardPrice",1188);
        map.put("editUser","zhuli0513");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        lotteryId = DBUtil.getForValue("select id from lottery ORDER BY create_dt desc limit 1;","bigBoy.properties");
    }

    /**
     * 查看首页抽奖页面（今天&明天的抽奖）
     * @throws Exception
     */
    @Test(priority = 2,description = "查看H5抽奖页面")
    public void listTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,listService);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertTrue(jsonObj.getString("data").contains(awardName));
//        Assert.assertEquals(jsonObj.getJSONArray("data").getJSONObject(1).getString("awardName"),awardName);
    }

    /**
     * 查看指定抽奖的信息
     * @throws Exception
     */
    @Test(priority = 3,description = "查看指定抽奖的信息")
    public void infoTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,infoService);
        rc.params("userId",userId);
        rc.params("id",lotteryId);
        System.out.println("lotteryId="+lotteryId+",userId="+userId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 参与抽奖
     * @throws Exception
     */
    @Test(priority = 4,description = "参与抽奖")
    public void drawTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,drawService);
        rc.setHeader("token",token);
        HashMap<String,Object> map = new HashMap<>();
        map.put("lotteryId",lotteryId);
        map.put("userId",userId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertNotNull("data");
    }

    /**
     * 分享抽奖——抽奖码+1
     * @throws Exception
     */
    @Test(priority = 5,description = "分享抽奖")
    public void shareTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,shareService);
        HashMap<String,Object> map = new HashMap<>();
        map.put("lotteryId",lotteryId);
        map.put("userId",userId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertNotNull("data");
    }

    /**
     * 查看全部/我的抽奖
     * @return
     */
    @DataProvider
    public static Object[][] type(){
        return new Object[][]{
                {1},{2}
        };
    }
    @Test(priority = 6,dataProvider = "type",description = "查看全部/我的抽奖")
    public void queryAllTest(Integer type) throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryAllService);
        rc.params("userId",userId);
        rc.params("type",type);
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
