package bigBoy.test;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 回帖测试
 * @author: zhuli
 * @create: 2021-03-27 13:43
 **/
public class reply {

    private String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY5ODU5MTgsInVzZXJJZCI6NTAzNTE1NH0.H99XkE0cBvgecxvtIsYj6wDv_cDhQN7_4KZRUT8t16g";
    private String cidV2 ="fn%2BOQ4oVxomQ4VanhwoqkASEDZBsslMesIJo8oHtVd%2FvFOv5H30BVHM8Z3aDmeRIOuY%2FKzQtbjxByQY1%2FaRmljyyEime8aa%2BDKARCpV57SIV5cvB76EAb%2B2%2FPJt8SkybGLsFziLqMj7gtqYH%2Fwutivrtth0qqx446k2kmOlptrKnvDJkPYjis8BVTVWSRZWYl1lbzVpAMvcJyKtAsaeZKPUwA7Eys3GCzx9d3n24bA60W%2FXQDswmdVIK5lzoBAig%2FY7louQxzNDZuxXI%2BR55wPCPxlwrDgpVoYo7nNTn6v%2BOmJzISGOLBWpgdFSTh27Xydsd6%2B8oyXCTAujivTF0SA%3D%3D";

    private Long targetId;

    @Test
    public void test() throws Exception {
        for(int i=0;i<50;i++){
            addBigBoyReplyInfo();
            System.out.println(i);
        }
    }

