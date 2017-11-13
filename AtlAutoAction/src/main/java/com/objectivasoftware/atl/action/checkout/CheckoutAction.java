package com.objectivasoftware.atl.action.checkout;

import com.objectivasoftware.atl.core.page.CheckoutPage;

import cucumber.api.java.en.And;

public class CheckoutAction {

	@And("使用產品抵用券, 金额=\"(.*)\"")
	public void useTicket1(String value) {
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.useTicket1(value);
	}

	@And("使用e化現金代用券, 金额=\"(.*)\"")
	public void useTicket2(String value) {
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.useTicket2(value);
	}

	@And("使用現金抵用券, 金额=\"(.*)\"")
	public void useTicket3(String value) {
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.useTicket3(value);
	}

	@And("点击核对订单，进入订单核对页面。")
	public void checkout() {
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.checkOrder("543");
	}
}
