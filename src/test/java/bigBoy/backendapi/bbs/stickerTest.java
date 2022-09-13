package bigBoy.backendapi.bbs;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 表情包测试
 * @author: zhuli
 * @create: 2021-12-15 13:14
 **/
public class stickerTest {
    private BigInteger stickerGroupId;
    /**
     * 新增表情包分组
     * @throws Exception
     */
    @Test(priority = 1)
    public void addStickerGroupTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/sticker/addGroup");
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","表情包分组"+ RandomUtil.getDigits(4));
        map.put("seq",1);
        map.put("image","http://bigboy-img.hoopchina.com.cn/manage-img/e566900a-6507-4caf-af6e-665567b5fcfe_width_1120_height_660.png");
        map.put("online",1);
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增表情包分组：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        stickerGroupId = DBUtil.getForValue("select id from sticker_group order by update_dt desc limit 1","bigBoy.properties");
    }

    /**
     * 新增表情包
     * @throws Exception
     */
    public void addStickerTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/sticker/add");
        HashMap<String,Object> map = new HashMap<>();
        map.put("stickerGroupId",stickerGroupId);
        map.put("name","表情包"+RandomUtil.getDigits(2));
        map.put("image","https://bigboy-img.hoopchina.com.cn/manage-img/1638412514_228_width_600_height_759.gif");
        map.put("online",1);
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增表情包：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

    @Test(priority = 2)
    private void test() throws Exception {
        for (int i = 0; i < 20; i++) {
            addStickerGroupTest();
            addStickerTest();
        }
    }

}
