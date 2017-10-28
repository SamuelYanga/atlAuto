package com.objectivasoftware.atl.base.frame;

import com.objectivasoftware.atl.base.Configurations;
import com.objectivasoftware.atl.base.Constants;
import com.objectivasoftware.atl.base.browser.DriverFactory;
import com.objectivasoftware.atl.base.browser.MyDriver;
import com.objectivasoftware.atl.base.wait.MyElementLocatorFactory;
import com.objectivasoftware.atl.base.wait.WaitUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.Collections;
import java.util.List;

public class AbstractFrame {

	protected MyDriver myDriver;

	public final static String TARGET_URL = Configurations.getConfiguration(Constants.SELENIUM_TARGETURL);

	public AbstractFrame() {
		this.myDriver = DriverFactory.getBrowser();
		waitPageLoadIdentifier();
		PageFactory.initElements(new MyElementLocatorFactory(myDriver), this);
	}

	private void waitPageLoadIdentifier() {
		WaitUtil.waitOn(myDriver).untilPageDown();
		List<String> pageIdentitfierList = getLoadIdentifier();
		for (String pageIdentitfier : pageIdentitfierList) {
			WaitUtil.waitOn(this.myDriver).untilAdded(By.id(pageIdentitfier));
		}
	}

	protected List<String> getLoadIdentifier() {
		return Collections.emptyList();
	}
}
