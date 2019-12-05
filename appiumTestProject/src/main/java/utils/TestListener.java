package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

  public void onStart(ITestContext context) {
    System.out.println("*** Test Suite " + context.getName() + " started ***");
  }

  public void onFinish(ITestContext context) {
    System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
    ExtentTestManager.endTest();
    ExtentManager.getInstance().flush();
  }

  public void onTestStart(ITestResult result) {
    System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
    ExtentTestManager.startTest(result.getMethod().getMethodName());
  }

  public void onTestSuccess(ITestResult result) {
    System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
    ExtentTestManager.getTest().log(Status.PASS, "Test passed");
  }

  public void onTestFailure(ITestResult result) {
    ExtentTestManager.getTest().log(Status.FAIL,"Test execution Failed!!");
    ExtentTestManager.getTest().log(Status.FAIL,"Exception:  " + result.getThrowable());

    Class clazz = result.getTestClass().getRealClass();
    Field field = null;
    try {
      field = clazz.getDeclaredField("appiumDriver");
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }

    field.setAccessible(true);

    AppiumDriver<?> driver = null;
    try {
      driver = (AppiumDriver<?>) field.get(result.getInstance());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    String testMethodName = result.getName().toString().trim();
    String screenShotName = testMethodName + timeStamp + ".png";
    String fileSeperator = System.getProperty("file.separator");
    String reportsPath = System.getProperty("user.dir") + fileSeperator + "TestReport" + fileSeperator
        + "Screenshots"+ fileSeperator;
    String destination = null;
    try {
      String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
      TakesScreenshot ts = (TakesScreenshot) driver;
      File source = ts.getScreenshotAs(OutputType.FILE);
      // after execution, you could see a folder "FailedTestsScreenshots"
      // under src folder
      destination = reportsPath + screenShotName;
      File finalDestination = new File(destination);
      FileUtils.copyFile(source, finalDestination);
    } catch (FileNotFoundException e) {
      ExtentTestManager.getTest().log(Status.FAIL, "File not found exception occurred while taking screenshot"+ e.getMessage());
    } catch (Exception e) {
      ExtentTestManager.getTest().log(Status.FAIL, "An exception occurred while taking screenshot " + e.getCause());
    }

    // attach screenshots to report
    try {
      ExtentTestManager.getTest().fail("Screenshot for failure", MediaEntityBuilder.createScreenCaptureFromPath("Screenshots"+
          fileSeperator + screenShotName).build());
    } catch (IOException e) {
      ExtentTestManager.getTest().log(Status.FAIL, "An exception occurred while attaching screenshot " + e.getCause());
    }
  }

  public void onTestSkipped(ITestResult result) {
    System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
    ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
  }
}
