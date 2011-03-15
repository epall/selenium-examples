package com.saucelabs.demo;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class FrameNavigationTest extends MagicSeleniumTest {
  @Test
  @SeleniumVersion(version = 1)
  public void testFrames1() {
    se1.open("http://svallens.com/selenium/seltests/html/NestedFrames.html");
    se1.waitForPageToLoad("30000");
    assert !se1.isTextPresent("This is a test");
    assert se1.getTitle().equals("NestedFrames");
    assert se1.isElementPresent("id=foo");

    se1.selectFrame("bottomFrame");
    assert se1.getText("id=changeTop").equals("changeTop");

    se1.selectFrame("relative=top");
    assert se1.isElementPresent("id=foo");
  }

  @Test
  @SeleniumVersion(version = 2)
  public void testFrames2() {
    se2.get("http://svallens.com/selenium/seltests/html/NestedFrames.html");
    assert se2.getPageSource().indexOf("This is a test") == -1;
    assert se2.getTitle().equals("NestedFrames");
    assert se2.findElement(By.id("foo")) != null;

    se2.switchTo().frame("bottomFrame");
    assert se2.findElement(By.id("changeTop")).getText()
        .equals("changeTop");

    se2.switchTo().defaultContent();
    assert se2.findElement(By.id("foo")) != null;
  }
}
