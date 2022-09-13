package bigBoy.backendapi.dataInfo;

import bigBoy.app.basicTest;
import bigBoyUtils.DBUtil;
import bigBoyUtils.JdbcTool;
import bigBoyUtils.RandomUtil;
import bigBoyUtils.RestClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @program: zaoApiTest
 * @description: 资料卡测试
 * @author: zhuli
 * @create: 2021-04-13 11:09
 **/
public class dataInfoIpTest {

    private String dataInfoIpName;
    private BigInteger dataInfoIpId;

    /**
     * 新增IP资料卡
     * @throws Exception
     */
    @Test(priority = 1)
    public void addDataInfoIpTest() throws Exception{
        dataInfoIpName = "I钢铁侠IP"+ RandomUtil.getDigits(4);
        RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/dataInfo/add");
        Map<String, Object> map = new HashMap<>();
        map.put("name",dataInfoIpName);
        map.put("online",1);
        map.put("createUser","zhuli0513");
        map.put("intro","简介"+RandomUtil.getDigits(5));
        map.put("type",1);//1-Ip 2-角色
        rc.body(JSONArray.toJSON(map));
        System.out.println("新增资料卡：" + map);
        JSONObject jsonObj = rc.post();
        System.out.println("接口返回信息：" + jsonObj);
        assertEquals(jsonObj.getString("code"), "SUCCESS");
        dataInfoIpId = DBUtil.getForValue("select id from data_info where name ='"+dataInfoIpName +"' order by update_dt desc limit 1","bigBoy.properties");
        System.out.println(dataInfoIpId);//资料卡id
    }

    /**
     * IP关联角色
     * @throws Exception
     */
    @Test(priority = 2)
    public void associateDataInfoTest() throws Exception{
        //从数据库中取dataInfoRoleIds
        List<Long> dataInfoRoleIds = new ArrayList<>();
        String sql = "select id from data_info where type =2 and online =1";

        //连接数据库
        Connection connection = JdbcTool.getConnection("bigBoy.properties");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            dataInfoRoleIds.add(resultSet.getLong("id"));
        }
        System.out.println(dataInfoRoleIds);

        for(int i=0;i <dataInfoRoleIds.size();i++){
            Long dataInfoRoleId=dataInfoRoleIds.get(i);
            RestClient rc = new RestClient(basicTest.backendDomain,"/backendapi/dataInfo/associateDataInfo");
            Map<String, Object> map = new HashMap<>();
            map.put("id",dataInfoIpId);
            map.put("associatedDataInfoId",dataInfoRoleId);
            rc.body(JSONArray.toJSON(map));
            System.out.println("IP关联角色：" + map);
            JSONObject jsonObj = rc.post();
            System.out.println("接口返回信息：" + jsonObj);
            assertEquals(jsonObj.getString("code"), "SUCCESS");
        }
    }


}
