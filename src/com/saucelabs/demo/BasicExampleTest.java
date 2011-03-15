package com.saucelabs.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class BasicExampleTest extends MagicSeleniumTest {
  @Test
  @SeleniumVersion(version = 1)
  public void testIsTextPresent() {
    se1.open("http://bing.com/");
    se1.type("q", "Selenium");
    se1.click("go");
    se1.waitForPageToLoad("30000");
    assert se1.isTextPresent("testing");
    se1.click("css=li.sa_wr:nth-child(2) h3 a");
    se1.waitForPageToLoad("30000");
    assert se1.getTitle().equals(
        "Selenium web application testing system");
  }

  @Test
  @SeleniumVersion(version = 2)
  public void testIsTextPresent2() {
    se2.get("http://bing.com/");
    WebElement field = se2.findElement(By.id("sb_form_q"));
    field.sendKeys("Selenium");
    field.submit();
    assert se2.getPageSource().indexOf("testing") != -1;

    se2.findElements(By.cssSelector("li.sa_wr h3 a"))
        .get(1).click();
    assert se2.getTitle().equals(
        "Selenium web application testing system");
  }
}
