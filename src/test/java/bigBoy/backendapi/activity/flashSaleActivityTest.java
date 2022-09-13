package bigBoy.backendapi.activity;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @program: zaoApiTest
 * @description: 秒杀活动
 * @author: zhuli
 * @create: 2021-07-13 14:21
 **/
public class flashSaleActivityTest {

    private BigInteger flashSaleActivitydsId;

    /**
     * 添加秒杀活动
     * @throws Exception
     */
    @Test(priority = 1)
    public void addFlashSaleActivity() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/flashSaleActivity/add");

        HashMap<String,Object> map = new HashMap<>();
        map.put("startTime","2021-07-16 21:00:00");
        map.put("endTime","2021-07-16 21:30:00");
        map.put("color","");
        map.put("titleImg","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("backImg","https://bigboy-img.hoopchina.com.cn/manage-img/1626069919_796_width_658_height_1026.jpeg");

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("新增秒杀，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        flashSaleActivitydsId = DBUtil.getForValue("SELECT id FROM flash_sale_activity order by create_dt desc limit 1","bigBoy.properties");
    }


    /**
     * 关联秒杀商品
     * @throws Exception
     */
    @Test(priority = 2)
    public void addGoods() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/flashSaleActivity/addGoods");

        HashMap<String,Object> map = new HashMap<>();
        map.put("flashSaleActivityId",flashSaleActivitydsId);
//        map.put("flashSaleActivityId",33);
        map.put("goodsId",19);
        map.put("price",1);
        map.put("totalCount",10);
        map.put("purchaseLimit",1);

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("关联商品，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
