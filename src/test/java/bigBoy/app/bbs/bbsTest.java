package bigBoy.app.bbs;

import bigBoy.app.basicTest;
import bigBoy.app.user.loginTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @program: hupuTest
 * @description:
 * @author: zhuli
 * @create: 2020-11-05 17:50
 **/
public class bbsTest {
    private final String addBigBoyThreadInfoService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/bbs/addBigBoyThreadInfo";

    private final String addBigBoyReplyInfoService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/bbs/addBigBoyReplyInfo";

    private final String queryThreadInfoService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/bbs/queryThreadInfo";

    private final String queryReplyListService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/bbs/queryReplyList";

    private final String searchForThreadService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/goods/searchForThread";

    private final String queryForThreadService = "/appapi/"+basicTest.project+"/"+basicTest.version+"/trade/show/queryForThread";

    private Long targetId;

    private BigInteger goodsId;

    private Long tradeShowId;

    private Integer[] categorys = new Integer[]{0};//分类 1：高达/2：手办/3：模型/4：乐高（没有值代表全部类型）

    private String[] keywords = new String[]{"高达", "乐高", "手办", "模型"};

    private String token;

    @BeforeClass
    public void setUp() throws Exception {
//        token = new loginTest().checkSecurityCodeSTest();
        //18260356798
        token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDA3NDgwMTQsInVzZXJJZCI6NTAwMTAwOH0.du3BjTHhVq878521Db_-ysqfff359vJ3gmTWrOlsiNQdsf";
    }

    //发主贴时搜索商品
    public void searchForThread(String keyword,Integer category) throws Exception {
        RestClient rc = new RestClient(basicTest.apiDomain, searchForThreadService);
        rc.setHeader("token",token);
//        rc.setHeader("cid_v2","");
        rc.params("keyword", keyword);
        rc.params("category", category);//分类 1：高达/2：手办/3：模型/4：乐高（没有值代表全部类型）
        rc.params("pageNum", 1);
        rc.params("pageSize", 10);
        JSONObject jsonObj = rc.get();
//        System.out.println("搜素商品信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        if(Long.parseLong(jsonObj.getJSONObject("data").getString("totalCount"))!=0) {
            goodsId = new BigInteger(jsonObj.getJSONObject("data").getJSONArray("list").getJSONObject(0).getString("id"));
            Assert.assertTrue(jsonObj.getString("data").contains(keyword));
        }else {
        }
    }

    //搜索投稿中的展览
    public void queryForThread() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain, queryForThreadService);
        rc.setHeader("token",token);
        JSONObject jsonObj = rc.get();
        System.out.println("搜素投稿中的展览：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        if(jsonObj.getJSONArray("data").size() != 0) {
            tradeShowId = Long.parseLong(jsonObj.getJSONArray("data").getJSONObject(0).getString("id"));
            Assert.assertTrue(jsonObj.getString("data").contains("id"));
        }else {
            System.out.println("无搜索到对应展览...");
        }
    }

    @Test(priority = 1,description = "发布主贴")
    public void addBigBoyThreadInfoTest() throws Exception{
        for (int i =0;i<10;i++){
            addBigBoyThreadInfo();
        }
    }


    public void addBigBoyThreadInfo() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,addBigBoyThreadInfoService);
        rc.setHeader("token",token);
        HashMap<String,Object>  map = new HashMap<>();
        map.put("title","帖子"+ RandomUtil.getDigits(4));
        map.put("content","测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n测试帖子内容\n"+ RandomUtil.getDigits(4));
        List<Object> imgUrlsList = new ArrayList<>();
        for(int i = 0;i<3;i++){
            HashMap<String,Object>  imgUrls = new HashMap<>();
            imgUrls.put("url","https://img.bigboy.club/manage-img/8333708f-73e9-4b24-a616-5e746b9a95a5_width_1080_height_1101");
            imgUrls.put("height","800");
            imgUrls.put("width","400");
            imgUrlsList.add(imgUrls);
        }
        map.put("images",imgUrlsList);

        //关联商品id
//        List<Object> goodsIdList = new ArrayList<>();
//        for (String keyword : keywords) {
//            for (Integer category : categorys) {
//                searchForThread(keyword, category);    //发主贴时搜索商品
//                goodsIdList.add(goodsId);
//            }
//        }
//        map.put("goodsIds",goodsIdList);

        //关联展览id
//        List<Object> tradeShowIdList = new ArrayList<>();
//        queryForThread();
//        tradeShowIdList.add(tradeShowId);  //搜索投稿中的展览
//        map.put("tradeShowIds",tradeShowIdList);

        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("发帖传参：" + JSONArray.toJSON(map));
        System.out.println("调用发帖接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        targetId =Long.parseLong(jsonObj.getString("data"));
    }

    /**
     * 发布回帖
     * @throws Exception
     */
    @Test(priority = 2,description = "发布回贴")
    public void addBigBoyReplyInfoTest() throws Exception{
        replyInfo[] reply = new replyInfo[]{
                new replyInfo(targetId, 0),
                new replyInfo(goodsId.longValue(), 1)
//                new replyInfo(tradeShowId, 2)
        };
        for (replyInfo key :reply) {
            addBigBoyReplyInfo(key.getId(), key.getType());
        }
    }

    public void addBigBoyReplyInfo(Long id,Integer type) throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,addBigBoyReplyInfoService);
        rc.setHeader("token",token);
        HashMap<String,Object>  map = new HashMap<>();
        map.put("content","我要测试一下回帖是否正常～"+ RandomUtil.getDigits(4));
        List<Object> imgUrlsList = new ArrayList<>();
        for(int i = 0;i<3;i++){
            imgUrlsList.add("http://movie-img.hupu.com/movie/98fffb8d655c9b503ecb3a2718d69fea412871.jpg");
        }
        map.put("imgUrls",imgUrlsList);
        map.put("targetId",id);
        map.put("type",type);//0-帖子 1-商品 2-橱窗
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("调用回帖接口，传参信息：" + JSONArray.toJSON(map));
        System.out.println("调用回帖接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    /**
     * 帖子详情接口
     */
    @Test(priority = 3,description = "查看某个帖子详情")
    public void queryThreadInfoTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryThreadInfoService);
        rc.params("id",targetId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看贴子详情页接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        String title = DBUtil.getForValue("SELECT title FROM associate_thread where id="+targetId,"bigBoy.properties");
        //校验数据库这个帖子标题与接口返回的帖子标题是否为同一个
        Assert.assertEquals(jsonObj.getJSONObject("data").getString("title"),title);
    }

    /**
     * 回帖列表
     * @throws Exception
     */
    @Test(priority = 4,description = "查看某个帖子的回帖")
    public void queryReplyListTest() throws Exception{
        RestClient rc = new RestClient(basicTest.apiDomain,queryReplyListService);
        rc.params("targetId",targetId);
        rc.params("type",0);
        rc.params("pageSize",10);
        rc.params("pageNum",1);
        JSONObject jsonObj = rc.get();
        System.out.println("调用查看回帖列表接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
