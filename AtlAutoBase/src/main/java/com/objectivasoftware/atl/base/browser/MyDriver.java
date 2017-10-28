package com.objectivasoftware.atl.base.browser;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.objectivasoftware.atl.base.Configurations;
import com.objectivasoftware.atl.base.Constants;
import com.objectivasoftware.atl.base.browser.impl.DriverTypeImpl;
import com.objectivasoftware.atl.base.wait.WaitUtil;

public class MyDriver extends AbstractWebDriver {

	public MyDriver(WebDriver delegate) {
		super(delegate);
	}

	public void skipSSLValidation() {
		try {
			if (DriverTypeImpl.IE.name()
					.equalsIgnoreCase(Configurations.getConfiguration(Constants.SELENIUM_BROWERTYPE))) {
				WaitUtil.waitOn(this).untilAdded(By.id("overridelink"));
				this.findElement(By.id("overridelink")).click();
			} else if (DriverTypeImpl.EDGE.name()
					.equalsIgnoreCase(Configurations.getConfiguration(Constants.SELENIUM_BROWERTYPE))) {
				WaitUtil.waitOn(this).untilAdded(By.id("invalidcert_continue"));
				this.findElement(By.id("invalidcert_continue")).click();
			}
		} catch (Exception e) {
		}
	}

	public void switchToAlert() {
		try {
			Alert alert = super.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
		}
	}
}
