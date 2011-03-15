package com.saucelabs.demo.pagefactory;

import org.openqa.selenium.WebElement;

public class GoogleSearchPage {
  protected WebElement q;

  public void searchFor(String query) {
    q.sendKeys(query);
    q.submit();
  }
}
