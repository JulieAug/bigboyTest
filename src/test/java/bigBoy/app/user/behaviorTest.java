package bigBoy.app.user;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: hupuTest
 * @description: 用户点赞/收藏
 * @author: zhuli
 * @create: 2020-11-09 14:10
 **/
public class behaviorTest {

    private final String addBehaviorServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/users/behavior/add";
    private final String cancelBehaviorServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/users/behavior/cancel";
    private final String clearUserBrowseRecordListServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/users/behavior/clearUserBrowseRecordList";
    private final String queryBrowseRecordServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/users/behavior/queryBrowseRecord";
    private final String queryListServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/users/behavior/queryList";
    private final String wantGoodsServive = "/appapi/"+basicTest.project+"/"+basicTest.version+"/users/behavior/wantGoods";

    private Long targetId = DBUtil.getForValue("SELECT id from associate_thread ORDER BY create_dt desc limit 1;","bigBoy.properties");

    private BigInteger goodsId = DBUtil.getForValue("SELECT id from goods_pro where price is null ORDER BY ct desc limit 1;","bigBoyData.properties");

    private String token;

    @BeforeClass
    public void setUp() throws Exception {
        token = new loginTest().checkSecurityCodeSTest();
    }

    /**
     * 用户点赞/收藏
     * actionType ———— 0：点赞 1：收藏  2：阅读  3：浏览  4：想要
     * type ———— 0:商品 1：帖子 2：回帖
     * @throws Exception
     */
    @Test(priority = 1,description = "用户点赞/收藏 （取消点赞/收藏）帖子/回帖/橱窗")
    public void behaviorTest() throws Exception{
        Integer[] actionTypes = new Integer[]{0,1};
        behaviorType[] behaviorTypes = new behaviorType[]{
                new behaviorType(0,goodsId.longValue()),
                new behaviorType(1,targetId),
        };
        for (behaviorType behaviortype:behaviorTypes) {
            for (Integer actionType:actionTypes) {
                System.out.println("actionType="+actionType+",type="+behaviortype.getType()+",targetId="+behaviortype.getTargetId());
                addBehavior(actionType,behaviortype.getType(),behaviortype.getTargetId());
                cancelBehaviorTest(actionType,behaviortype.getType(),behaviortype.getTargetId());
            }
        }
    }

    public void addBehavior(Integer actionType,Integer type,Long targetId) throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,addBehaviorServive);
        rc.setHeader("token",token);
        Map<String,Object> map = new HashMap<>();
        map.put("targetId",targetId);
        map.put("type",type);
        map.put("actionType",actionType);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     *取消点赞
     */
    public void cancelBehaviorTest(Integer actionType,Integer type,Long targetId) throws Exception{
        RestClient  rc = new RestClient(basicTest.apiDomain,cancelBehaviorServive);
        rc.setHeader("token",token);
        Map<String,Object> map = new HashMap<>();
        map.put("targetId",targetId);
        map.put("type",type); //0:商品 1：帖子
        map.put("actionType",actionType);//0：点赞 1：收藏
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用取消点赞/收藏接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @DataProvider
    public static Object[][] type(){
        return new Object[][]{
                {0},{1}
        };
    }
    @Test(priority = 2,description = "我的收藏（商品/帖子）",dataProvider = "type")
    public void queryListTest(Integer type) throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryListServive);
        rc.setHeader("token",token);
        rc.params("type",type);
        rc.params("actionType",1);
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用清空浏览记录接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    @Test(priority = 3,description = "查询浏览记录")
    public void queryBrowseRecordTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryBrowseRecordServive);
        rc.setHeader("cid","25BD1599-3910-48C5-9A98-7CE9F1A75EAC");
        JSONObject jsonObj = rc.get();
        System.out.println("调用清空浏览记录接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 4,description = "清空浏览记录")
    public void clearUserBrowseRecordListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,clearUserBrowseRecordListServive);
        rc.setHeader("cid","25BD1599-3910-48C5-9A98-7CE9F1A75EAC");
        JSONObject jsonObj = rc.post();
        System.out.println("调用清空浏览记录接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 5,description = "用户点击想要商品")
    public void wantGoodsTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,wantGoodsServive);
        rc.setHeader("cid","25BD1599-3910-48C5-9A98-7CE9F1A75EAC");
        Map<String,Object> map = new HashMap<>();
        map.put("targetId",goodsId);
        map.put("type",0); //0:商品
        map.put("actionType",4);//4:想要
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用想要接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
