package bigBoy.backendapi.goods;

import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: hupuTest
 * @description: 商品资源位管理
 * @author: zhuli
 * @create: 2020-11-05 17:10
 **/
public class categoryAssociateFactoryTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private String scheme ="bigboyapp://web/detail?url=http%3a%2f%2fwww.baidu.com%2f";
    private String factoryName = "资源位"+ RandomUtil.getDigits(2);

    private BigInteger categoryAssociateFactoryId;

    @Test(priority = 1,dataProvider = "category")
    public void categoryAssociateFactoryTest(String factoryName,String categoryName,Integer linkType,Integer sort,String linkVal,String factoryImg) throws Exception{
        for(int i=0;i<4;i++) {
            addCategoryAssociateFactoryTest(factoryName+RandomUtil.getDigits(2),categoryName,linkType,sort,linkVal,factoryImg);
            queryList();//查询商品资源位管理列表
            updateCategoryAssociateFactoryTest();//编辑商品资源位
            deleteCategoryAssociateFactoryTest();//删除商品资源位
        }
    }

    /**
     * 不同类目tab下添加资源位
     * @throws Exception
     */

    @DataProvider
    public static Object[][] category(){
        return new Object[][]{
                //scheme跳转
                {"推荐资源位","推荐",0,1,"bigboy://web/detail?url=http%3a%2f%2fwww.51testing.com%2f","https://bigboy-img.hoopchina.com.cn/manage-img/1604563284_477.jpeg"},
                {"高达资源位","高达",0,2,"bigboy://web/detail?url=http%3a%2f%2fwww.baidu.com%2f","https://bigboy-img.hoopchina.com.cn/manage-img/1604564275_107.jpeg"},
                {"乐高资源位","乐高",0,3,"bigboy://web/detail?url=http%3a%2f%2fwww.taobao.com%2f","https://bigboy-img.hoopchina.com.cn/manage-img/1604562025_659.jpeg"},
                {"手办资源位","手办",0,4,"bigboy://web/detail?url=http%3a%2f%2fwww.qq.com%2f","https://bigboy-img.hoopchina.com.cn/manage-img/1604563030_804.jpg"},
                {"模型资源位","模型",0,5,"bigboy://web/detail?url=http%3a%2f%2fwww.youku.com%2f","https://bigboy-img.hoopchina.com.cn/manage-img/1604561643_495.jpg"}

//                //商品id
//                {"推荐商品位","推荐",1,6,"85455","https://bigboy-img.hoopchina.com.cn/manage-img/1604563284_477.jpeg"},
//                {"高达商品位","高达",1,7,"85456","https://bigboy-img.hoopchina.com.cn/manage-img/1604564275_107.jpeg"},
//                {"乐高商品位","乐高",1,8,"85457","https://bigboy-img.hoopchina.com.cn/manage-img/1604562025_659.jpeg"},
//                {"手办商品位","手办",1,9,"85458","https://bigboy-img.hoopchina.com.cn/manage-img/1604563030_804.jpg"},
//                {"模型商品位","模型",1,10,"85459","https://bigboy-img.hoopchina.com.cn/manage-img/1604561643_495.jpg"}

//                品牌id
//                {"热门品牌位","热门",2,"85455","https://bigboy-img.hoopchina.com.cn/manage-img/1604563284_477.jpeg"},
//                {"高达品牌位","高达",2,"85456","https://bigboy-img.hoopchina.com.cn/manage-img/1604564275_107.jpeg"},
//                {"乐高品牌位","乐高",2,"85457","https://bigboy-img.hoopchina.com.cn/manage-img/1604562025_659.jpeg"},
//                {"手办品牌位","手办",2,"85458","https://bigboy-img.hoopchina.com.cn/manage-img/1604563030_804.jpg"},
//                {"模型品牌位","模型",2,"85459","https://bigboy-img.hoopchina.com.cn/manage-img/1604561643_495.jpg"}

        };
    }

    public void addCategoryAssociateFactoryTest(String factoryName,String categoryName,Integer linkType,Integer sort,String linkVal,String factoryImg) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/categoryAssociateFactory/add");
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName",categoryName);
        map.put("factoryImg",factoryImg);
        map.put("factoryName",factoryName);
        map.put("linkType",linkType);//跳转类型 0-schema 1-商品id 2-品牌id
//        map.put("linkVal", scheme+ URLEncoder.encode("http://www.51testing.com/", "UTF-8"));
        map.put("linkVal",linkVal);
        map.put("sort",sort);
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增品牌详细信息为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用新增品牌接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        categoryAssociateFactoryId = DBUtil.getForValue("select id from category_associate_factory where factory_name = '"+factoryName+"'order by create_dt desc limit 1","bigBoy.properties");
    }


    /**
     * 编辑品牌
     * @throws Exception
     */
    public void updateCategoryAssociateFactoryTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/categoryAssociateFactory/update");
        Map<String,Object> map = new HashMap<>();
        map.put("id",categoryAssociateFactoryId);
        map.put("categoryName","高达");
        map.put("factoryImg","https://bigboy-img.hoopchina.com.cn/manage-img/1604563284_477.jpeg");
        map.put("factoryName",factoryName);
        map.put("linkType",0);//跳转类型 0-schema 1-商品id 2-品牌id
        map.put("linkVal", scheme+ URLEncoder.encode("http://www.baidu.com/", "UTF-8"));
        rc.body(JSONArray.toJSON(map));
        System.out.println("编辑品牌详细信息为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用编辑品牌接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查询品牌管理列表
     * @throws Exception
     */
    public void queryList() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/categoryAssociateFactory/queryList");
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
//        Assert.assertTrue(jsonObj.getString("data").contains(factoryName));
    }

    /**
     * 删除品牌
     * @throws Exception
     */
    public void deleteCategoryAssociateFactoryTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/categoryAssociateFactory/delete");
        Map<String,Object> map = new HashMap<>();
        map.put("id",categoryAssociateFactoryId); rc.body(JSONArray.toJSON(map));
        System.out.println("删除品牌详细信息为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用删除品牌接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
