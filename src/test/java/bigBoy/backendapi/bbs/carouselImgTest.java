package bigBoy.backendapi.bbs;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 资料库新增资源位
 * @author: zhuli
 * @create: 2022-05-18 10:18
 **/
public class carouselImgTest {
    @Test(priority = 1)
    public void addCarouseImgTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/carouseImg/add");
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","资料库资源位"+ RandomUtil.getDigits(4));
        map.put("catalogId",1045);//一级/二级目录Id
        map.put("targetId",1001);//资料目录根节点id 商品传1000/资料传1001、
        map.put("location",2);//所处资料库位置 0-整体顶部 1-一级分类 2-二级分类
        map.put("imgSrc","http://bigboy-img.hoopchina.com.cn/manage-img/e566900a-6507-4caf-af6e-665567b5fcfe_width_1120_height_660.png");
        map.put("imgLink","bigboy://goods/detail?goodsId=19");
        map.put("pageType",3);//1:首页推荐页 2:商品推荐页 3:资料目录页资源位 4:首页搜索页 5:商品搜索页
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增资料库资源位：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }
}
