package bigBoy.app.goods;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * @program: zaoApiTest
 * @description: 商品详情页
 * @author: zhuli
 * @create: 2020-12-25 13:44
 **/
public class goodsDetailsTest {

    private final String queryGoodsShopViewById = "/appapi/" + basicTest.project + "/" + basicTest.version + "/goods/queryGoodsShopViewById";

    private final String getComments = "/appapi/" + basicTest.project + "/" + basicTest.version + "/goods/comments";

    private final String associateThread = "/appapi/" + basicTest.project + "/" + basicTest.version + "/goods/associateThread";

    private BigInteger goodsId = DBUtil.getForValue("select id from goods_pro order by ct asc limit 1;", "bigBoyData.properties");

    /**
     * 查询商品详情
     *
     * @throws Exception
     */
    @Test(priority = 1, description = "查询商品详情")
    public void queryGoodsShopViewById() throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain, queryGoodsShopViewById);
        rc.params("goodsId", goodsId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        Assert.assertTrue(jsonObj.getString("data").contains("productName"));
    }

    /**
     * 获取商品的评论列表
     * @throws Exception
     */
    @Test(priority = 2)
    public void getgoodsCommentsTest() throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain, getComments);
        rc.params("goodsId", "12187");//    有全网评价的商品 47959
        rc.params("type",1);//1：带图片 0：不带图片
        rc.params("pageNum", "1");
        rc.params("pageSize", "10");
        JSONObject jsonObj = rc.get();
        System.out.println(jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

//    有开箱实拍的商品 1
    @Test(priority = 3)
    public void associateThreadTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain, associateThread);
        rc.params("goodsId", 1);
        rc.params("pageNum", "1");
        rc.params("pageSize", "10");
        JSONObject jsonObj = rc.get();
        System.out.println(jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
