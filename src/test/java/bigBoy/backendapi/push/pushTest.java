package bigBoy.backendapi.push;

import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: zaoApiTest
 * @description: push
 * @author: zhuli
 * @create: 2020-12-24 11:52
 **/
public class pushTest {

    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-backend-api";

    private final String addService = "/backendapi/push/add";

    private final String queryList = "/backendapi/push/queryList";

//    @Test(priority = 1,description = "push推送")
    public void addPushTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,addService);
        Map<String,Object> map = new HashMap<>();
        map.put("title","造物push标题"+ RandomUtil.getDigits(3));//推送标题
        map.put("content","造物内容快来点击查看吧！");//推送内容
        map.put("image","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1086180543,3796773770&fm=26&gp=0.jpg");
//        map.put("platform",1);//iOS——0，Android——1
        map.put("type",0);//0-scheme 1-其他
        map.put("scheme","bigboy://display/index");
//            map.put("sendDate","");
        map.put("sendType",0);//发送方式 0-立即发送 1-定时发送
        map.put("userType",0);//0-全部 1-七天活跃用户
        rc.body(JSONArray.toJSON(map));
        JSONObject jsonObj = rc.post();
        System.out.println( "传参："+JSONArray.toJSON(map));
        System.out.println("接口返回：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 2,description = "push推送列表查看")
    public void queryPushListTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,queryList);
        rc.params("status",0);//0-已发送 1-未发送
        rc.params("beginDate","");
        rc.params("endDate","");
        rc.params("pageNum",1);
        rc.params("pageSize",10);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");

    }

}