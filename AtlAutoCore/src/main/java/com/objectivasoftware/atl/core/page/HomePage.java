package com.objectivasoftware.atl.core.page;

import org.apache.log4j.Logger;

import com.objectivasoftware.atl.base.Configurations;
import com.objectivasoftware.atl.base.Constants;
import com.objectivasoftware.atl.base.browser.DriverFactory;
import com.objectivasoftware.atl.base.browser.MyDriver;
import com.objectivasoftware.atl.base.frame.BasePage;

public class HomePage extends BasePage {

	private final static Logger LOGGER = Logger.getLogger(HomePage.class);

	/**
	 * 打开主页
	 * 
	 * @return
	 */
	public static HomePage open() {
		MyDriver browser = DriverFactory.getBrowser();
		LOGGER.info("#############HomePage.openHomePage start.");
		browser.get(Configurations.getConfiguration(Constants.SELENIUM_TARGETURL));
		browser.skipSSLValidation();
		// browser.switchToAlert();
		browser.manage().window().maximize();

		return new HomePage();
	}

}
