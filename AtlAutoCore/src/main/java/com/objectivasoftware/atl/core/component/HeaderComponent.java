package com.objectivasoftware.atl.core.component;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BaseComponent;
import com.objectivasoftware.atl.core.page.LoginPage;

public class HeaderComponent extends BaseComponent {

	public static final String LOGIN_LINK_CSS = ".user-detail .login-msg";
	@FindBy(css = LOGIN_LINK_CSS)
	private WebElement loginLink;

	public void login(String userName, String password) {
		loginLink.click();

		LoginPage loginPage = new LoginPage();
		loginPage.login(userName, password);
	}

	public static final String LOGIN_USERNAME_CSS = ".user-detail .login-msg span";
	@FindBy(css = LOGIN_USERNAME_CSS)
	private WebElement userName;

	public String getLoginUserName() {
		return userName.getText();
	}

}
