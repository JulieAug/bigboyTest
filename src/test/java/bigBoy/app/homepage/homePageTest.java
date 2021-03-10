package bigBoy.app.homepage;

import bigBoy.app.basicTest;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;


/**
 * @program: hupuTest
 * @description: app首页
 * @author: zhuli
 * @create: 2020-10-26 10:49
 **/
public class homePageTest {

    @Test(priority = 1,description = "查询首页轮播图&热门商品")
    public void queryHomePageTopTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/homePage/queryTopInfo");
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看资源位&商品接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 2,description = "查询首页帖子列表")
    public void queryHomePageThreadListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/homePage/queryThreadList");
        rc.setHeader("cid","123456");
        JSONObject jsonObj = rc.get();
        System.out.println("调用首页推荐列表接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority =3,description = "查询资讯列表")
    public void queryNewsTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,"/appapi/"+basicTest.project+"/"+basicTest.version+"/homePage/queryNews");
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用首页资讯接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
