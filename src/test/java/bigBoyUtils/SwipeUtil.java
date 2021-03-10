package bigBoyUtils;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * @program: hupuTest
 * @description: 滑动组件
 * @author: zhuli
 * @create: 2020-11-03 14:11
 **/
public class SwipeUtil {

    /**
     *向上滑动屏幕
     */
    public static void swipeUp(AppiumDriver driver){
        int width = driver.manage().window().getSize().width; //获取屏幕的宽
        int height = driver.manage().window().getSize().height; //获取屏幕的长
        PointOption pointOption= PointOption.point(width/2, height*3/4); //这里相当于一个坐标原点
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(pointOption).moveTo(PointOption.point(width/2,height/4)).release().perform();
        new TouchAction(driver).press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(width/2,height/4)).release().perform();
    }

    /**
     *向下滑动屏幕
     */
    public static void swipeDown(AppiumDriver driver) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        new TouchAction(driver).press(PointOption.point(width / 2, height / 10))
                .moveTo(PointOption.point(width / 2, height * 3 / 4)).release().perform();
    }



    /**
     *向左滑动屏幕
     */
    public static void swipeLeft(AppiumDriver driver) {
        //获取手机屏幕的宽度
        int width = driver.manage().window().getSize().width;
        //获取手机屏幕的高度
        int height = driver.manage().window().getSize().height;
        /*new一个TouchAction对象，调用其按压press()方法，输入坐标点,moveTo移动到下一个坐标点，之后调用release()和perform()方法执行，注意，宽乘以或者除以的数字自己来定义计算的，主要是呈现一个公共的方式，兼容所有的手机*/
        new TouchAction(driver).press(PointOption.point(width * 3 / 4, height / 2))
                .moveTo(PointOption.point(width / 10, height / 2)).release().perform().perform();
    }

    /**
     *向右滑动屏幕
     */
    public static void swipeRight(AppiumDriver driver) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        new TouchAction(driver).press(PointOption.point(width / 10, height / 2))
                .moveTo(PointOption.point(width * 3 / 4 , height / 2)).release().perform();
    }
}
