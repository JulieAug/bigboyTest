package hupu.backendapi;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @program: zaoApiTest
 * @description: 商品分类
 * @author: zhuli
 * @create: 2021-10-29 13:57
 **/
public class goodsCategory {
    /**
     * 添加商品分类
     * @throws Exception
     */
    @Test(priority = 1)
    public void addCategoryTest() throws Exception{
        for (int i = 0; i < 20; i++) {
            RestClient rc = new RestClient(basicTest.hupuBackendDomain,"/backendapi/goodsCategory/add");
            HashMap<String,Object> map = new HashMap<>();
            map.put("name","超级赛车分类三"+ i);
            map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1626069912_423_width_548_height_552.png");
            map.put("parentId",20);//上级分类id
            rc.body(JSONArray.toJSON(map));
            JSONObject jsonObj = rc.post();
            System.out.println("传参：" + JSONArray.toJSON(map));
            System.out.println("接口返回信息：" +"\n"+jsonObj);
            Assert.assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }
}
