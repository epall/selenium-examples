package com.saucelabs.demo.pagefactory;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.saucelabs.demo.MagicSeleniumTest;
import com.saucelabs.demo.SeleniumVersion;

public class GoogleSearchPageTest
    extends MagicSeleniumTest {
  @Test
  @SeleniumVersion(version = 2)
  public void testGoogleSearchPage() {
    se2.get("http://www.google.com/");
    GoogleSearchPage page = PageFactory
        .initElements(se2, GoogleSearchPage.class);
    page.searchFor("Cheese");
    assert se2.getPageSource() .indexOf("milk") != -1;
  }
}
