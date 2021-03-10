package bigBoyUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @program: hupuTest
 * @description: AppiumDriver
 * @author: zhuli
 * @create: 2020-11-03 20:51
 **/
public class AppiumDriverUtil {

    public static AppiumDriver createDriver(){
        try{
            //设置自动化相关参数（设置的过程中只需更改value值）
            DesiredCapabilities capabilities = new DesiredCapabilities();
            //设置测试的平台是IOS/Android/FirefoxOS
            capabilities.setCapability("platformName", "Android");
            //设置设备的序列号
            capabilities.setCapability("deviceName", "d6eae185");
            //设置测试的安卓系统版本
            capabilities.setCapability("platformVersion", "10");
            //设置apk的包名
            capabilities.setCapability("appPackage", "com.bigboy.zao");
            //设置启动Activity
            capabilities.setCapability("appActivity", "com.bigboy.zao.main.HomeActivity");
            //使用 Unicode 输入法 ，支持中文输入
//        capabilities.setCapability("unicodeKeyboard", "True");
            //重置输入法到原有状态
//        capabilities.setCapability("resetKeyboard", "True");
            //设置避免重新签名
            capabilities.setCapability("noReset", true);
            //每次启动时覆盖session，否则第二次后运行会报错不能新建session
            capabilities.setCapability("sessionOverride", true);
            AndroidDriver<WebElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//隐式等待 10秒
            return driver;
        }catch(
                Exception e
        ){
            throw new RuntimeException(e);
        }
    }
}

