package bigBoy.backendapi.coupon;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.DateUtil;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 优惠券测试
 * @author: zhuli
 * @create: 2021-03-01 16:49
 **/
public class couponTest {
    private BigInteger couponId ;
    private String title;
    private Integer thresholdPrice = 0;//优惠门槛
    private Integer discount = 8;//折扣
    private Integer reducePriceLimit = 10;//折扣上限
    private Integer effectiveDay = 5;//领取过期时间
    private String effectiveDate ="2022-08-06 00:00:00";//生效时间
    private String expiryDate ="2022-08-07 16:25:00";//失效时间

    /**
     * 新建-发放优惠券
     * @throws Exception
     */
    @Test
    public void couponTest() throws Exception {
        //满减优惠券—领取过期
//        addReduceCouponTestA();
//        addSendUserCouponsTest();

        //满减优惠券—统一过期
        addReduceCouponTestB();
        addSendUserCouponsTest();

//        //打折优惠券—领取过期
//        addDiscountCouponTestA();
//        addSendUserCouponsTest();
//
//        //打折优惠券—统一过期
//        addDiscountCouponTestB();
//        addSendUserCouponsTest();

    }

    /**
     * 新增打折优惠券——领取过期
     * @throws Exception
     */
    @Test
    public void addDiscountCouponTestA() throws Exception{
        title = "抽赏打折领取过期"+RandomUtil.getDigits(4);
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/add");
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        map.put("usableTarget",2);//1-商城；2-抽赏

        map.put("type",1);//优惠券类型 1-折扣 2-满减
        map.put("thresholdPrice",thresholdPrice);//优惠门槛
        map.put("discount",discount);
        map.put("reducePriceLimit",reducePriceLimit);//打折上限

        map.put("expiryType",2);// 1：统一生效过期 2：领取生效过期
        map.put("effectiveDay",effectiveDay);//领取后多少天内有效

        rc.body(JSONArray.toJSON(map));
        System.out.println("新增打折优惠券：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        couponId = DBUtil.getForValue("select id from coupons_template where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 新增打折优惠券——统一过期
     * @throws Exception
     */
    @Test
    public void addDiscountCouponTestB() throws Exception{
        title = "抽赏打折统一过期"+RandomUtil.getDigits(4);
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/add");
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        map.put("usableTarget",2);//1-商城；2-抽赏

        map.put("type",1);//优惠券类型 1-折扣 2-满减
        map.put("thresholdPrice",thresholdPrice);//打折门槛
        map.put("discount",discount);
        map.put("reducePriceLimit",reducePriceLimit);//打折金额上限

        map.put("expiryType",1);// 1：统一生效过期 2：领取生效过期
        map.put("effectiveDate",effectiveDate);
        map.put("expiryDate",expiryDate);

        rc.body(JSONArray.toJSON(map));
        System.out.println("新增打折优惠券：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        couponId = DBUtil.getForValue("select id from coupons_template where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 新增满减优惠券——领取过期
     * @throws Exception
     */
    @Test
    public void addReduceCouponTestA() throws Exception{
        title = "抽赏满减优惠券"+RandomUtil.getDigits(4);
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/add");
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        map.put("usableTarget",2);//1-商城；2-抽赏

        map.put("expiryType",2);// 1：统一生效过期 2：领取生效过期
        map.put("effectiveDay",effectiveDay);//领取后多少天内有效

        map.put("type",2);//优惠券类型 1-折扣 2-满减
        map.put("thresholdPrice",thresholdPrice);//满减门槛
        map.put("reducePrice",30);//满减金额

        rc.body(JSONArray.toJSON(map));
        System.out.println("新增满减优惠券：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        couponId = DBUtil.getForValue("select id from coupons_template where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 新增满减优惠券——统一过期
     * @throws Exception
     */
    @Test
    public void addReduceCouponTestB() throws Exception{
        title = "优惠券16.25过期"+RandomUtil.getDigits(4);
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/add");
        Map<String, Object> map = new HashMap<>();
        map.put("title",title);
        map.put("usableTarget",2);//1-商城；2-抽赏

        map.put("expiryType",1);// 1：统一生效过期 2：领取生效过期
        map.put("effectiveDate",effectiveDate);
        map.put("expiryDate",expiryDate);

        map.put("type",2);//优惠券类型 1-折扣 2-满减
        map.put("thresholdPrice",thresholdPrice);//满减门槛
        map.put("reducePrice",30);//满减金额

        rc.body(JSONArray.toJSON(map));
        System.out.println("新增满减优惠券：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        couponId = DBUtil.getForValue("select id from coupons_template where title ='"+title +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 发放优惠券
     * @throws Exception
     */
    @Test
    public void addSendUserCouponsTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/batchAddUserCoupons");
        HashMap<String,Object> map = new HashMap<>();
        map.put("title","优惠券发放"+RandomUtil.getDigits(4));
        map.put("remark","备注testing...");
        map.put("userId","5001021");
        map.put("couponId",couponId);
        rc.body(JSONArray.toJSON(map));
        System.out.println("发放优惠券：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 3)
    public void querySendUSerCouponsListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/querySendUSerCouponsList");
        rc.params("pageNum",1);
        rc.params("pageSize",20);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询定向发送优惠券接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
