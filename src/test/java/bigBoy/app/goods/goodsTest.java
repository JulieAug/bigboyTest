package bigBoy.app.goods;

import bigBoy.app.basicTest;
import bigBoy.app.user.loginTest;
import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

import java.math.BigInteger;

/**
 * @program: hupuTest
 * @description: 热搜关键词
 * @author: zhuli
 * @create: 2020-11-10 19:10
 **/
public class goodsTest {
    private String apiDomain = basicTest.apiDomain;

    private String project = basicTest.project;

    private String version = basicTest.version;

    private final String getAllMenuGoods = "/appapi/"+project+"/"+version+"/goods/getAllMenuGoods";

    private final String getChannelInfo = "/appapi/"+project+"/"+version+"/goods/getChannelInfo";

    private final String getCollectionInfo = "/appapi/"+project+"/"+version+"/goods/getCollectionInfo";

    private final String getCommentsByChannel = "/appapi/"+project+"/"+version+"/goods/getCommentsByChannel";

    private final String getFlow = "/appapi/"+project+"/"+version+"/goods/getFlow";

    private final String getGoodsByCollection = "/appapi/"+project+"/"+version+"/goods/getGoodsByCollection";

    private final String getRecommendList = "/appapi/"+project+"/"+version+"/goods/getRecommendList";

    private final String hotSearchKeywords = "/appapi/"+project+"/"+version+"/goods/hotSearchKeyWords";

    private final String searchByKeywords = "/appapi/"+project+"/"+version+"/goods/searchByKeyWords";

//    private BigInteger goodsId = DBUtil.getForValue("select id from goods_pro order by ct asc limit 1;","bigBoyData.properties");

    private Long channelId = DBUtil.getForValue("select id from channel_pro order by ct asc limit 1;","bigBoyData.properties");

    private Integer[] categorys = new Integer[]{0,1,2,3,4};//分类 1：高达/2：手办/3：模型/4：乐高（没有值代表全部类型）
    private Integer[] sortCategorys = new Integer[]{1,2,3};//排序类目 1：综合/2：销量/3：价格
    private Integer[] sortTypes = new Integer[]{1,2};//排序方式 1：升序/2：降序

    private String token;

    @BeforeClass
    public void setUp() throws Exception {
        token = new loginTest().checkSecurityCodeSTest();
    }


    /**
     * 获取商品分类目录及商品
     * @param category
     * @throws Exception
     */
    @Test(priority = 1,dataProvider = "category",description = "获取商品分类目录及商品")
    public void getAllMenuGoodsTest(Integer category) throws Exception{
        RestClient rc = new RestClient(apiDomain,getAllMenuGoods);
        rc.params("category",category);
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 查询渠道店铺详情
     * @throws Exception
     */
    @Test(priority = 2,description = "查询渠道店铺详情")
    public void getChannelInfoTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,getChannelInfo);
        rc.params("id",channelId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        String channelName = DBUtil.getForValue("select shop_name from channel_pro order by ct asc limit 1;","bigBoyData.properties");
        Assert.assertTrue(jsonObj.getString("data").contains(channelName));
    }

