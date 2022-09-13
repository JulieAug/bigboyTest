package bigBoy.backendapi.label;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 标签测试
 * @author: zhuli
 * @create: 2021-03-24 23:08
 **/
public class labelTest {
    private String name;
    private BigInteger labelId;


    @DataProvider
    public static Object[][] type(){
        return new Object[][]{
                {0},{1},{2}
        };
    }
    @Test(priority = 1,dataProvider = "type",description = "添加标签")
    public void addLabelTest(Integer type) throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/label/add");
        Map<String, Object> map = new HashMap<>();
        name = "标签名称"+RandomUtil.getDigits(3);
        map.put("name",name);
        map.put("description","标签描述"+ RandomUtil.getDigits(3));
        map.put("type",type);//标签分类 0: 通用 1：社区 2：电商
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增标签：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        labelId = DBUtil.getForValue("select id from label where name ='"+name +"' order by update_dt desc limit 1","bigBoy.properties");
    }

    @Test(priority = 2,description = "修改标签")
    public void updateLabelTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/label/update");
        Map<String, Object> map = new HashMap<>();
        map.put("id",labelId);
        map.put("name",name);
        map.put("description","更新标签描述"+ RandomUtil.getDigits(3));
//        map.put("type",0);//标签分类 0: 通用 1：社区 2：电商
        rc.body(JSONArray.toJSON(map));
        System.out.println("更新标签：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    //@Test(priority = 3,description = "删除标签")
    public void deleteLabelTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/label/delete");
        Map<String, Object> map = new HashMap<>();
        map.put("id",labelId);
        rc.body(JSONArray.toJSON(map));
        System.out.println("删除标签：" + map);
        JSONObject jsonObj = rc.post();
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 4,dataProvider = "type",description = "查询标签")
    public void queryLabelTest(Integer type) throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/label/query");
        Map<String, Object> map = new HashMap<>();
//        map.put("name",name);
        map.put("pageNum",1);
        map.put("pageSize",10);
        List<Object> typesList = new ArrayList<>();
        typesList.add(type);
        map.put("types",typesList);
        rc.body(JSONArray.toJSON(map));
        System.out.println("查询标签：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
