package bigBoy.mock;
import com.github.dreamhead.moco.bootstrap.Bootstrap;

/**
 * @program: zaoApiTest
 * @description: moco
 * @author: zhuli
 * @create: 2021-03-08 15:30
 **/
public class mocoTest {
    //http -p 8080 -c api.json
    public static void main(String[] args) {
        (new Bootstrap()).run(new String[]{"http","-p","8888","-c","/Users/zhuli0513/IdeaProjects/zaoApiTest/src/test/java/bigBoy/mock/test.json"});
    }
}
