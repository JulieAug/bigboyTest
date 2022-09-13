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
public class hupuBoxActivityTest {
    private BigInteger boxTopicCategoryId;
    private BigInteger boxTopicId;

    /**
     * 添加抽赏分类
     * @throws Exception
     */
    @Test(priority = 1)
    public void addTopicCategoryTest() throws Exception{
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/boxActivity/addTopicCategory");
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","抽赏分类"+ RandomUtil.getDigits(4));
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("online",1);//1：上线 0：下线
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加抽赏分类，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        boxTopicCategoryId = DBUtil.getForValue("SELECT id FROM box_topic_category order by create_dt desc limit 1","bigBoyHupu.properties");
    }


    /**
     * 添加抽赏主题
     * @throws Exception
     */
    @Test(priority = 2)
    public void addTopicTest() throws Exception{
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/boxActivity/addTopic");

        HashMap<String,Object> map = new HashMap<>();
        map.put("boxTopicCategoryId",boxTopicCategoryId);
        map.put("name","抽赏主题"+ RandomUtil.getDigits(4));
        map.put("actualPrice1",0.01);//现金
        map.put("actualPrice5",0.02);
        map.put("virtualPrice1",0.01);//代币
        map.put("virtualPrice5",0.02);
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("content","抽赏活动主题描述"+RandomUtil.getDigits(4));
        map.put("online",1);//1：上线 0：下线

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加抽赏主题，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        boxTopicId = DBUtil.getForValue("SELECT id FROM box_topic order by create_dt desc limit 1","bigBoyHupu.properties");
    }

    /**
     * 添加抽赏主题奖品
     * @throws Exception
     */
    @DataProvider
    public static Object[][] grade(){
        return new Object[][]{
//                {"A"},{"B"},{"C"},{"D"},{"E"},{"F"},{"G"},{"H"},{"I"},{"J"},{"K"},{"LAST"}
                {"A"},{"B"},{"C"},{"D"},{"E"},{"F"},{"G"},{"H"},{"I"},{"J"},{"K"}
        };
    }
    @Test(priority = 3,dataProvider = "grade")
    public void addBoxTopicAward(String grade) throws Exception{
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/boxActivity/addBoxTopicAward");
        HashMap<String,Object> map = new HashMap<>();
        map.put("awardName",grade+"奖品名称"+ RandomUtil.getDigits(4));
        map.put("awardImage","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("boxTopicId",boxTopicId);
        map.put("goodsId",19);
        map.put("grade",grade);
        map.put("totalStock",10);
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
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/boxActivity/addBox");
        HashMap<String,Object> map = new HashMap<>();
        map.put("boxTopicId",boxTopicId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("开箱，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 5)
    public void addTopicToCategoryTest() throws Exception{
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/boxActivity/addTopicToCategory");
        HashMap<String,Object> map = new HashMap<>();
        map.put("boxTopicCategoryId",boxTopicCategoryId);
        map.put("boxTopicId",boxTopicId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("开箱，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
