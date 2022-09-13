package bigBoy.backendapi.badge;

import bigBoy.app.basicTest;
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
 * @description: 徽章测试
 * @author: zhuli
 * @create: 2021-08-12 13:27
 **/
public class badgeTest {

//    1='累计登录'  2='浏览日志'  3='点赞日志'  4='关注用户' 5='评论日志'  6='发布日志'  7='收藏商品'
//    8='被点赞日志' 9='被关注'  10='被评论日志' 11='被收藏日志'
//    12='浏览商品' 13='支付' 14='售出' 15='成为卖家' 16='支付金额' 17='收藏日志'

    /**
     * 新增单极徽章
     * @throws Exception
     */
    @Test(priority = 1)
    public void addBadgeTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/badge/add");
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","收藏日志");
        map.put("action",8);
        map.put("needCount",4);
        map.put("badgeGroupId",32);
        map.put("single",1);
        map.put("remark","徽章备注");
        map.put("online",1);
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1628745956_730_width_750_height_475.jpg");
        map.put("type",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加活动任务，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }


    /**
     * 新增多级徽章
     * @throws Exception
     */
    @Test(priority = 2)
    public void multiLevelBadge() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/badge/add");
        HashMap<String,Object> map = new HashMap<>();
        map.put("badgeGroupId",41);
        map.put("single",0);//1-单级 0-多级
        map.put("type",1);//1：系统徽章 2：人工徽章

        map.put("name","评论日志");
        map.put("action",5);
        List<Object> gradesList = new ArrayList();
        for(int i=1;i<6;i++ ){
            HashMap<String,Object> grade = new HashMap<>();
            grade.put("grade",i);
            grade.put("needCount",i);
            grade.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
            gradesList.add(grade);
        }
        map.put("grades",gradesList);
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1628745956_730_width_750_height_475.jpg");

        map.put("remark","徽章备注");
        map.put("online",1);
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println("传参：" + JSONArray.toJSON(map));
        System.out.println("添加活动任务，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
