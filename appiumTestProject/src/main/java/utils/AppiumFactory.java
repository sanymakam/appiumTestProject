package utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumFactory {
  AppiumDriverLocalService appiumService;
  public String startAppiumService() {
    this.appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort());
    this.appiumService.start();
    return this.appiumService.getUrl().toString();
  }

  public void stopAppiumService() {
    this.appiumService.stop();
  }
}
