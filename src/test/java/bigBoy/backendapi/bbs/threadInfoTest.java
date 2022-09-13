package bigBoy.backendapi.bbs;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: zaoApiTest
 * @description: 发日志
 * @author: zhuli
 * @create: 2022-02-23 17:03
 **/
public class threadInfoTest {
    @Test
    public void addBigBoyThreadInfo() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/bbs/addBigBoyThreadInfo");
        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",5001021);
        map.put("title","秒杀进行中"+ RandomUtil.getDigits(4));
        map.put("content","卡片测试内容");
        map.put("status",1);//0-草稿箱 1-发布成功
        map.put("threadType",0);//类型 0-帖子 1-文章
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
        List<Object> goodsIdList = new ArrayList<>();
        goodsIdList.add(985);
        map.put("goodsIds",goodsIdList);

        //关联卡片
        List<Object> goodsAssociate = new ArrayList<>();
        HashMap<String,Object> card = new HashMap<>();
        card.put("cardType",3);//卡片类型 1-造物赏 2-0元抽奖 3-秒杀 4-商品 5-自定义
        card.put("otherId",161);//其他id 造物赏id/抽奖id/秒杀id
//        card.put("goodsId",19);
//        card.put("goodsName","HGUC 普通版 夜莺");
//        card.put("goodsPrice",null);
//        card.put("goodsImg","http://bigboy-img.hoopchina.com.cn/manage-img/1755e4fd-8e1a-4c92-9ab4-b52b3f4dd7bf_width_1200_height_1200.png");
//        card.put("status",1);
//        card.put("flashSaleEndTime","2022-02-24 23:07:00");
//        card.put("url","bigboy://web/detail?url=https://activity.bigboy.club/ichibansho/topic/164");

        goodsAssociate.add(card);

        map.put("goodsAssociateList",goodsAssociate);


        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("发帖传参：" + JSONArray.toJSON(map));
        System.out.println("调用发帖接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


}
