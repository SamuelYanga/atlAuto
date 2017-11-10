package com.objectivasoftware.atl.core.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;
import com.objectivasoftware.atl.base.wait.WaitUtil;
import com.objectivasoftware.atl.base.wait.WaitUtil.UntilEvent;
import com.objectivasoftware.atl.core.vo.ProductInfo;

public class ProductDetailPage extends BasePage {

	// btn-increase
	public static final String INCREASE_NUM_CSS = ".btn-increase";
	@FindBy(css = INCREASE_NUM_CSS)
	private WebElement increaseBtn;

	// btn-decrease
	public static final String DECREASE_NUM_CSS = ".btn-decrease";
	@FindBy(css = DECREASE_NUM_CSS)
	private WebElement decreaseBtn;

	// productNum
	public static final String PRODUCT_NUM_CSS = "input[name=productNum]";
	@FindBy(css = PRODUCT_NUM_CSS)
	private WebElement productNum;

	// prodetail-content-btn
	public static final String ADD_TO_CART_CSS = ".prodetail-content-btn";
	@FindBy(css = ADD_TO_CART_CSS)
	private WebElement addToCart;

	// add-success
	public static final String ADD_SUCCESS_CSS = ".add-success";
	@FindBy(css = ADD_SUCCESS_CSS)
	private WebElement addSuccess;

	//plp-add-close
	public static final String WARM_REMINDER_CONFIRM_CSS = ".plp-add-close";
	@FindBy(css = WARM_REMINDER_CONFIRM_CSS)
	private WebElement warmReminderConfirm;
	
	public void addToCart(int num) {
		updateProductNum(num);
		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL2).untilElementToBeClickable(addToCart);
		addToCart.click();
		WaitUtil.waitOn(myDriver).untilShown(By.cssSelector(ADD_SUCCESS_CSS));
		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilShown(By.cssSelector(WARM_REMINDER_CONFIRM_CSS));
			warmReminderConfirm.click();
		} catch (TimeoutException e) {
			
		}
//		WaitUtil.waitOn(myDriver).untilShown(By.cssSelector(ADD_SUCCESS_CSS));
	}

	public void updateProductNum(int num) {
		String temp = productNum.getAttribute("value");
		int value = Integer.valueOf(temp);
		int increased = num - value;
		if (increased == 0) {
			return;
		} else if (increased > 0) {
			for (int i = 0; i < (num - value); i++) {
				increase();
			}
		} else {
			for (int i = 0; i < (value - num); i++) {
				decrease();
			}
		}
	}

	private void increase() {
		String value0 = productNum.getAttribute("value");
		increaseBtn.click();
		WaitUtil.waitOn(myDriver, new UntilEvent() {

			@Override
			public boolean excute() {
				String value1 = productNum.getAttribute("value");
				return !value1.equals(value0);
			}
		}).untilEventHappened();
	}

	private void decrease() {
		String value0 = productNum.getAttribute("value");
		decreaseBtn.click();
		WaitUtil.waitOn(myDriver, new UntilEvent() {

			@Override
			public boolean excute() {
				String value1 = productNum.getAttribute("value");
				return !value1.equals(value0);
			}
		}).untilEventHappened();
	}

	// plp-search
	public static final String PRODUCT_INFO_CSS = ".prodetail-content-par-value";
	@FindBy(css = PRODUCT_INFO_CSS)
	private List<WebElement> cartTotalInfo;

	// plp-search
	public static final String PRODUCT_TITLE_CSS = ".prodetail-content-title-zh";
	@FindBy(css = PRODUCT_TITLE_CSS)
	private WebElement productTitle;

	// plp-search
	public static final String PRODUCT_PRICE_CSS = ".prodetail-content-price-value";
	@FindBy(css = PRODUCT_PRICE_CSS)
	private WebElement productPrice;

	public ProductInfo getProductInfo() {
		String id = null;
		String code = null;
		String pv = null;
		String bv = null;
		
		int counter = 0;
		for (WebElement info : cartTotalInfo) {
			if (counter == 0) {
				id = info.getText();
			} else if (counter == 1) {
				code = info.getText();
			} else if (counter == 2) {
				pv = info.getText();
			} else if (counter == 3) {
				bv = info.getText();
			}
			counter++;
		}

		String title = productTitle.getText().trim();
		String price = productPrice.getText().trim();
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(id);
		productInfo.setCode(code);
		productInfo.setPv(pv);
		productInfo.setBv(bv);
		productInfo.setTitle(title);
		productInfo.setPrice(price);
		return productInfo;
	}

}
