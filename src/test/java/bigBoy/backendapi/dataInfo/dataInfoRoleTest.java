package bigBoy.backendapi.dataInfo;

import bigBoy.app.basicTest;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 角色资料卡
 * @author: zhuli
 * @create: 2021-04-13 19:43
 **/
public class dataInfoRoleTest {

    private String dataInfoRoleName;
    private BigInteger dataInfoRoleId;

    /**
     * 新增角色资料卡
     * @throws Exception
     */
    @Test(priority = 1)
    public void addDataInfoRoleTest() throws Exception{
        for(int i =0;i<11;i++){
            dataInfoRoleName = "钢铁侠角色"+ RandomUtil.getDigits(4);
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/dataInfo/add");
            Map<String, Object> map = new HashMap<>();
            map.put("name",dataInfoRoleName);
            map.put("online",1);
            map.put("createUser","zhuli0513");
            map.put("type",2);//1-Ip 2-角色
            rc.body(JSONArray.toJSON(map));
            System.out.println("新增资料卡：" + map);
            JSONObject jsonObj = rc.post();
            System.out.println("接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }

    }


    /**
     * 编辑角色相关视频
     * @throws Exception
     */
    @Test
    public void updateVideoTest() throws Exception{
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/dataInfo/updateVideo");
        Map<String, Object> map = new HashMap<>();
        map.put("id",84);
        List<Object> videoLists = new ArrayList<>();
        Map<String, Object> video = new HashMap<>();
        video.put("cover","http://bigboy-img.hoopchina.com.cn/manage-img/0fc3a9bd-d2bf-4154-9d27-5442300299d3_width_560_height_560.png?x-oss-process=image/resize,h_100,m_lfit");
        video.put("origin","http://bigboy-img.oss-cn-hangzhou.aliyuncs.com/manage-img/1618314769_484_width_1_height_1.mp4");
        video.put("timeLong",30);
        videoLists.add(video);
        map.put("videoList",videoLists);
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增资料卡：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
    }

}
