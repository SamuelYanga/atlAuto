package com.objectivasoftware.atl.base.browser;

import com.objectivasoftware.atl.base.Configurations;
import com.objectivasoftware.atl.base.Constants;
import com.objectivasoftware.atl.base.browser.impl.DriverTypeImpl;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverThread {
    private static Logger LOGGER = Logger.getLogger(DriverThread.class);

    private MyDriver Browser;
    private DriverType selectedBrowserType;

    private final DriverType defaultBrowserType = DriverTypeImpl.FIREFOX;
    private final String browserParameter = Configurations.getConfiguration(Constants.SELENIUM_BROWERTYPE) == null ? defaultBrowserType.toString().toUpperCase() : Configurations.getConfiguration(Constants.SELENIUM_BROWERTYPE).toUpperCase();
    private final String operatingSystem = Configurations.getConfiguration(("os.name").toUpperCase());
    private final String systemArchitecture = Configurations.getConfiguration(("os.arch"));
    private final boolean useRemoteBrowser = Constants.SELENIUM_MODE_LOCAL.equals(Configurations.getConfiguration(Constants.SELENIUM_MODE)) ? false : true;

    public MyDriver getDriver() {
        if (null == Browser) {
            selectedBrowserType = determineEffectiveBrowserType();
            DesiredCapabilities desiredCapabilities = selectedBrowserType.getDesiredCapabilities();
            try {
				instantiateBrowser(desiredCapabilities);
			} catch (MalformedURLException e) {
				LOGGER.error("Init remote driver failed", e);
				//init driver failed, stop program by throw Exception
				throw new RuntimeException("Init driver failed, hub is: " 
						 + Configurations.getConfiguration(Constants.SELENIUM_HUBADDRESS));
			} 
        }

        return Browser;
    }

    public void quitBrowser() {
        if (null != Browser) {
            Browser.quit();
        }
    }

    private DriverType determineEffectiveBrowserType() {
        DriverType BrowserType = defaultBrowserType;
        try {
            BrowserType = DriverTypeImpl.valueOf(browserParameter);
        } catch (IllegalArgumentException ignored) {
            LOGGER.error("Unknown driver specified, defaulting to '" + BrowserType + "'...");
        } catch (NullPointerException ignored) {
            LOGGER.error("No driver specified, defaulting to '" + BrowserType + "'...");
        }
        return BrowserType;
    }

    private void instantiateBrowser(DesiredCapabilities desiredCapabilities) throws MalformedURLException {
        LOGGER.info(" ");
        LOGGER.info("Current Operating System: " + operatingSystem);
        LOGGER.info("Current Architecture: " + systemArchitecture);
        LOGGER.info("Current Browser Selection: " + selectedBrowserType);
        LOGGER.info(" ");
        if (useRemoteBrowser) {
            URL seleniumGridURL = new URL(Configurations.getConfiguration(Constants.SELENIUM_HUBADDRESS));
            String desiredBrowserVersion = Configurations.getConfiguration(Constants.SELENIUM_DESIREDbROWSERVERSION);
            String desiredPlatform = Configurations.getConfiguration(Constants.SELENIUM_DESIREDPLATFORM);

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            Browser = new MyDriver(new RemoteWebDriver(seleniumGridURL, desiredCapabilities));
        } else {
            Browser = selectedBrowserType.getBrowserObject(desiredCapabilities);
        }
        Browser.manage().timeouts().pageLoadTimeout(Long.parseLong(Configurations.getConfiguration(Constants.SELENIUM_WAITTIME)) * 2, TimeUnit.MILLISECONDS);
    }
}
