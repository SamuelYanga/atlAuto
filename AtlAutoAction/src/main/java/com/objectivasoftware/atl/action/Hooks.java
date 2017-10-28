package com.objectivasoftware.atl.action;

import java.util.Set;

import org.openqa.selenium.OutputType;

import com.objectivasoftware.atl.base.browser.DriverFactory;
import com.objectivasoftware.atl.base.browser.MyDriver;
import com.objectivasoftware.atl.base.frame.BasePage;

import cucumber.api.Scenario;
import cucumber.api.java.After;

public class Hooks {

	@After(order = 13000)
	public void closeWindow(Scenario scenario) {

		MyDriver browser = DriverFactory.getBrowser();
		Set<String> handles = browser.getWindowHandles();

		int size = handles.size();
		for (String str : handles) {
			browser.switchTo().window(str);
			if (size > 1) {
				browser.close();
				size--;
			}
		}
	}

	@After(order = 11000)
	public void embedScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			scenario.write("Current Page URL is " + DriverFactory.getBrowser().getCurrentUrl());
			byte[] screenshot = DriverFactory.getBrowser().getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}

		if (BasePage.imgList != null && BasePage.imgList.size() > 0) {
			for (byte[] screenshot : BasePage.imgList) {
				scenario.embed(screenshot, "image/png");
			}
			BasePage.imgList.clear();
		}
	}

	@After(order = 9000)
	public void clearCookies(Scenario scenario) {
//		DriverFactory.clearCookies();
	}

}
