package bigBoy.backendapi.goods;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.*;

/**
 * @program: zaoApiTest
 * @description: 商祥页模板图测试
 * @author: zhuli
 * @create: 2021-04-02 17:05
 **/
public class goodsImgTemplateTest {
    private BigInteger goodsImgTemplateId;
    private String name;

    /**
     * 添加商品图配置模版
     * @throws Exception
     */
    @Test(priority = 1)
    public void addGoodsImgTemplateTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/goodsImgTemplate/add");
        Map<String,Object> map = new HashMap<>();
        name="商品图模板"+ RandomUtil.getDigits(2);
        map.put("name",name);

        List<Object> headPicList = new ArrayList<>();
        headPicList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1616651706_256_width_700_height_541.png");
        map.put("headPicList",headPicList);

        List<Object> bottomPicList = new ArrayList<>();
        bottomPicList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1616053125_119_width_548_height_552.png");
        map.put("bottomPicList",bottomPicList);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用添加商品图配置模版接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        goodsImgTemplateId = DBUtil.getForValue("select id from goods_img_template where name ='"+name+"' order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 查询商品图配置模版
     * @throws Exception
     */
    @Test(priority = 2)
    public void queryGoodsImgTemplateTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/goodsImgTemplate/query");
        rc.params("name","");
        rc.params("pageNum",1);
        rc.params("pageSize",20);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查询商品图配置模版接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertTrue(jsonObj.getString("data").contains(name));
    }

    /**
     * 更新商品图配置模版
     * @throws Exception
     */
    @Test(priority = 3)
    public void updateGoodsImgTemplateTest() throws Exception {
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/goodsImgTemplate/update");
        Map<String, Object> map = new HashMap<>();
        map.put("id", goodsImgTemplateId);
        name = "编辑商品图模板" + RandomUtil.getDigits(2);
        map.put("name", name);
        List<Object> headPicList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            headPicList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1616651706_256_width_700_height_541.png");
        }
        map.put("headPicList",headPicList);

        List<Object> bottomPicList = new ArrayList<>();
        for (int j = 1; j < 5; j++) {
            bottomPicList.add("https://bigboy-img.hoopchina.com.cn/manage-img/1616053125_119_width_548_height_552.png");
        }
        map.put("bottomPicList",bottomPicList);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，传参：" + JSONArray.toJSON(map));
        System.out.println("调用添加商品图配置模版接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 商品图模版应用到全部自营商品上
     * @throws Exception
     */
    @Test(priority = 4)
    public void useForAllGoodsTemplateTest() throws Exception {
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/goodsImgTemplate/useForAllGoods");
        Map<String, Object> map = new HashMap<>();
        map.put("id", goodsImgTemplateId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用删除商品图配置模版接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 删除商品图模版
     * @throws Exception
     */
    @Test(priority = 5)
    public void deleteGoodsImgTemplateTest() throws Exception {
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/goodsImgTemplate/delete");
        Map<String, Object> map = new HashMap<>();
        map.put("id", goodsImgTemplateId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用删除商品图配置模版接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



}
