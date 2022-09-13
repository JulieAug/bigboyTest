package bigBoy.backendapi.homepage;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 浮窗资源位
 * @author: zhuli
 * @create: 2021-05-24 17:00
 **/
public class floatingWindowTest {

    @Test(priority = 1)
    public void addCarouseTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/floatingWindow/add");
        Map<String, Object> map = new HashMap<>();
        map.put("name","浮窗资源位"+ RandomUtil.getDigits(2));
        map.put("image","http://movie-img.hupu.com/movie/8063fe42-c295-4e5d-8343-211a636a1ee9");
        map.put("schema","scheme");
        map.put("pageType",1);
        map.put("startTime","2021-06-06 00:00:00");
        map.put("endTime","2021-06-07 00:00:00");
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增浮窗资源位详细信息为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用新增浮窗资源位接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
