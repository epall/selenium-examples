package com.saucelabs.demo;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class AlertHandlingTest extends
    MagicSeleniumTest {
  @Test
  @SeleniumVersion(version = 1)
  public void testAlerts1() {
    se1
        .open("http://svallens.com/selenium/");
    se1
        .waitForPageToLoad("30000");
    se1.click("link=Alert");
    se1.getAlert();
    se1
        .chooseOkOnNextConfirmation();
    se1.click("link=Confirm");
    se1.getConfirmation();
    se1.refresh();
    assert se1
    .isTextPresent("Selenium");
  }

  @Test()
  @SeleniumVersion(version = 2)
  public void testAlerts2() {
    se2
        .get("http://svallens.com/selenium/");
    se2.findElement(
        By.linkText("Alert")).click();
    se2.switchTo().alert()
        .accept();
    se2.findElement(
        By.linkText("Confirm")).click();
    se2.switchTo().alert()
        .accept();
    se2.switchTo()
        .defaultContent();
    assert se2.getPageSource()
        .indexOf("Selenium") != -1;
  }
}
