package com.objectivasoftware.atl.core.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;
import com.objectivasoftware.atl.base.wait.WaitUtil;
import com.objectivasoftware.atl.core.component.PopupComponent;

public class LoginPage extends BasePage {
	public static final String USERNAME_INPUT_ID = "j_username";
	@FindBy(id = USERNAME_INPUT_ID)
	private WebElement usernameInput;

	public static final String PASSWORD_INPUT_ID = "j_password";
	@FindBy(id = PASSWORD_INPUT_ID)
	private WebElement passwordInput;

	public static final String LOGIN_BTN_CSS = ".login-btn";
	@FindBy(css = LOGIN_BTN_CSS)
	private WebElement loginButton;
	
	public void login(String userName, String password) {
		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL2).untilElementToBeClickable(usernameInput);
		usernameInput.clear();
		usernameInput.sendKeys(userName);
		passwordInput.clear();
		passwordInput.sendKeys(password);
		loginButton.click();

		PopupComponent popupComponent = new PopupComponent();
		popupComponent.confirmPopup();
	}
}
