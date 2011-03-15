package com.saucelabs.demo;

import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.ActionChainsGenerator;
import org.testng.annotations.Test;

public class AdvancedUserInteractionsTest extends
    MagicSeleniumTest {
  @Test
  @SeleniumVersion(version = 2, local = true)
  public void testFancyKeys() {
    se2.get("http://saucelabs.com");
    ActionChainsGenerator builder = ((HasInputDevices) se2).actionsBuilder();

    builder.keyDown(Keys.CONTROL)
        .keyUp(Keys.CONTROL).build().perform();
  }
}
