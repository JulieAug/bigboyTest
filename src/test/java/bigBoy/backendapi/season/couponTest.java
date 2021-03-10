package bigBoy.backendapi.season;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;
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

    @Test(priority = 1)
    public void addCouponTest() throws Exception{
        for(int i=0;i<10;i++){
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/coupon/add");
            Map<String, Object> map = new HashMap<>();
            Integer discount = parseInt(RandomUtil.getDigits(1));
            if(discount==0){
                map.put("discount",8);
            }else {
                map.put("discount", discount);
            }
            map.put("thresholdPrice",RandomUtil.getDigits(3));
            map.put("title","清仓特价"+RandomUtil.getDigits(4));
            map.put("type",1);
            rc.body(JSONArray.toJSON(map));
            System.out.println("新增优惠券：" + map);
            JSONObject jsonObj = rc.post();
            System.out.println("接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }
}