    public void addBigBoyReplyInfo() throws Exception{
        RestClient rc = new RestClient("http://bigboy-stg.hupu.com","/appapi/1/1.1.7/bbs/addBigBoyReplyInfo");
        rc.setHeader("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY4MjY4ODUsInVzZXJJZCI6NTAzNTE1NH0.KeZaYSgrdBefdEKYDe9UF3Z3ULwJk4jSAtslPm2zVZk");
//        rc.setHeader("cid_v2");
        HashMap<String,Object> map = new HashMap<>();
        map.put("content","帅啊");
//        List<Object> imgUrlsList = new ArrayList<>();
//        for(int i = 0;i<3;i++){
//            imgUrlsList.add("http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
//        }
//        map.put("imgUrls",imgUrlsList);
        map.put("targetId","100007926");
        map.put("type",0);//0-帖子 1-商品 2-橱窗
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用回帖接口，传参信息：" + JSONArray.toJSON(map));
        System.out.println("调用回帖接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    //sit回帖测试


    @Test(dataProvider = "token")
    public void addBigBoyReplyInfoTest1(String token) throws Exception {
        for (int i = 1; i < 30; i++) {
            RestClient rc = new RestClient("http://bigboy-stg.hupu.com", "/appapi/1/1.1.8/bbs/addBigBoyReplyInfo");
            rc.setHeader("token",token);
            rc.setHeader("cid_v2", "D7fAZ9ry18LbSqElFOirPVHvk5slwNjx1mWH6AGdrSwgCRrwZuxq19PlqTpLMibTCyidU6Z%2FKx2qpxhjplMKjHaRV725vTLmmIka%2BPH%2FMqoIWVdMO5EInX6dIUFD8VcXr2R6qDCB2nsUqppj7fsqiuyCx70lYh7EDi1QjuMjKNKO6kNrPh6VIK3AGVre0W%2BR9B3LFLg3ZQ0Z1QIOkt5fBtWX8XEVMSK2835L0sC0T4%2FiSyWRPrm2zlx6qbAq7519zhVDIG1vvwQyXf%2B4aHpeATNNsJt5JacyBh%2FVSXbvmcx%2F%2FbYgxRI6%2BjeDTOL6be2RWRXR%2FR%2FD1WNT8Oo6zHZ4%2Bg%3D%3D");
            HashMap<String, Object> map = new HashMap<>();
            map.put("content", "帅啊" + RandomUtil.getDigits(5));
            map.put("targetId", "100007926");
            map.put("type", 0);//0-帖子 1-商品 2-橱窗
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("调用回帖接口，传参：" + JSONArray.toJSON(map));
            System.out.println("调用回帖接口，接口返回：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
            System.out.println("第" + i + "条，回帖时间" + DateUtil.getDateTime());
            System.out.println();
        }
    }

    @DataProvider
    public static Object[][] token(){
        return new Object[][]{
                //sit token
                {"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY3NTI5MDIsInVzZXJJZCI6NTAwMDAzMH0.tfGTLOI-uJGRwm4ytv58hOLfSJWeB9Q4waKHpsKH8SA"},
                {"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY4MzQ3NDgsInVzZXJJZCI6NTAwMDAzMX0.LROqtc-sLxkIJLnVpGVbpfthxpX8VgZbzKFRBtEvUv8"},
                {"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY4MzQ4MjksInVzZXJJZCI6NTAwMDA0MX0.-hYaUA4b0CRHtwAU1JFZHYriIQzgM2ngRjKLlyD1dPM"}
                //stg token
//                {"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTY4MzY2OTgsInVzZXJJZCI6NTAzNTE1NH0.lKMrZUcXDz7ump6omvs_fVP7WxJ8FaK86ApnUv0TpOo"}
        };
    }


    @Test
    public void testBehavior() throws Exception{
        for(int i=1;i<31;i++){
            addBigBoyThreadInfo();
            addBehavior();
            System.out.println("第" + i + "条，时间:" + DateUtil.getDateTime());
            System.out.println("---------------------------------------------------------------------");
            System.out.println();
        }
    }

    public void addBigBoyThreadInfo() throws Exception{
        RestClient rc = new RestClient("http://bigboy-sit.hupu.com","/appapi/1/1.1.8/bbs/addBigBoyThreadInfo");
        rc.setHeader("token",token);
        rc.setHeader("cid_v2",cidV2);
        HashMap<String,Object>  map = new HashMap<>();
        map.put("title","测试帖子"+ RandomUtil.getDigits(4));
        map.put("content","测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n"+ RandomUtil.getDigits(4));
        List<Object> imgUrlsList = new ArrayList<>();
        for(int i = 0;i<3;i++){
            HashMap<String,Object>  imgUrls = new HashMap<>();
            imgUrls.put("url","https://bigboy-img.hoopchina.com.cn/manage-img/1610358188_828_width_820_height_512.jpg");
            imgUrls.put("height","800");
            imgUrls.put("width","400");
            imgUrlsList.add(imgUrls);
        }
        map.put("images",imgUrlsList);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("发帖传参：" + JSONArray.toJSON(map));
        System.out.println("发帖接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        targetId =Long.parseLong(jsonObj.getString("data"));
    }

    public void addBehavior() throws Exception{
        RestClient rc = new RestClient("http://bigboy-sit.hupu.com","/appapi/1/1.1.8/users/behavior/add");
        rc.setHeader("token",token);
        rc.setHeader("cid_v2",cidV2);
        Map<String,Object> map = new HashMap<>();
        map.put("targetId",targetId);
        map.put("type",1);// 0:商品 1：帖子 2：回帖
        map.put("actionType",0);// 0：点赞 1：收藏  2：阅读  3：浏览  4：想要
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("点赞传参：" + JSONArray.toJSON(map));
        System.out.println("点赞接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    @Test
    public void testQueryPreOrderDetail() throws Exception{
        for(int i=1;i<21;i++){
            queryPreOrderDetail();
            System.out.println("第" + i + "次，时间:" + DateUtil.getDateTime());
            System.out.println("---------------------------------------------------------------------");
            System.out.println();
        }
    }
    public void queryPreOrderDetail() throws Exception{
        RestClient rc = new RestClient("http://bigboy-stg.hupu.com","/appapi/2/1.1.8/order/queryPreOrderDetail");
        rc.setHeader("token",token);
        rc.setHeader("cid_v2",cidV2);
        Map<String,Object> map = new HashMap<>();
        List<Object> orderItemDtoList = new ArrayList();
        Map<String,Object> orderItemDto = new HashMap<>();
        orderItemDto.put("goodsId",148);
        orderItemDto.put("goodsCount",1);
        orderItemDtoList.add(orderItemDto);
        map.put("orderItemDtoList",orderItemDtoList);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("进入收银台页面：" + JSONArray.toJSON(map));
        System.out.println("收银台接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }



    @Test
    public void queryThreadInfoTest() throws Exception{
        for(int i=0;i<30;i++){
            RestClient rc = new RestClient("https://bigboy.hupu.com","/appapi/2/1.1.8/bbs/queryThreadInfo");
            rc.setHeader("token",token);
            rc.setHeader("cid_v2",cidV2);
            rc.params("id","100007926");
            rc.params("queryFirstLevelReply",1);
            JSONObject jsonObj = rc.get();
            System.out.println("查看帖子详情，接口返回信息：" + jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
            System.out.println("第" + i + "次，时间:" + DateUtil.getDateTime());
            System.out.println("---------------------------------------------------------------------");
            System.out.println();
        }
    }
}
