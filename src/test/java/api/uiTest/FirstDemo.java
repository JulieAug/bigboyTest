//package api.uiTest;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class FirstDemo {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		 // Create a new instance of the Firefox driver
//        // Notice that the remainder of the code relies on the interface,
//        // not the implementation.
//		//设置系统参数，指定火狐浏览器的启动位置。
//		System.setProperty("webdriver.firefox.bin", "E:\\Program Files\\Mozilla Firefox\\firefox.exe");
//		//设置系统参数，指定geckodriver的路径。
//		System.setProperty("webdriver.gecko.driver", "F:\\DnTest\\geckodriver-v0.24.0-win64\\geckodriver.exe");
//		//启动webdriver，作为我们进行操作的浏览器。
//		WebDriver driver = new FirefoxDriver();
//
//        // 访问百度
//        driver.get("http://www.baidu.com");
//        // Alternatively the same thing can be done like this
//        // driver.navigate().to("http://www.google.com");
//
//        // Find the text input element by its name
//        //查找百度上的输入框，name属性为wd
//        WebElement element = driver.findElement(By.name("wd"));
//
//        // Enter something to search for
//        //在输入框元素中输入Cheese!
//        element.sendKeys("Cheese!");
//
//        // Now submit the form. WebDriver will find the form for us from the element
//        //提交表单参数，完成搜索
//        element.submit();
//
//        // Check the title of the page
//        //输出网页标题即html的head元素中title元素的内容。
//        System.out.println("Page title is: " + driver.getTitle());
//
//        // Google's search is rendered dynamically with JavaScript.
//        // Wait for the page to load, timeout after 10 seconds
//        //显式等待，设置最长等待10秒，如果预期的事件发生，则停止等待，继续执行，否则等满10秒之后报错。
//        WebDriverWait ewait=new WebDriverWait(driver, 10);
//        //通过until中的内部类
//        ewait.until(new ExpectedCondition<Boolean>() {
//        	//固定的expectedcondition中的内容，指定apply方法，返回和ExpectedCondition<Boolean>的类型相同的返回值
//            public Boolean apply(WebDriver d) {
//            	//返回Boolean类型的结果
//                return d.getTitle().toLowerCase().startsWith("cheese!");
//            }
//        });
//
//        // Should see: "cheese! - Google Search"
//        //再次输出页面标题
//        System.out.println("Page title is: " + driver.getTitle());
//
//        //Close the browser
//        //关闭driver进程，关掉浏览器。
//        driver.quit();
////        driver.close();
//	}
//
//}