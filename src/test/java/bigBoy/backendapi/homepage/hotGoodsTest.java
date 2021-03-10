package bigBoy.backendapi.homepage;

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

/**
 * @program: hupuTest
 * @description: 后台热门商品增删改查
 * @author: zhuli
 * @create: 2020-10-26 15:25
 **/
public class hotGoodsTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private BigInteger goodsId = DBUtil.getForValue("select id from goods_pro order by ct asc limit 1;","bigBoyData.properties");;

    private Long hotGoodsId;

    private String name;

    private String scheme ="bigboyapp://web/detail?url=";

    @DataProvider
    public static Object[][] status(){
        return new Object[][]{
                {1},{0},{1}
        };
    }

    /**
     * 新增热门商品
     */
    @Test(priority = 1)
    public void addHotGoodsTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotGoods/add");
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId",goodsId);
        map.put("name","热门商品test"+ RandomUtil.getDigits(4));
        map.put("imgSrc","http://movie-img.hupu.com/movie/8063fe42-c295-4e5d-8343-211a636a1ee9");
        String imgLink = scheme+ URLEncoder.encode("http://www.51testing.com/", "UTF-8");
        map.put("imgLink",imgLink);
        map.put("price",RandomUtil.getDigits(3));
        map.put("sales",RandomUtil.getDigits(4));
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增商品详细信息为；" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        hotGoodsId = DBUtil.getForValue("select id from hot_goods order by create_dt desc limit 1;","bigBoy.properties");
        System.out.println("新增热门商品的Id为：" + hotGoodsId);
    }


    /**
     * 更新热门商品信息
     */
    @Test(priority = 2,dataProvider = "status")
    public void updateHotGoodsTest(Number status) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotGoods/update");
        Map<String, Object> map = new HashMap<>();
        map.put("id",hotGoodsId);
        map.put("goodsId",goodsId);
        String nameUpdate = "商品更新名称"+RandomUtil.getDigits(3);
        map.put("name",nameUpdate);
        String imgSrcUpdate = "http://movie-img.hupu.com/movie/477608db-8272-400e-98f7-809f732932d8";
        map.put("imgSrc",imgSrcUpdate);
        map.put("sales","98");
        map.put("status",status);
        rc.body(JSONArray.toJSON(map));
        System.out.println("更新商品详细信息为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        //判断更新接口是否调用成功
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");

        name = DBUtil.getForValue("select name from hot_goods where id = "+hotGoodsId,"bigBoy.properties");
        if(name==null){
            System.out.println("未查到相应热门商品信息...");
        }else {
            Assert.assertTrue(name.contains(nameUpdate));
        }
        //判断图片地址，传参与入库的值是否一致
        String imgSrc = DBUtil.getForValue("select img_src from hot_goods where id = " + hotGoodsId, "bigBoy.properties");
        if(imgSrc ==null){
            System.out.println("未查到相应热门商品信息...");
        }else{
            Assert.assertTrue(imgSrc.contains(imgSrcUpdate));
        }
    }


    /**
     * 查询商品信息
     */
    @Test(priority = 3)
    public void queryHotGoodsListTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotGoods/queryList");
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询热门商品接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        //判断接口返回中是否已更新的图片名称
        Assert.assertTrue(jsonObj.getString("data").contains(name));
    }

    /**
     *删除热门商品
     */
    @Test(priority = 4)
    public void deleteHotGoodsTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/hotGoods/delete");
        Map<String,Object> map = new HashMap<>();
        map.put("id",hotGoodsId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用删除热门商品接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
