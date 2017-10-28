package com.objectivasoftware.atl.core.component;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BaseComponent;
import com.objectivasoftware.atl.base.wait.WaitUtil;

public class PopupComponent extends BaseComponent {

	public static final String POPUP_SHOW_CSS = ".alt-popup.show";
	@FindBy(css = POPUP_SHOW_CSS)
	private WebElement popup;

	public static final String CONFIRM_BTN_CSS_ = ".submit-button-wrapper .atl-btn";
	
	public void confirmPopup() {
		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilShown(By.cssSelector(POPUP_SHOW_CSS));
			WebElement confirmBtn = popup.findElement(By.cssSelector(CONFIRM_BTN_CSS_));
			confirmBtn.click();
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL3).untilRemoved(By.cssSelector(POPUP_SHOW_CSS));
		} catch (TimeoutException e) {
			return;
		}

	}
	

}
