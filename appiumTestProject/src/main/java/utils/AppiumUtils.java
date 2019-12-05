package utils;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class AppiumUtils {
  private AppiumDriver appiumDriver;

  public AppiumUtils(AppiumDriver appiumDriver) {
    this.appiumDriver = appiumDriver;
  }

  public void waitForElement(WebElement element, int sec) throws InterruptedException {
    try {
      WebDriverWait wait = new WebDriverWait(this.appiumDriver, sec);
      wait.until(ExpectedConditions.elementToBeClickable(element));
    } catch (NoSuchElementException e1) {
        for (int i=0;i<3;i++) {
          Thread.sleep(500);
          waitForElement(element, sec);
        }
    } catch (TimeoutException e2) {
      Reporter.log("TimeoutException waiting for the element");
    }
  }

  public void swipeFromLeftToRight(WebElement webElement) throws InterruptedException {
    waitForElement(webElement,20);
    int xAxisStartPoint = webElement.getLocation().getX();
    int xAxisEndPoint = xAxisStartPoint + webElement.getSize().getWidth();
    int yAxis = webElement.getLocation().getY() + webElement.getSize().getHeight() / 2;
    swipe(xAxisStartPoint, yAxis, xAxisEndPoint, yAxis, 1000);
  }

  public void swipe(int startX, int startY, int endX, int endY, int durationInMilliSeconds) {
    new TouchAction(this.appiumDriver)
        .press(point(startX, startY))
        .waitAction(waitOptions(Duration.ofMillis(durationInMilliSeconds)))
        .moveTo(point(endX, endY)).release().perform();
  }

  public Boolean isDisplayed(WebElement element) {
    try {
      if (element.isDisplayed())
        return true;
    } catch (NoSuchElementException e) {
        return false;
    }
    return false;
  }

  public void waitForElementToDisapper(WebElement element, int sec) throws InterruptedException {
    try {
      WebDriverWait wait = new WebDriverWait(this.appiumDriver, sec);
      wait.until(ExpectedConditions.invisibilityOf(element));
    } catch (NoSuchElementException e1) {
      for (int i=0;i<3;i++) {
        Thread.sleep(500);
        waitForElement(element, sec);
      }
    } catch (TimeoutException e2) {
      Reporter.log("TimeoutException waiting for the element");
    }
  }
}

