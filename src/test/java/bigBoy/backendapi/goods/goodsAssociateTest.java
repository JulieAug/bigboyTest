package bigBoy.backendapi.goods;

import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: hupuTest
 * @description: 商品与橱窗/帖子关联
 * @author: zhuli
 * @create: 2020-10-29 16:40
 **/
public class goodsAssociateTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private BigInteger goodsId = DBUtil.getForValue("SELECT id FROM goods ORDER BY ct desc limit 1;","bigBoyData.properties");

    private BigInteger associateId;

    @DataProvider
    public static Object[][] associate() {
        return new Object[][] {
                {1,DBUtil.getForValue("SELECT id from display_window ORDER BY create_dt desc limit 1;","bigBoy.properties")},
                {0,DBUtil.getForValue("SELECT id from associate_thread ORDER BY create_dt desc limit 1;","bigBoy.properties")}
        };
    }

    /**
     * 商品与橱窗/帖子关联
     * @throws Exception
     */
    @Test(priority = 1,dataProvider = "associate")
    public void  associateTest(int type,long targetId) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/associate/addAssociate");
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",goodsId);
        map.put("type",type);//类型 0-帖子 1-橱窗
        map.put("targetId",targetId);
        map.put("goodsImg","https://bigboy-img.hoopchina.com.cn/manage-img/imgShow@3x-1604025710.png");
        rc.body(JSONArray.toJSON(map));
        System.out.println("传参为："+JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        associateId = DBUtil.getForValue("select id from goods_associate where goods_id = '"+goodsId+"' and target_id = '"+targetId+"'  order by  create_dt desc limit 1","bigBoy.properties");

        //调用查询关联接口
        queryAssociateList(type,targetId);
        //调用删除关联接口
        deleteAssociateTest();
    }

    /**
     * 查看商品橱窗/帖子关联
     * @throws Exception
     */
    public void queryAssociateList(int type,long targetId) throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/associate/queryAssociateList");
        rc.params("targetId",targetId);
        rc.params("type",type);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看关联列表，接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 商品橱窗/帖子 取消关联
     * @throws Exception
     */
    public void deleteAssociateTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/backendapi/goods/associate/deleteAssociate");
        Map<String,Object> map = new HashMap<>();
        map.put("id",associateId);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用删除关联接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
