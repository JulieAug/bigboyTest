package httpclient;

import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @program: zaoApiTest
 * @description: httpclient测试
 * @author: zhuli
 * @create: 2021-03-30 16:51
 **/
public class test {
    private String hotSearchKeywordsUrl = "http://bigboy-sit.hupu.com/appapi/1/1.1.6/goods/hotSearchKeyWords";

    @Test
    public void hotSearchKeywordsTest() throws Exception{
        HttpClient hc = new HttpClient();
        HttpResponse response = hc.get(hotSearchKeywordsUrl);
        HttpEntity entity = response.getEntity();
        String message = EntityUtils.toString(entity, "utf-8");
        System.out.println(message);
    }


//    发送Get请求：restClient.get(url,headermap)
//
//    发送Post请求，同表单Post提交：restClient.post(url,paramsmap,headermap)
//
//    发送Post Raw请求：restClient.post(url,entityString,headermap);
//
//    发送Put Raw请求：restClient.put(url,entityString,headermap);
//
//    发送Delete请求：restClient.delete(url);
}
