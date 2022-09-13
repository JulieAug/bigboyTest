package bigBoyUtils;

import com.alibaba.fastjson.JSONObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class RestClientExtent extends RestClient {

    private RequestSpecification request;
    static HttpPut httpput;
    public RestClientExtent(String endpoint, String service) {
        super(endpoint,service);
        this.request= RestAssured.given().contentType("application/json").config(RestAssuredConfig.newConfig().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("utf-8"))).baseUri(endpoint + service);
    }

    public void setHeader(String key, String value) {
        Header header = new Header(key, value);
        this.request.header(header);
    }

    public RestClient body(Object value) {
        this.request.body(value);
        return this;
    }

    public void setPutHeader(String key, String value) {
        Header header = new Header(key, value);
        httpput.setHeader((org.apache.http.Header) header);
    }

    public String getCooike(String uri, String cookieName) {
        String cookies = ((Response)this.request.get(uri, new Object[0])).getCookie(cookieName);
        return cookies;
    }

    public RestClient params(String name, Object value) {
        this.request.param(name, new Object[]{value});
        return this;
    }

    public JSONObject delete() throws Exception {
        Response resp = (Response)this.request.delete();
        if (resp.getStatusCode() == 200) {
            JSONObject result = JSONObject.parseObject(resp.asString());
            return result;
        } else {
            throw new RuntimeException(resp.getStatusCode() + "get unsuccess");
        }
    }
    public JSONObject put() throws Exception {
        Response resp = (Response)this.request.put();
        if (resp.getStatusCode() == 200) {
            JSONObject result = JSONObject.parseObject(resp.asString());
            return result;
        } else {
            throw new RuntimeException(resp.getStatusCode() + "get unsuccess");
        }
    }


        /**
         * 发送 http put 请求，参数以原生字符串进行提交
         * @param url
         * @return
         */
    public static JSONObject httpPutRaw(String url, String stringJson){
        JSONObject result=null;
        String encode="utf-8";
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        httpput = new HttpPut(url);

        //设置header
        httpput.setHeader("Content-type", "application/json");

                //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpput.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpput);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
           result = JSONObject.parseObject(EntityUtils.toString(entity, encode));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();  //关闭连接、释放资源
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public JSONObject delete(String url, Map<String,String> map) throws Exception {
        Response resp = (Response)this.request.delete(url, map);
        if (resp.getStatusCode() == 200) {
            JSONObject result = JSONObject.parseObject(resp.asString());
            return result;
        } else {
            throw new RuntimeException(resp.getStatusCode() + "get unsuccess");
        }
    }
}
