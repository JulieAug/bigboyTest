package bigBoy.backendapi.activity;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 组卡活动测试
 * @author: zhuli
 * @create: 2021-07-20 14:07
 **/
public class cardActivityTest {
    private BigInteger cardActivityId = BigInteger.valueOf(17);
    private BigInteger couponTemplateId ;
    private String couponTemplateName;
    private BigInteger cardId ;
    private BigInteger cardGroupId ;

    /**
     * 添加活动
     * @throws Exception
     */
    @Test(priority = 1)
    public void addCardActivity() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/cardActivity/add");

        HashMap<String,Object> map = new HashMap<>();
        map.put("name","历史组卡活动测试"+ RandomUtil.getDigits(2));
        map.put("startTime","2021-01-01 00:00:00");
        map.put("endTime","2021-01-30 00:00:00");
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("description","组卡活动描述"+RandomUtil.getDigits(4));
        map.put("online",1);//1：上线 0：下线
        map.put("score1",100);
        map.put("score5",500);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加活动任务，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        cardActivityId = DBUtil.getForValue("SELECT id FROM card_activity order by create_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 添加活动任务
     * @throws Exception
     */
    /**
     * action 对应
     * 0 - 登录造物App - app相关 app
     * 1 - 关注用户 - 社区相关 record
     * 2 - 关注话题 - 社区相关 record
     * 3 - 浏览日志 - 社区相关 record
     * 4 - 点赞日志 - 社区相关 record
     * 5 - 评论日志 - 社区相关 record
     * 6 - 收藏日志 - 社区相关 record
     * 7 - 分享日志 - 社区相关 record
     * 8 - 浏览商品 - 电商相关 shop
     * 9 - 购买优选商品 - 电商相关 shop
     * 10 - appstore评分 - app相关 app
     * 11 - 发布日志 - 社区相关 record
     * 12 - 赠送卡片 - 活动相关 task
     */
    @Test(priority = 2)
    public void addTaskTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/cardActivity/addTask");
        HashMap<String,Object> map = new HashMap<>();
//        map.put("cardActivityId",cardActivityId);
//        map.put("name","组卡活动任务"+ RandomUtil.getDigits(2));
//        Integer action = Integer.valueOf(RandomUtil.getDigits(1));
//        if(action ==0){
//            map.put("action",2);
//        }else {
//            map.put("action",action);
//        }
        map.put("cardActivityId",33);
        map.put("name","关注2个话题");
        map.put("startTime","2021-07-29 00:00:00");
        map.put("action",2);

        map.put("needCount",2);
        map.put("score",100);
        map.put("type",1);

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加活动任务，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



    /**
     * 添加活动卡片
     * @throws Exception
     */
    @Test(priority = 3)
    public void addCard() throws Exception{
        for(int i= 0;i<10;i++){
            addCardTest();
        }
    }

    public void addCardTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/cardActivity/addCard");

        HashMap<String,Object> map = new HashMap<>();
        map.put("name","卡片测试"+RandomUtil.getDigits(4));
        map.put("cardActivityId",cardActivityId);
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        for(int i= 0;i<24;i++){
            map.put("stockHour"+i,i);
        }
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加活动卡片，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        cardId = DBUtil.getForValue("SELECT id FROM card order by create_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 新增优惠券
     * @throws Exception
     */
    @Test(priority = 4)
    public void addCouponTest() throws Exception{
        couponTemplateName = "打折优惠券测试"+RandomUtil.getDigits(4);
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/add");
        Map<String, Object> map = new HashMap<>();
        Integer discount = parseInt(RandomUtil.getDigits(1));
        if(discount==0){
            map.put("discount",8);
        }else{
            map.put("discount", discount);
        }
        map.put("thresholdPrice",RandomUtil.getDigits(3));
        map.put("title",couponTemplateName);
        map.put("type",1);//优惠券类型 1-折扣 2-满减
        map.put("discountBuyCount",1);
        map.put("expiryType",2);
        map.put("effectiveDay",30);//领取后多少天内有效
        map.put("endTime","2022-03-10 00:00:00");
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增优惠券：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        couponTemplateId = DBUtil.getForValue("select id from coupons_template where title ='"+couponTemplateName +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 添加卡组
     * @throws Exception
     */
    @Test(priority = 5)
    public void addCardGroup() throws Exception{
        for(int i= 0;i<10;i++){
            addCouponTest();
            addCardGroupTest();
        }
    }

    public void addCardGroupTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/cardActivity/addCardGroup");
        HashMap<String,Object> map = new HashMap<>();
        map.put("cardActivityId",cardActivityId);
        map.put("name","卡组名称"+RandomUtil.getDigits(4));
        map.put("image","http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
        map.put("awardName","奖品名称"+RandomUtil.getDigits(4));
        List<Object> awardImageList = new ArrayList();
        awardImageList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("awardImageList",awardImageList);
        map.put("couponTemplateId",couponTemplateId);
        map.put("couponTemplateName",couponTemplateName);
        map.put("hot",1);//是否是热门卡组，1——是 0——不是
        map.put("online",1);//1——上线，0——下线
        map.put("sort",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加卡组，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        cardGroupId = DBUtil.getForValue("SELECT id FROM card_group order by create_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 把活动卡片添加到卡组下
     * @throws Exception
     */
    @Test(priority = 6)
    public void addCardToGroupTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/cardActivity/addCardToGroup");

        HashMap<String,Object> map = new HashMap<>();
        map.put("cardId",cardId);
        map.put("cardGroupId",cardGroupId);

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加卡片到卡组中，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 配置活动卡片收集奖励
     * @throws Exception
     */
    @Test(priority = 7)
    public void addCardCollectAward() throws Exception{
        for(int i= 0;i<10;i++){
            addCouponTest();
            addCardCollectAwardTest(i);
        }
    }

    public void addCardCollectAwardTest(Integer count) throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/cardActivity/addCardCollectAward");

        HashMap<String,Object> map = new HashMap<>();
        map.put("cardActivityId",cardActivityId);
        map.put("awardName","奖品名称"+RandomUtil.getDigits(4));
        map.put("awardImg","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("couponTemplateId",couponTemplateId);
        map.put("couponTemplateName",couponTemplateName);
        map.put("count",count);

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("配置活动卡片收集奖励，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



    @Test
    public void addCardTest1() throws Exception{
        for(int i= 1;i<50;i++) {
            RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/cardActivity/addCard");
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", "普通卡片测试" + i);
            map.put("cardActivityId",39);
            map.put("cardType",1);
            map.put("image", "https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
//            for (int j = 0; j < 24; j++) {
//                map.put("stockHour" + j, j);
//            }
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("添加活动卡片，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }


    @Test
    public void addCardGroupTest1() throws Exception{
        for(int i= 0;i<10;i++) {
            RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/cardActivity/addCardGroup");
            HashMap<String, Object> map = new HashMap<>();
            map.put("cardActivityId",39);
            map.put("name", "卡组名称" +i);
            map.put("image", "http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
            map.put("awardName", "奖品名称" + i);
            List<Object> awardImageList = new ArrayList();
            awardImageList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
            map.put("awardImageList",awardImageList);
            map.put("couponTemplateId", 244);
            map.put("couponTemplateName", "打折优惠券测试9441");
            map.put("hot", 1);//是否是热门卡组，1——是 0——不是
            map.put("online", 1);//1——上线，0——下线
            map.put("sort", 1);
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("添加卡组，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }
}
