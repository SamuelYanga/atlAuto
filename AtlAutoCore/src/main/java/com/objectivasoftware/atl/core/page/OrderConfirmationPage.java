package com.objectivasoftware.atl.core.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;

public class OrderConfirmationPage extends BasePage {

	public static final String ORDERID_LINK_CSS = ".orderconfirm-orderid-link";
	@FindBy(css = ORDERID_LINK_CSS)
	private WebElement orderId;

	public String getOrderId() {
		return orderId.getText().trim();
	}
}
