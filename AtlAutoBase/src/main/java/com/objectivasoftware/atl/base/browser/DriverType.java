package com.objectivasoftware.atl.base.browser;

import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverType {
    MyDriver getBrowserObject(DesiredCapabilities desiredCapabilities);

    DesiredCapabilities getDesiredCapabilities();
}
