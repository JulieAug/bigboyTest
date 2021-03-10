package bigBoyUtils;

import bigBoyUtils.ZipCompressor;
import org.testng.annotations.Test;

/**
 * @program: hupuTest
 * @description: 邮件发送测试
 * @author: zhuli
 * @create: 2020-10-23 15:54
 **/
public class emailTest {

//    @Test
    public void email(){
        //压缩后的存放路径
        ZipCompressor zc = new  ZipCompressor("/Users/zhuli0513/Downloads/测试图片/test.zip");
        //压缩谁
        zc.compressExe("/Users/zhuli0513/IdeaProjects/hupuTest/test-output/大蓝书测试报告/_Users_zhuli0513_IdeaProjects_hupuTest_run.html");
    }

}
