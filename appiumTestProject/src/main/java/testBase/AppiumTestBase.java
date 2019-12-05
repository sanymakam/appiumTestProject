package testBase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import utils.ShellUtils;

public class AppiumTestBase {
  static LinkedBlockingQueue<String> mobileQueue  = new LinkedBlockingQueue<>();

  @BeforeSuite(alwaysRun = true)
  public void suiteLevelSetup() throws InterruptedException {
    setTestMobiles();
  }

  public void setTestMobiles() throws InterruptedException {
    ShellUtils shell = new ShellUtils();
    StringBuilder mobileString = shell.executeCommand("adb devices");
    List<String> mobileList = Arrays.asList(mobileString.toString().split("\n"));

    for (int i=1;i<mobileList.size();i++) {
      mobileQueue.put(mobileList.get(i).split("\t")[0]);
    }
    Reporter.log("NUmber of test devices:" + mobileQueue.size(), true);
  }

  public Object borrowTestMobile() throws InterruptedException {
    return mobileQueue.poll(10, TimeUnit.SECONDS);
  }

  public void returnMobileDevice(String mobileDevice) throws InterruptedException {
    try{
      mobileQueue.put(mobileDevice);
      Reporter.log("Added back test mobile to queue "+ mobileDevice, true);
    } catch (NullPointerException e) {
      Reporter.log("Exception while returning test mobile "+ e, true);
    }
  }

}
