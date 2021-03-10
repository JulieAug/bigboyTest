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
import java.util.HashMap;
import java.util.Map;

/**
 * @program: hupuTest
 * @description: 聚合页管理
 * @author: zhuli
 * @create: 2020-11-09 13:41
 **/
public class collectionsTest {
    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private String name;
    private BigInteger ipsId;
    private BigInteger seriesId;
    private BigInteger factoryId;
    private String ipsCollectionName;
    private String seriesCollectionName;
    private String factoryCollectionName;
    private BigInteger goodsId = DBUtil.getForValue("select id from goods_pro order by ct asc limit 1;","bigBoyData.properties");

    @DataProvider
    public static Object[][] collectionType(){
        return new Object[][]
                {
                        {"ips"},{"series"},{"factory"}
                };
    }

    @DataProvider
    public static Object[][] category(){
        return new Object[][]
                {
                        {"高达"},{"乐高"},{"手办"},{"模型"}
                };
    }

    /**
     * 品牌聚合页增删改查
     * @param collectionType
     * @throws Exception
     */
    @Test(priority = 1,dataProvider = "collectionType")
    public void collectionTest(String collectionType) throws Exception{
        createCollection(collectionType);
        ipsId = DBUtil.getForValue("select id from ips_pro where name = '"+name+"' order by  ct desc limit 1","bigBoyData.properties");
        ipsCollectionName = DBUtil.getForValue("select name from ips_pro where id = '"+ipsId+"' order by  ct desc limit 1","bigBoyData.properties");
        seriesId = DBUtil.getForValue("select id from series_pro where name = '"+name+"' order by  ct desc limit 1","bigBoyData.properties");
        seriesCollectionName = DBUtil.getForValue("select name from series_pro where id = '"+seriesId+"' order by  ct desc limit 1","bigBoyData.properties");
        factoryId = DBUtil.getForValue("select id from factory_pro where name = '"+name+"' order by  ct desc limit 1","bigBoyData.properties");
        factoryCollectionName = DBUtil.getForValue("select name from factory_pro where id = '"+factoryId+"' order by  ct desc limit 1","bigBoyData.properties");

        updateCollection(collectionType);//编辑品牌聚合页
        getGoodsBycollection(collectionType);
        addGoodsLink(collectionType);//品牌聚合页关联商品
        deleteGoodsLink(collectionType);//品牌聚合页删除关联商品
        onlineCollection(collectionType);//下线品牌聚合页
        tearDown();//删除品牌聚合页
    }

    /**
     * 根据不同品类搜索二级实体
     * @throws Exception
     */
    @Test(priority = 2,dataProvider = "category")
    public void searchCollection(String category) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/collections/v1/search");
        rc.params("category",category);
//        List<Object> keywordsList = new ArrayList<>();
//        keywordsList.add("");
        rc.params("keywords","");
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 新增品牌聚合页
     * @throws Exception
     */
    public void createCollection(String collectionType) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/collections/v1/create");
        Map<String,Object> map = new HashMap<>();
        map.put("collectionType",collectionType);// ips/series/factory
        map.put("intro",collectionType+"聚合页简介"+ RandomUtil.getDigits(2));
        map.put("logoUrl","https://bigboy-img.hoopchina.com.cn/manage-img/1604563284_477.jpeg");
        name=collectionType+"聚合页"+ RandomUtil.getDigits(2);
        map.put("name",name);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 编辑品牌聚合页
     * @throws Exception
     */
    public void updateCollection(String collectionType) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/collections/v1/update");
        Map<String,Object> map = new HashMap<>();
        if(ipsId!=null){
            map.put("id",ipsId);
        }else if(seriesId!=null){
            map.put("id",seriesId);
        }else if(factoryId!=null){
            map.put("id",factoryId);
        }
        map.put("collectionType",collectionType);// ips/series/factory
        map.put("name",collectionType+"聚合页名称编辑"+ RandomUtil.getDigits(2));
        map.put("logoUrl","https://bigboy-img.hoopchina.com.cn/manage-img/1604563284_477.jpeg");
        map.put("intro",collectionType+"聚合页简介编辑"+ RandomUtil.getDigits(2));
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 增加商品和分类的关联
     * @throws Exception
     */
    public void addGoodsLink(String collectionType) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/add/link");
        Map<String,Object> map = new HashMap<>();
        if(ipsId!=null){
            map.put("collectionId",ipsId);
            map.put("collectionName",ipsCollectionName);
        }else if(seriesId!=null){
            map.put("collectionId",seriesId);
            map.put("collectionName",seriesCollectionName);
        }else if(factoryId!=null){
            map.put("collectionId",factoryId);
            map.put("collectionName",factoryCollectionName);
        }
        map.put("collectionType",collectionType);
        map.put("id",goodsId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 删除商品和分类的关联
     * @throws Exception
     */
    public void deleteGoodsLink(String collectionType) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/delete/link");
        Map<String,Object> map = new HashMap<>();
        if(ipsId!=null){
            map.put("collectionId",ipsId);
            map.put("collectionName",ipsCollectionName);
        }else if(seriesId!=null){
            map.put("collectionId",seriesId);
            map.put("collectionName",seriesCollectionName);
        }else if(factoryId!=null){
            map.put("collectionId",factoryId);
            map.put("collectionName",factoryCollectionName);
        }
        map.put("collectionType",collectionType);
        map.put("id",goodsId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");

    }


    /**
     * 上下线品牌聚合页
     * @param collectionType
     * @throws Exception
     */
    public void onlineCollection(String collectionType) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/collections/v1/online");
        Map<String,Object> map = new HashMap<>();
        if(ipsId!=null){
            map.put("id",ipsId);
        }else if(seriesId!=null){
            map.put("id",seriesId);
        }else if(factoryId!=null){
            map.put("id",factoryId);
        }
        map.put("collectionType",collectionType);// ips/series/factory
        map.put("status",false);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 根据不同二级实体获取商品列表
     * @throws Exception
     */
    public void getGoodsBycollection(String collectionType) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/collections/v1/getGoodsByCollection");
        rc.params("category",1);
        rc.params("collectionType",collectionType);
        if(ipsId!=null){
            rc.params("id",ipsId);
        }else if(seriesId!=null){
            rc.params("id",seriesId);
        }else if(factoryId!=null){
            rc.params("id",factoryId);
        }
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 删除品牌聚合页记录
     */
    public void tearDown(){
        if(ipsId!=null){
            DBUtil.update("delete from ips_pro order by  ut desc limit 1","bigBoyData.properties");
        }else if(seriesId!=null){
            DBUtil.update("delete from series_pro order by  ut desc limit 1","bigBoyData.properties");
        }else if(factoryId!=null){
            DBUtil.update("delete from factory_pro order by ut desc limit 1","bigBoyData.properties");
        }
    }
}