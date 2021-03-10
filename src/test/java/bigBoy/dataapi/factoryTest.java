package bigBoy.dataapi;

import bigBoyUtils.DBUtil;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import bigBoyUtils.RestClient;

import java.math.BigInteger;

/**
 * @program: hupuTest
 * @description: 厂商信息查询
 * @author: zhuli
 * @create: 2020-11-05 21:16
 **/
public class factoryTest {
    private final String  apiDomain = "http://msv-zuul-sit.hupu.io:8769/bigboy-data-api";

    private BigInteger factoryId ;

    /**
     * 根据id获取厂商
     * @throws Exception
     */
    @Test(priority = 1)
    public void searchFactoryByIdTest() throws Exception{
        factoryId = DBUtil.getForValue("select id from factory_pro order by ct desc limit 1","bigBoyData.properties");
        RestClient rc = new RestClient(apiDomain,"/api/factories/v1/"+factoryId);
        rc.params("id",factoryId);
        JSONObject jsonObj = rc.get();
        System.out.println("调用接口，接口返回信息：" + jsonObj);
        Assert.assertTrue(jsonObj.getString("data").contains("PLM"));
    }

    public void updateFactoryTest() throws Exception{
        RestClient rc = new RestClient(apiDomain,"/api/factories/v1/"+factoryId);
        rc.formParams("id",factoryId);

    }

}
