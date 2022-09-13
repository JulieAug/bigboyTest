package bigBoy.backendapi.dataInfo;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 资料库目录管理
 * @author: zhuli
 * @create: 2021-04-13 11:04
 **/
public class dataCatalogTest {
    private String dataCatalogName;

    @Test(priority = 1)
    public void addDataCatalogTest() throws Exception{
        dataCatalogName = "资料库目录"+ RandomUtil.getDigits(4);
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/dataCatalog/add");
        Map<String, Object> map = new HashMap<>();
        map.put("name",dataCatalogName);
        map.put("online",0);
        map.put("createUser","zhuli0513");
        map.put("bindingTargetType",1);//绑定的目标类型 1：资料卡 2：聚合页
        map.put("bindingTargetId",2);//绑定的目标id 资料卡id/聚合页id
        map.put("parentId",30);//上级目录id,创建根目录时不用传，创建子目录时需要传父目录的id
        map.put("picture","");
        map.put("seq","");//顺序
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增优惠券：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
