package bigBoy.backendapi;

import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: hupuTest
 * @description: 热搜关键词配置
 * @author: zhuli
 * @create: 2020-11-10 10:37
 **/
public class hotSearchKeywordsTest {
    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private String hotSearchkeywordsName;

    private Long hotSearchkeywordsId;

    /**
     * 添加热搜词
     * @throws Exception
     */
    @Test(priority = 1)
    public void addHotSearchKeywordsTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotSearchKeywords/addHotSearchKeywords");
        Map<String,Object> map = new HashMap<>();
        hotSearchkeywordsName = "高达MG"+ RandomUtil.getDigits(2);
        map.put("keywords",hotSearchkeywordsName);
        map.put("sort",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        hotSearchkeywordsId = DBUtil.getForValue("select id from hot_search_keywords where keywords = '"+hotSearchkeywordsName+"'order by create_dt desc limit 1","bigBoy.properties");
        System.out.println(hotSearchkeywordsId);
    }

    /**
     * 修改热搜词
     * @throws Exception
     */
    @Test(priority = 2)
    public void updateHotSearchKeywordsTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotSearchKeywords/updateHotSearchKeywords");
        Map<String,Object> map = new HashMap<>();
        map.put("id",hotSearchkeywordsId);
        hotSearchkeywordsName = "热搜词测试"+ RandomUtil.getDigits(2);
        map.put("keywords",hotSearchkeywordsName);
        map.put("sort",2);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        hotSearchkeywordsName = DBUtil.getForValue("select keywords from hot_search_keywords where id = "+hotSearchkeywordsId,"bigBoy.properties");
    }

    /**
     * 查询热搜词
     * @throws Exception
     */
    @Test(priority = 3)
    public void queryHotSearchKeywordsListTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotSearchKeywords/queryListBackend");
        rc.params("keywords",hotSearchkeywordsName);
        rc.params("pageNum","1");
        rc.params("pageSize","10");
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");

    }

    /**
     * 删除热搜词
     * @throws Exception
     */
    @Test(priority = 4)
    public void deleteHotSearchKeywordsTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotSearchKeywords/delete");
        Map<String,Object> map = new HashMap<>();
        map.put("id",hotSearchkeywordsId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
