package bigBoy.backendapi.popwindow;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;
import util.DateUtil;

import java.util.Date;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 弹窗配置
 * @author: zhuli
 * @create: 2021-04-16 15:25
 **/
public class popWindowTest {
    /**
     * 新增弹窗配置
     * @throws Exception
     */
    @Test(priority = 1)
    public void addPopWindowTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/pop/window/add");
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","商城页"+ RandomUtil.getDigits(4));
        map.put("frequency",1);//0-每天 1-每次
        map.put("link","bigboy://goods/brand?type=series&collectionId=2");
        map.put("image","http://bigboy-img.hoopchina.com.cn/manage-img/e566900a-6507-4caf-af6e-665567b5fcfe_width_1120_height_660.png");
        map.put("positionType",1);//弹窗位置 0-首页 1-商城页
        map.put("userType",0);//用户群 0-全部用户 1-新用户
        map.put("startDt", "2021-04-11 00:00");
        map.put("endDt","2021-04-30 00:00");
        map.put("operate","zhuli013");
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增弹窗：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
