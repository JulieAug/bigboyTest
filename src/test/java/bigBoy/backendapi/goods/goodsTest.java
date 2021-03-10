package bigBoy.backendapi.goods;

import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hupuTest
 * @description: 搜索商品接口
 * @author: zhuli
 * @create: 2020-10-26 16:21
 **/
public class goodsTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private BigInteger goodsId = DBUtil.getForValue("select id from goods_pro order by ct asc limit 1;","bigBoyData.properties");

    private BigInteger categoryAssociateFactoryId;

    private BigInteger seriesId = DBUtil.getForValue("select id from series_pro order by id asc limit 1;","bigBoyData.properties");

    private String shopGoodsUrl ="bigboy://web/detail?url=http%3a%2f%2fwww.51testing.com%2f";

    /**
     * 根据商品id获取商品信息
     * @throws Exception
     */
    @Test(priority = 1)
    public void queryGoodsInfoByIdTest() throws Exception{
        RestClient rc = new RestClient(apiDomain, "/backendapi/goods/queryGoodsInfoById");
        rc.params("id",goodsId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询商品接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
//        Assert.assertTrue(jsonObj.getString("data").contains("EG 高达系列 圣诞什锦点心套装 RX-78-2 圣诞色"));
    }

    /**
     * 根据keyword搜索商品信息
     * @throws Exception
     */
    @Test(priority = 2)
    public void searchGoodsTest() throws Exception{
        RestClient rc  = new RestClient(apiDomain,"/backendapi/goods/search");
        List<Object> keywordsList = new ArrayList<>();
        keywordsList.add("高达系列");
        rc.params("keywords",keywordsList);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
//        Assert.assertTrue(jsonObj.getString("data").contains("高达系列"));
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 根据关键词&类型查询商品信息
     * @throws Exception
     */
    @Test(priority = 3)
    public void searchByCategoryTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/searchByCategory");
        rc.params("category","高达");
        rc.params("pageSize",1);
        rc.params("pageNum",20);
        rc.params("keywords","高达系列");
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertTrue(jsonObj.getString("data").contains("高达"));
    }

    /**
     * 根据品牌id查询商品列表
     * @throws Exception
     */
    @Test(priority = 4)
    public void searchGoodsByFactoryIdTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/searchGoodsByFactoryId");
        categoryAssociateFactoryId = DBUtil.getForValue("select id from category_associate_factory order by id desc limit 1","bigBoy.properties");
        rc.params("id",categoryAssociateFactoryId);
        rc.params("pageSize",1);
        rc.params("pageNum",20);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
    }

    /**
     * 修改系列
     * @throws Exception
     */
    @Test(priority = 5)
    public void updateSeriesTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/series/update");
        Map<String,Object> map = new HashMap<>();
        map.put("category","高达分类测试"+ RandomUtil.getDigits(2));
        map.put("id",seriesId);
        map.put("intro","MG_testing...");
        map.put("logoUrl","https://bigboy-img.hoopchina.com.cn/manage-img/1604563493_668.jpg");
        map.put("name","高达名称"+RandomUtil.getDigits(2));
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        String categroy = DBUtil.getForValue("select category from series_pro where id ="+seriesId,"bigBoyData.properties");
    }

    /**
     * 修改商品基本信息
     * @throws Exception
     */
    @Test(priority = 6)
    public void updateGoodsInfo() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/update/info");
        Map<String,Object> map = new HashMap<>();
        map.put("id",goodsId);
        map.put("category","高达分类测试"+ RandomUtil.getDigits(2));
        map.put("intro","MG_testing...");
        map.put("price",99);
        map.put("productName","高达产品名称测试");
        map.put("saleVolume",300);
        map.put("ratio","1/100");//比例
        map.put("saleStatus","预售");//销售状态  在售/预售/暂无
        map.put("factoryId",5608);
        map.put("factoryName","万代南梦宫娱乐");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        String productName = DBUtil.getForValue("select product_name from goods_pro where id ="+goodsId,"bigBoyData.properties");
        Assert.assertTrue(productName.contains("高达产品名称测试"));
        String ratio = DBUtil.getForValue("select ratio from goods_pro where id ="+goodsId,"bigBoyData.properties");
        Assert.assertTrue(ratio.contains("1/100"));
    }


    /**
     * 修改商品图片信息
     * @throws Exception
     */
    @Test(priority = 8)
    public void updateGoodsPics() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/update/pics");
        Map<String,Object> map = new HashMap<>();
        map.put("id",goodsId);
//        map.put("logoUrl","");
        List<Object> detailPicsList	 = new ArrayList<>();
        detailPicsList.add("http://bbs.78dm.net/data/attachment/forum/202010/27/181944eh758vv3cawuah5y.jpg,http://bbs.78dm.net/data/attachment/forum/202010/27/181945py622z557sziqt1i.jpg,http://bbs.78dm.net/data/attachment/forum/202010/27/181945pq6cmq90h6116f9s.jpg,http://bbs.78dm.net/data/attachment/forum/202010/27/181946gwdj2kjkszdxbhrf.jpg");
        map.put("detailPics",detailPicsList);//细节图
        List<Object> headPicsList = new ArrayList<>();
        headPicsList.add("http://bbs.78dm.net/data/attachment/forum/202010/25/002650fmaxgi3ta3w2pgag.jpg");
        map.put("headPics",headPicsList);//头图
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 修改商品可见性
     * @throws Exception
     */
    @Test(priority = 9)
    public void updateGoodsStatus() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/update/status");
        Map<String,Object> map = new HashMap<>();
        map.put("id",goodsId);
        map.put("showStatus",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 商品评论上下线
     * @throws Exception
     */
    @Test(priority = 10)
    public void goodsCommentsOnlineTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/comments/online");
        Map<String,Object> map = new HashMap<>();
        map.put("id",1);//channel_comments_pro表中id
        map.put("status",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 新增商品渠道
     * @throws Exception
     */
    @Test(priority = 11)
    public void addChannelTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/addChannel");
        Map<String,Object> map = new HashMap<>();
        map.put("channelType","dewu");
        map.put("goodsId","478");
        map.put("shopGoodsUrl",shopGoodsUrl);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


}