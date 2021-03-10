package bigBoy.backendapi.homepage;

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

import static org.testng.Assert.assertEquals;

/**
 * @program: hupuTest
 * @description: 展览页
 * @author: zhuli
 * @create: 2020-10-29 18:22
 **/
public class showTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private String showName = "展览名称测试"+ RandomUtil.getDigits(4);

    private String displayWindowName;

    private String productionName = "作品名称测试"+RandomUtil.getDigits(4);

    private Long showId;

    private Long displayWindowId;

    private BigInteger goodsId = DBUtil.getForValue("SELECT id FROM goods ORDER BY ct asc limit 1;","bigBoyData.properties");

    private Long productionId;

    private BigInteger associateGoodsId;

    /**
     * 添加展览
     * @throws Exception
     */
    @Test(priority = 1)
    public void addShowTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/trade/show/add");
        Map<String,Object> map = new HashMap<>();
        map.put("name",showName);
        map.put("intro","展览简介测试"+RandomUtil.getDigits(4));
        map.put("imgBig","http://movie-img.hupu.com/movie/b6929ea14533e8e2bc33597db7247f11271687.jpg");
        map.put("imgSmall","http://movie-img.hupu.com/movie/b6929ea14533e8e2bc33597db7247f11271687.jpg");
        map.put("status",0);
        map.put("startDate","2020-10-21 11:00:00");
        map.put("endDate","2021-01-31 11:00:00");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        showId = DBUtil.getForValue("select id from trade_show where name = '"+showName+"' order by  create_dt desc limit 1","bigBoy.properties");
        System.out.println(showId);
    }

    /**
     * 查看单个展览信息
     * @throws Exception
     */
    @Test(priority = 2)
    public void showQueryInfoTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/trade/show/queryInfo");
        rc.params("id",showId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询单个展览接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertEquals(jsonObj.getJSONObject("data").getString("name"),showName);
    }

    /**
     * 编辑某个展览信息
     * @throws Exception
     */
    @Test(priority = 3)
    public void updateShowTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/trade/show/update");
        Map<String,Object> map = new HashMap<>();
        map.put("id",showId);
        map.put("img","");
        map.put("intro","展会简介测试...");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



    /**
     * 添加橱窗信息
     * @throws Exception
     */

    @Test(priority = 4)
    public void adddisplayWindowTest() throws Exception{
        for(int i=0;i<1;i++){
            adddisplayWindow();
        }
    }

    public void adddisplayWindow() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/display/window/add");
        Map<String,Object> map = new HashMap<>();
        map.put("tradeShowId",showId);
        displayWindowName = "橱窗名称"+RandomUtil.getDigits(4);
        map.put("name",displayWindowName);
        map.put("sort",10);
        map.put("status",0);
        List<Object> showImgUrlList	 = new ArrayList<>();
        showImgUrlList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1604563284_477.jpeg");
        showImgUrlList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1604563493_668.jpg");
        showImgUrlList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1604563532_2.jpeg");
        showImgUrlList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1604563577_800.jpeg");
        showImgUrlList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1604562450_610.jpg");
        map.put("showImgUrlList",showImgUrlList);
        map.put("userName","大男孩用户"+RandomUtil.getDigits(4));//用户昵称
        map.put("avatarUrl","https://bigboy-img.hoopchina.com.cn/manage-img/1604051030_866.png");//用户头像
        map.put("story","橱窗故事测试ing...");
        map.put("topic","高达系列话题");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        displayWindowId =  DBUtil.getForValue("select id from display_window where trade_show_id ="+showId,"bigBoy.properties");
    }


    /**
     * 更新橱窗信息
     * @throws Exception
     */
    @Test(priority = 5)
    public void updatedisplayWindowTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/display/window/update");
        Map<String,Object> map = new HashMap<>();
        map.put("id",displayWindowId);
        map.put("sort",8);
        map.put("status",1);
        map.put("story","橱窗故事Updating...");
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 商品/帖子关联
     * @throws Exception
     */
    @Test(priority = 6)
    public void  addAssociateTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/associate/addAssociate");
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",goodsId);
        map.put("targetId",displayWindowId);
        map.put("type",1);
        map.put("goodsImg","https://bigboy-img.hoopchina.com.cn/manage-img/imgShow@3x-1604025710.png");
        rc.body(JSONArray.toJSON(map));
        System.out.println(map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        associateGoodsId = DBUtil.getForValue("select id from goods_associate where goods_id = '"+goodsId+"' and target_id = '"+displayWindowId+"'  order by  create_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 查看商品/帖子关联
     * @throws Exception
     */
    @Test(priority = 7)
    public void queryAssociateList() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/associate/queryAssociateList");
        rc.params("targetId",displayWindowId);
        rc.params("type",1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看关联列表，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



    /**
     * 商品/帖子 取消关联
     * @throws Exception
     */
    @Test(priority = 7)
    public void deleteAssociateTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/associate/deleteAssociate");
        Map<String,Object> map = new HashMap<>();
        map.put("id",associateGoodsId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 删除橱窗信息
     * @throws Exception
     */
    @Test(priority = 9)
    public void deletedisplayWindowTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/display/window/delete");
        Map<String,Object> map = new HashMap<>();
        map.put("id",displayWindowId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 删除新建的展览数据
     * @throws Exception
     */
    @Test(priority = 10)
    public void deleteShowTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/trade/show/delete");
        Map<String,Object> map = new HashMap<>();
        map.put("id",showId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertNotNull(showId,"该条展览数据已被删除...");
    }

//    @Test(priority = 6)
//    新建橱窗下的作品==废弃
    private void addProductionTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/production/window/add");
        Map<String,Object> map = new HashMap<>();
        map.put("name",productionName);
        map.put("tradeShowId",showId);
        map.put("displayWindowId",displayWindowId);
        map.put("goodsId",goodsId);
        map.put("goodsImg","http://bbs-attachment-cdn.78dm.net/upload/2020/10/63a2d41579c44f4798b5f616ad869c9d");
        map.put("intro","作品简介准备ing...");
        List<Object> imgUrlList	 = new ArrayList<>();
        imgUrlList.add("http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
        imgUrlList.add("http://movie-img.hupu.com/movie/8063fe42-c295-4e5d-8343-211a636a1ee9");
        imgUrlList.add("http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
        imgUrlList.add("http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
        imgUrlList.add("http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
        map.put("imgUrlList",imgUrlList);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        productionId = DBUtil.getForValue("SELECT id FROM production_show where display_window_id = "+displayWindowId,"bigBoy.properties");
    }

//    @Test(priority = 7)
//    编辑橱窗下的作品==废弃
    public void updateProductionTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/production/window/update");
        Map<String,Object> map = new HashMap<>();
        map.put("id",productionId);
        map.put("name",productionName);
        map.put("tradeShowId",showId);
        map.put("displayWindowId",displayWindowId);
        map.put("goodsId",goodsId);
        map.put("goodsImg","http://bbs-attachment-cdn.78dm.net/upload/2020/10/63a2d41579c44f4798b5f616ad869c9d");
        map.put("intro","作品简介Updating...");
        List<Object> imgUrlList	 = new ArrayList<>();
        imgUrlList.add("http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
        imgUrlList.add("http://movie-img.hupu.com/movie/4d40d75ddc453a47b6067d18c50c5ff356724.jpg");
        imgUrlList.add("http://movie-img.hupu.com/movie/d083dc5440d47473d8de419e6ce879d0484155.jpg");
        map.put("imgUrlList",imgUrlList);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

//    @Test(priority = 8)
//    删除橱窗下的作品==废弃
    public void deleteProductionTest() throws Exception {
        RestClient rc = new RestClient(apiDomain, "/backendapi/production/window/delete");
        Map<String, Object> map = new HashMap<>();
        map.put("id", productionId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
