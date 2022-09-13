package bigBoy.backendapi.activity;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @program: zaoApiTest
 * @description: 抽赏测试
 * @author: zhuli
 * @create: 2021-08-18 17:06
 **/
public class boxActivityTest {
    private BigInteger boxTopicCategoryId;
    private BigInteger boxTopicId;

    /**
     * 添加抽赏分类
     * @throws Exception
     */
    @Test(priority = 1)
    public void addTopicCategoryTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/boxActivity/addTopicCategory");
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","分类tab"+ RandomUtil.getDigits(2));
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("online",1);//1：上线 0：下线
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加抽赏分类，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        boxTopicCategoryId = DBUtil.getForValue("SELECT id FROM box_topic_category order by create_dt desc limit 1","bigBoy.properties");
    }


    /**
     * 添加抽赏主题
     * @throws Exception
     */
    @Test(priority = 2)
    public void addTopicTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/boxActivity/addTopic");

        HashMap<String,Object> map = new HashMap<>();
        map.put("boxTopicCategoryId",boxTopicCategoryId);
        map.put("name","抽赏"+ RandomUtil.getDigits(4));
        map.put("type",1);//抽赏类型 1：普通 2：特价
//        map.put("discountActualPrice1",0.01);//特价现金
//        map.put("discountVirtualPrice1",0.02);//特价造物赏
        map.put("actualPrice1",0.01);//现金
        map.put("actualPrice5",0.02);
        map.put("virtualPrice1",0.01);//代币
        map.put("virtualPrice5",0.02);
        map.put("scorePrice1",1);//次元石
        map.put("scorePrice5",5);
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1633934934_580_width_600_height_759.gif");
        map.put("content","抽赏活动主题描述"+RandomUtil.getDigits(4));
        map.put("online",1);//1：上线 0：下线

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加抽赏主题，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        boxTopicId = DBUtil.getForValue("SELECT id FROM box_topic order by create_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 添加抽赏主题奖品
     * @throws Exception
     */
    @DataProvider
    public static Object[][] grade(){
        return new Object[][]{
                {"A"},{"B"},{"C"},{"D"},{"E"}
                ,{"F"},{"G"},{"H"},{"I"},{"J"}
                ,{"K"},{"L"},{"M"},{"N"},{"O"}
                ,{"P"},{"Q"},{"R"},{"S"},{"T"}
                ,{"LAST"}
                ,{"FIRST"}
                ,{"UNLUCKY"}
        };
    }
    @Test(priority = 3,dataProvider = "grade")
    public void addBoxTopicAward(String grade) throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/boxActivity/addBoxTopicAward");
        HashMap<String,Object> map = new HashMap<>();
        map.put("awardName",grade+"奖品名称"+ RandomUtil.getDigits(4));
        map.put("awardImage","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("boxTopicId",boxTopicId);
        map.put("goodsId",985);
        map.put("grade",grade);
        map.put("totalStock",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加抽赏主题奖品，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 开箱
     * @throws Exception
     */
    @Test(priority = 4)
    public void addBoxTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/boxActivity/addBox");
        HashMap<String,Object> map = new HashMap<>();
        map.put("boxTopicId",boxTopicId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("开箱，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    //抽赏关联分类
    @Test(priority = 5)
    public void addTopicToCategoryTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/boxActivity/addTopicToCategory");
        HashMap<String,Object> map = new HashMap<>();
        map.put("boxTopicCategoryId",boxTopicCategoryId);
        map.put("boxTopicId",boxTopicId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("开箱，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 新建兑换道具
     * @throws Exception
     */
//    @Test(priority = 6)
    public void addAwardTest() throws Exception{
        for (int i = 67; i <99 ; i++) {
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/boxPeriodActivity/addAward");
            HashMap<String,Object> map = new HashMap<>();
            map.put("name","优惠券奖品"+RandomUtil.getDigits(3));
            map.put("awardType",1);
            map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1651317204_623_width_700_height_1310.jpg");
            map.put("targetId",i);
            map.put("needScore",10);
            map.put("remainStock",100);
            map.put("online",1);
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }


    /**
     * 新增阶段奖励
     * @throws Exception
     */
//    @Test(priority = 7)
    public void addStageAwardTest() throws Exception {
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/boxPeriodActivity/addStageAward");
        HashMap<String, Object> map = new HashMap<>();
        map.put("boxPeriodActivityStageId", 27);
        map.put("awardType", 0);//0-积分 1-优惠券
        map.put("requirement", 2);//1-个人 2-全员+个人
        map.put("image", "https://bigboy-img.hoopchina.com.cn/manage-img/1651317204_623_width_700_height_1310.jpg");
        map.put("name", "全员+个人 100次元石");
        map.put("score", 100);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

//    @Test
    public void addStageAwardTest1() throws Exception {
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/boxPeriodActivity/addStageAward");
        HashMap<String, Object> map = new HashMap<>();
        map.put("boxPeriodActivityStageId", 26);
        map.put("awardType",1);//0-积分 1-优惠券
        map.put("requirement",2);//1-个人 2-全员+个人
        map.put("image", "https://bigboy-img.hoopchina.com.cn/manage-img/1651317204_623_width_700_height_1310.jpg");
        map.put("name", "优惠券C—10元优惠");
        map.put("targetId", 253);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