    /**
     * 查询聚合页详情
     * @throws Exception
     */
    @DataProvider
    public static Object[][] collection(){
        return new Object[][]{
                {"ips",DBUtil.getForValue("select id from ips_pro order by ct asc limit 1;","bigBoyData.properties")},
                {"series",DBUtil.getForValue("select id from series_pro order by ct asc limit 1;","bigBoyData.properties")},
                {"factory",DBUtil.getForValue("select id from factory_pro order by ct asc limit 1;","bigBoyData.properties")}
        };
    }
    @Test(priority = 3,dataProvider = "collection",description = "查询聚合页详情")
    public void getCollectionInfoTest(String type,BigInteger collectionId) throws Exception{
        RestClient rc = new RestClient(apiDomain,getCollectionInfo);
        rc.params("type",type);
        rc.params("collectionId",collectionId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertTrue(jsonObj.getString("data").contains("name"));
    }


    /**
     * 查询渠道店铺评论列表
     * @throws Exception
     */
    @Test(priority = 4,description = "查询渠道店铺评论列表")
    public void getCommentsByChannelTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,getCommentsByChannel);
        rc.params("channelId",channelId);
        rc.params("pageSize","1");
        rc.params("pageNum","10");
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 获取目录下的商品
     * @throws Exception
     */
    @Test(priority = 5,description = "获取目录下的商品")
    public void getFlowTest() throws Exception{
        menuType[] menu = new menuType[]{
                new menuType(1,"系列",
                        DBUtil.getForValue("SELECT target_id from sku_catalog where type=2 and target_id is not null limit 1;","bigBoy.properties"),
                        null,null),//高达
                new menuType(2,"IP",
                        DBUtil.getForValue("SELECT target_id from sku_catalog where type=3 and target_id is not null limit 1;","bigBoy.properties"),
                        null,null),//手办
                new menuType(3,"模型类型",
                        DBUtil.getForValue("SELECT target_id from sku_catalog where type=3 and target_id is not null limit 1;","bigBoy.properties"),
                        null,null),//模型
                new menuType(4,"IP",
                        DBUtil.getForValue("SELECT target_id from sku_catalog where type=4 and target_id is not null limit 1;","bigBoy.properties"),
                        null,null)//乐高
        };

        for (menuType menuType:menu){
            for (Integer sortCategory:sortCategorys){
                for (Integer sortType:sortTypes){
                    System.out.println("category="+menuType.getCategorys()+",menuType2="+menuType.getMenuType2()+",sortCategory="+sortCategory+",sortType="+sortType);
                    getFlow(menuType.getCategorys(),
                            menuType.getMenuType2(), menuType.getMenuTargetId2(),
                            menuType.getMenuType3(), menuType.getMenuTargetId3(),
                            sortCategory, sortType);
                }
            }
        }
    }
    public void getFlow(Integer category, String menuType2, long menuTargetId2,
                        String menuType3, Long menuTargetId3,
                        Integer sortCategory, Integer sortType) throws Exception{
        RestClient rc = new RestClient(apiDomain,getFlow);
        rc.params("category",category);//分类 1：高达/2：手办/3：模型/4：乐高（没有值代表全部类型）
        rc.params("menuType2",menuType2);
        rc.params("menuType3",menuType3);
        rc.params("menuTargetId2",menuTargetId2);
        rc.params("menuTargetId3",menuTargetId3);
        rc.params("sortCategory",sortCategory);//排序类目 1：综合/2：销量/3：价格
        rc.params("sortType",sortType);//排序方式 1：升序/2：降序
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     *查询聚合页商品列表
     * @throws Exception
     */
    @Test(priority = 6,description = "查询聚合页商品列表")
    public  void getGoodsByCollectionTest() throws Exception {
        Long ipsId =null;
        String ipsIds = DBUtil.getForValue("select ip_ids from goods_pro where ip_ids is not null limit 1;","bigBoyData.properties");
        if(ipsIds != null && !ipsIds.trim().equals("")){
            ipsId = new Long(ipsIds.split(",")[0]);
        }
        Long seriesId =  DBUtil.getForValue("select series_id from goods_pro where series_id is not null limit 1;","bigBoyData.properties");
        Long factoryId = DBUtil.getForValue("select factory_id from goods_pro where factory_id is not null limit 1;","bigBoyData.properties");

        goodsCollection[] goodsCollection = new goodsCollection[]{
                new goodsCollection("ips", ipsId),
                new goodsCollection("series", seriesId),
                new goodsCollection("factory", factoryId)
        };

        for(goodsCollection key:goodsCollection){
            for(Integer category : categorys){
                for(Integer sortCategory : sortCategorys){
                    for(Integer sortType : sortTypes){
                        System.out.println("type="+key.getType()+",collectionId="+key.getCollectionId()+",category="+category+",sortCategory="+sortCategory+",sortType="+sortType);
                        getGoodsByCollection(category,sortCategory,sortType,key.getType(),key.getCollectionId());
                    }
                }
            }
        }
    }

    public void getGoodsByCollection(Integer category, Integer sortCategory,Integer sortType,String type,Long collectionId) throws Exception{
        RestClient rc = new RestClient(apiDomain,getGoodsByCollection);
        rc.params("category",category);//分类 1：高达/2：手办/3：模型/4：乐高（没有值代表全部类型）
        rc.params("sortCategory",sortCategory);//排序类目 1：综合/2：销量/3：价格
        rc.params("sortType",sortType);//排序方式 1：升序/2：降序
        rc.params("type",type);//类型ips、series、factory
        rc.params("collectionId",collectionId);
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 获取商品推荐列表
     * @throws Exception
     */
    @DataProvider
    public static Object[][] category(){
        return new Object[][]{
                {null},{1},{2},{3},{4}
        };
    }
    @Test(priority = 7,dataProvider = "category",description = "获取商品推荐列表")
    public void getRecommendListTest(Integer category) throws Exception{
        RestClient rc = new RestClient(apiDomain,getRecommendList);
        rc.params("category",category);//分类 1：高达/2：手办/3：模型/4：乐高（没有值代表全部类型）
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 热搜关键词
     * @throws Exception
     */
    @Test(priority = 8,description = "热搜关键词")
    public void hotSearchKeywordsTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,hotSearchKeywords);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



    /**
     * 根据关键词搜索商品
     * @throws Exception
     */

    @Test(priority = 9,description = "根据关键词搜索商品")
    public  void searchByKeywordsTest() throws Exception {
        String[] keywords = new String[]{"高达","乐高","手办","模型"};
        for(String keyword:keywords){
            for(Integer category : categorys){
                for(Integer sortCategory : sortCategorys){
                    for(Integer sortType : sortTypes){
                        System.out.println("keyword="+keyword+";category=" +category + ";sortCategory="+sortCategory+";sortType="+sortType);
                        searchByKeywords(keyword,category,sortCategory,sortType);
                    }
                }
            }
        }

    }
    public  void searchByKeywords(String keyword,Integer category,Integer sortCategory,Integer sortType) throws Exception{
        RestClient rc = new RestClient(apiDomain,searchByKeywords);
        rc.params("keyword",keyword);
        rc.params("category",category);//分类 1：高达/2：手办/3：模型/4：乐高（没有值代表全部类型）
        rc.params("sortCategory",sortCategory);//排序类目 1：综合/2：销量/3：价格
        rc.params("sortType",sortType);//排序方式 1：升序/2：降序
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}

