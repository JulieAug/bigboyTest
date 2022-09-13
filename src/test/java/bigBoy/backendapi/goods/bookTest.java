package bigBoy.backendapi.goods;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 商品预售
 * @author: zhuli
 * @create: 2022-06-02 14:18
 **/
public class bookTest {
    private Long bookGoodsId;
    @Test(priority = 1)
    public void insertGoodsBook() throws Exception{
        for (int i = 19; i <20; i++) {
            insertGoodsBook(i);
            updateGoodsBookStock(i);
        }
    }

    public void insertGoodsBook(Integer goodsId) throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/goodsBook/insertGoodsBook");
        HashMap<String,Object> map = new HashMap<>();
        map.put("merchantId",5);
        map.put("merchantName","zl商家测试");
        map.put("goodsId",goodsId);
        String goodsName = DBUtil.getForValue("select product_name from goods_pro where id ="+goodsId,"bigBoyData.properties");
        map.put("goodsName",goodsName);
        map.put("beginDate", "2022-08-12 17:00:00");
        map.put("endDate", "2022-08-23 19:00:00");
        map.put("depositBeginDate", "2022-08-12 17:00:00");//定金时间
        map.put("depositEndDate", "2022-08-17 18:00:00");
        map.put("balanceBeginDate", "2022-09-17 12:02:00");
        map.put("balanceEndDate", "2022-09-18 18:30:00");
        map.put("arrivalDate", "2022-08-17 18:01:00");
        map.put("quotaCount",2);//限购
        map.put("goodsAmount",3);//商品金额
        map.put("balanceAmount",0.01);//尾款
        map.put("depositAmount",0.01);//定金
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增商品预售：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        bookGoodsId = DBUtil.getForValue("select id from goods_book where goods_id ='"+goodsId+"'order by create_date desc limit 1","bigBoy.properties");
    }

   //新增商品库存
    public void updateGoodsBookStock(Integer goodsId) throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/goodsBook/updateGoodsBookStock");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",bookGoodsId);
        map.put("goodsId",goodsId);
        map.put("changeCount",10);
        map.put("stockType","book_stock");
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增商品库存：" + map);
        JSONObject jsonObj = rc.post();
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    //上线预售商品
//    @Test
    public void updateOnLineStatus() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain, "/backendapi/goodsBook/updateOnLineStatus");
//        HashMap<String, Object> map = new HashMap<>();
        rc.params("id",37);
        rc.params("status",1);
        JSONObject jsonObj = rc.post();
//        rc.body(JSONArray.toJSON(map));
//        System.out.println(map);
//        JSONObject jsonObj = rc.post();
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}

