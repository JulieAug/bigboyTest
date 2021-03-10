package bigBoy.backendapi.homepage;

import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @program: hupuTest
 * @description: 后台资源位增删改查
 * @author: zhuli
 * @create: 2020-10-26 14:30
 **/
public class carouseImgTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private final String  appDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-app-api";

    private Long carouseId;

    private String imgSrc;

    private String scheme ="bigboyapp://web/detail?url=";

//    @Test
    public void test() throws Exception{
        String url= scheme+URLEncoder.encode("https://www.baidu.com/", "UTF-8");
        System.out.println(url);
    }

    /**
     * 添加资源位
     */
    @Test(priority = 1)
    public void addCarouseTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/carouseImg/add");
        Map<String, Object> map = new HashMap<>();
        map.put("name","首页资源位"+ RandomUtil.getDigits(2));
        map.put("intro","资源位备注"+RandomUtil.getDigits(3));
        map.put("imgSrc","http://movie-img.hupu.com/movie/8063fe42-c295-4e5d-8343-211a636a1ee9");
        String imgLink = scheme+URLEncoder.encode("http://www.51testing.com/", "UTF-8");
        map.put("imgLink",imgLink);
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增资源位详细信息为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用新增资源位接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        carouseId = DBUtil.getForValue("select id from carousel_img order by create_dt desc limit 1;","bigBoy.properties");
        System.out.println("新增资源位的Id为：" + carouseId);
    }


    /**
     * 更新商品资源位
     * 1、查询出新增的资源位的id
     * 2、修改该id对应的资源位的图片地址&跳转链接
     * 3、判断数据库该id下对应的图片地址&跳转链接的值，与传参的值是否一致
     */
    @Test(priority = 2)
    public void updateCarouseTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/carouseImg/update");
        Map<String, Object> map = new HashMap<>();
        map.put("id",carouseId);
        String imgSrcUpdate = "http://movie-img.hupu.com/movie/d486830e-c41a-4e0e-bded-6cb5e141efa5";
        map.put("imgSrc",imgSrcUpdate);
        String imgLinkUpdate = scheme+URLEncoder.encode("https://v.qq.com/", "UTF-8");
        map.put("imgLink",imgLinkUpdate);
        map.put("status",0);//显示出资源位
        rc.body(JSONArray.toJSON(map));
        System.out.println("更新资源位详细信息为：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("调用更新资源位接口，接口返回信息：" + jsonObj);
        //判断更新接口是否调用成功
        assertEquals(jsonObj.getString("code"), "SUCCESS");

        //判断图片跳转链接，传参与入库的值是否一致
        String imgLink = DBUtil.getForValue("select img_link from carousel_img where id = " + carouseId, "bigBoy.properties");
        if(imgLink == null){
            System.out.println("未查到相应资源位信息...");
        }else {
            Assert.assertEquals(imgLink,imgLinkUpdate);
        }
        //判断图片地址，传参与入库的值是否一致
        imgSrc = DBUtil.getForValue("select img_src from carousel_img where id = "+carouseId,"bigBoy.properties");
        if(imgSrc == null){
            System.out.println("未查到相应资源位信息...");
        }else {
            assertTrue(imgSrc.contains(imgSrcUpdate));
        }
        System.out.println("数据库中更新资源位图片："+ imgLink +"，资源位跳转地址；"+ imgLink);
    }


    /**
     * 查询资源位
     */
    @Test(priority = 3)
    public void queryCarouseListTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/carouseImg/queryList");
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询资源位接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        //判断接口返回中是否已更新的图片地址
        assertTrue(jsonObj.getString("data").contains(imgSrc));
        System.out.println("查询到资源位信息中包含的图片地址："+imgSrc);
    }

    /**
     * app首页轮播图&热门商品查询
     */
    @Test(priority = 4)
    public void queryTopInfoTest() throws Exception{
        RestClient rc = new RestClient(appDomain,"/appapi/1/1.0.0/homePage/queryTopInfo");
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看资源位&商品接口，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        //判断接口返回中是否已更新的图片地址
        assertTrue(jsonObj.getString("data").contains(imgSrc));
        System.out.println("查询到资源位信息中包含的图片地址："+imgSrc);
    }

    /**
     *删除资源位
     */
    @Test(priority = 5)
    public void deleteCarouseTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/carouseImg/delete");
        Map<String, Object> map = new HashMap<>();
        map.put("id",carouseId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用删除资源位接口，接口返回信息：" + jsonObj);
        //判断更新接口是否调用成功
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}

