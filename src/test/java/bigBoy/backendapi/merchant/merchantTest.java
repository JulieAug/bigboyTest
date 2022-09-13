package bigBoy.backendapi.merchant;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @program: zaoApiTest
 * @description: 商家
 * @author: zhuli
 * @create: 2022-04-27 16:38
 **/
public class merchantTest {
    /**
     * 添加商家
     * @throws Exception
     */
    @Test(priority = 1)
    public void addMerchant() throws Exception{
        for (int i = 1; i < 2; i++) {
            addMerchantTest();
        }
    }
    public void addMerchantTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/merchant/add");
        HashMap<String,Object> map = new HashMap<>();

        //商家基础信息
        map.put("name","商家测试"+ RandomUtil.getDigits(4));
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
        map.put("contractStartTime","2022-04-26 22:17:33");
        map.put("contractEndTime","2023-04-26 22:17:33");
        List<Object> serviceTagList = new ArrayList<>();
        serviceTagList.add("测试标签a");
        serviceTagList.add("测试标签b");
        serviceTagList.add("测试标签c");
        map.put("serviceTagList",serviceTagList);
        map.put("userAccount","zhuli");
        map.put("userPwd","123456");

        //联系人信息
        map.put("contactName","朱莉");
        map.put("contactPhone","18260356798");
        map.put("contactEmail","18260356798@qq.com");
        map.put("contactIdCardNo","320925199101021234");
        //结算信息
        map.put("accountingRate",20);
        map.put("payPeriod",30);
        map.put("alipayAccount","18260356798");
        map.put("alipayMerchantId","18260356798");
        map.put("settlePrice",20);

        //商家售后信息
        map.put("logisticsAddress","朱莉,18260356798,上海四行仓库");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加商家信息，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 添加商家商品
     * @throws Exception
     */
    @Test(priority = 2)
    public void addMerchantGoods() throws Exception{
        for (int i = 1; i < 31; i++) {
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/merchantGoods/add");
            HashMap<String,Object> map = new HashMap<>();
            map.put("merchantId","26");
            map.put("goodsId",i);
            map.put("price","1");
            map.put("online",1);
            map.put("stock",10);
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map)+"，添加商家信息，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }

    }
}
