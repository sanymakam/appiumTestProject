package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testBase.AppiumTestBase;
import utils.AppiumFactory;

public class AppiumSimpleCalculatorTest2 extends AppiumTestBase {
    private AppiumDriver appiumDriver;
    private String mobileDevice;
    private AppiumFactory appiumFactory;

    @BeforeClass(alwaysRun = true)
    public void appSetUp() throws IOException, InterruptedException {
        String filePath=null;
        String env = System.getProperty("env");
        mobileDevice = (String) borrowTestMobile();
        appiumFactory = new AppiumFactory();
        String appiumUrl = appiumFactory.startAppiumService();
        ServerSocket s = new ServerSocket(0);
        int systemPort = s.getLocalPort();
        Reporter.log("listening on port: " + systemPort , true);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("newCommandTimeout", "1000");
        capabilities.setCapability("udid", mobileDevice);
        capabilities.setCapability("uiautomator2ServerLaunchTimeout", 45000);
        capabilities.setCapability("systemPort", systemPort);

        capabilities.setCapability("appPackage", "com.android.calculator2");
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
        appiumDriver = new AndroidDriver(new URL(appiumUrl), capabilities);
    }

    public AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }

    @Test(groups = {"calculator"})
    public void appiumSampleTest() throws Exception {

        WebElement seven = appiumDriver.findElementById("com.android.calculator2:id/digit_6");
        seven.click();
        WebElement plus = appiumDriver.findElementById("com.android.calculator2:id/op_add");
        plus.click();
        WebElement three = appiumDriver.findElementById("com.android.calculator2:id/digit_1");
        three.click();
        WebElement equalTo = appiumDriver.findElementById("com.android.calculator2:id/eq");
        equalTo.click();
        WebElement result = appiumDriver.findElementById("com.android.calculator2:id/result");
        Assert.assertEquals(result.getText(), "7", "Result is not 7");
    }

    @AfterClass(alwaysRun = true)
    public void teardown() throws InterruptedException {
        returnMobileDevice(mobileDevice);
        try{
            if (null != appiumDriver) {
                appiumDriver.closeApp();
            }
            appiumFactory.stopAppiumService();
        } catch (Exception e) {
            Reporter.log("The exception: " + e);
        }
    }
}
