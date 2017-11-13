package com.objectivasoftware.atl.core.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
//		browser.manage().window().maximize();

		return new HomePage();
	}

	public static final String EXPIRED_USER_MSG_CSS = ".expand-user-time";
	@FindBy(css = EXPIRED_USER_MSG_CSS)
	private WebElement expiredUserMsg;

	public String getExpiredUserMsg() {
		return expiredUserMsg.getText().trim();
	}

}
