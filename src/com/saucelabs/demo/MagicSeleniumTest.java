package com.saucelabs.demo;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class MagicSeleniumTest {
    protected Selenium se1 = null;
    protected WebDriver se2 = null;

    public static final int SAUCE_TV_DELAY = 10;

    private static Log log = LogFactory.getLog(MagicSeleniumTest.class);

    @BeforeMethod
    public void connectToSauceOnDemand(Method method) throws MalformedURLException, InterruptedException {
        if(method.getAnnotation(SeleniumVersion.class) != null) {
            String username = System.getenv("SAUCE_USERNAME");
            String access_key = System.getenv("SAUCE_ACCESS_KEY");
            String host = System.getenv("SAUCE_HOST");
            String port = System.getenv("SAUCE_PORT");
            int version = method.getAnnotation(SeleniumVersion.class).version();
            boolean local = method.getAnnotation(SeleniumVersion.class).local();
            String name = method.getDeclaringClass().getSimpleName() + "." + method.getName() + " on Selenium " + version;

            log.info("Requesting session for "+name);

            if(local && version == 2) {
              se2 = new HtmlUnitDriver();
            } else if(version == 2) {
                DesiredCapabilities capabillities = new DesiredCapabilities("firefox", "3.6.",
                        Platform.WINDOWS);
                capabillities.setCapability("job-name", name);
                capabillities.setCapability("_provider", "sauce");
                if(host == null) {
                    host = "ondemand.saucelabs.com";
                }
                se2 = new RemoteWebDriver(new java.net.URL("http://" + username + ":"
                        + access_key + "@" + host + ":" + port + "/wd/hub"), capabillities);
                openBrowserToJobPage(((RemoteWebDriver)se2).getSessionId().toString());
                log.info("Waiting for Sauce TV to boot up...");
                Thread.sleep(SAUCE_TV_DELAY * 1000);
            } else {
                se1 = new DefaultSelenium(host, Integer.valueOf(port), "{\"username\": \""
                        + username + "\"," + "\"access-key\": \"" + access_key + "\","
                        + "\"os\": \"Windows 2003\", \"_provider\": \"sauce\", " + "\"browser\": \"firefox\","
                        + "\"browser-version\": \"3.6.\"," + "\"name\": \""+name+"\"}",
                        "http://saucelabs.com/");
                se1.start();
                openBrowserToJobPage(se1.getEval("selenium.sessionId"));
                log.info("Waiting for Sauce TV to boot up...");
                Thread.sleep(SAUCE_TV_DELAY * 1000);
            }
            log.info("Running test");
        }
    }

    private void openBrowserToJobPage(String sessionId) {
        if(!java.awt.Desktop.isDesktopSupported()) {
            return;
        }
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if(!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
            return;
        }

        java.net.URI uri;
        try {
            uri = new java.net.URI("http://saucelabs.com/jobs/" + sessionId.toString());
            desktop.browse(uri);
        } catch(URISyntaxException e) {
            return;
        } catch(IOException e) {
            return;
        }
    }

    @AfterMethod
    public void shutDownSelenium() {
        if(se1 != null) {
            se1.stop();
            se1 = null;
        }
        if(se2 != null) {
            se2.quit();
            se2 = null;
        }
    }
}