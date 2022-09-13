package hupu;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import bigBoyUtils.FormatUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @program: zaoApiTest
 * @description: 产品组测试
 * @author: zhuli
 * @create: 2021-10-25 14:00
 **/
public class productTest {
    private Long groupId;

    /**
     * 添加产品组测试
     * @throws Exception
     */
    @Test(priority = 1)
    public void insertProductGroupTest() throws Exception{
        for (int i = 0; i < 20; i++) {
            RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/productGroup/insertProductGroup");
            HashMap<String,Object> map = new HashMap<>();
            map.put("productName","产品组名称"+ RandomUtil.getDigits(2));
            map.put("remark","产品组备注"+ RandomUtil.getDigits(4));
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("添加产品组，接口返回信息：" +"\n"+jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
            groupId =Long.parseLong(jsonObj.getString("data"));
        }
    }

    /**
     * 新增产品组分类
     * @throws Exception
     */
    @Test(priority = 2)
    public void insertProductCategoryTest() throws Exception{
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/productCategory/insertProductCategory");
        HashMap<String,Object> map = new HashMap<>();
        map.put("categoryName","产品分类名称"+ RandomUtil.getDigits(2));
        map.put("groupId",groupId);
        map.put("remark","产品分类备注"+ RandomUtil.getDigits(4));
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加产品分类，接口返回信息：" +"\n"+jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 修改产品组
     * @throws Exception
     */
//    @Test(priority = 2)
    public void updateProductGroupTest() throws Exception{
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/productGroup/updateProductGroup");
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",groupId);
        map.put("productName","产品组名称upd"+ RandomUtil.getDigits(2));
        map.put("remark","产品组备注upd"+ RandomUtil.getDigits(4));
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("修改产品组，接口返回信息：" +"\n"+ jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 产品组列表查询
     * @throws Exception
     */
//    @Test(priority = 3)
    public void getProductGroupForListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/productGroup/getProductGroupForList");
        rc.params("pageNum",1);
        rc.params("pageSize",20);
        JSONObject jsonObj = rc.get();
        System.out.println("查询商品组列表，接口返回信息："+"\n"+ FormatUtil.formatJson(jsonObj.toString()));
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");

//        String title = DBUtil.getForValue("SELECT title FROM associate_thread where id="+targetId,"bigBoy.properties");
//        //校验数据库这个帖子标题与接口返回的帖子标题是否为同一个
//        Assert.assertEquals(jsonObj.getJSONObject("data").getString("title"),title);
    }


}
