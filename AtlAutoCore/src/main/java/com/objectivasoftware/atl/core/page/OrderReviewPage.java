package com.objectivasoftware.atl.core.page;

import java.util.ArrayList;
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
import com.objectivasoftware.atl.core.vo.ProductTotalInfo;

public class OrderReviewPage extends BasePage {

	// cart-list-content

	// plp-search
	public static final String PRODUCT_LIST_CSS = ".check-confirm-prolist .cart-list-content";
	@FindBy(css = PRODUCT_LIST_CSS)
	private List<WebElement> productList;
	

	public static final String PRODUCT_ITEM_CODE = ".info-code-num-input";
	public static final String PRODUCT_ITEM_TITLE = ".list-title .blue-a";
	public static final String PRODUCT_ITEM_BV = ".list-bv";
	public static final String PRODUCT_ITEM_PV = ".list-pv";
	public static final String PRODUCT_ITEM_PRICE = ".list-price";
	public static final String PRODUCT_ITEM_NUM = ".list-qtybox";
	public static final String PRODUCT_ITEM_TOTAL_PRICE = ".list-total-price";
	


	public List<ProductInfo> getProductInfo() {
		List<ProductInfo> list = new ArrayList<>();
		for (WebElement element : productList) {
			ProductInfo productInfo = getProductInfo(element);
			list.add(productInfo);
		}
		return list;
	}

	private ProductInfo getProductInfo(WebElement element) {
		String code = element.findElement(By.cssSelector(PRODUCT_ITEM_CODE)).getText().trim();
		String title = element.findElement(By.cssSelector(PRODUCT_ITEM_TITLE)).getText().trim();
		String bv = element.findElement(By.cssSelector(PRODUCT_ITEM_BV)).getText().trim();
		String pv = element.findElement(By.cssSelector(PRODUCT_ITEM_PV)).getText().trim();
		String price = element.findElement(By.cssSelector(PRODUCT_ITEM_PRICE)).getText().trim();
		String num = element.findElement(By.cssSelector(PRODUCT_ITEM_NUM)).getText().trim();
		String totalPrice = element.findElement(By.cssSelector(PRODUCT_ITEM_TOTAL_PRICE)).getText().trim();
		ProductInfo productInfo = new ProductInfo();
		productInfo.setCode(code);
		productInfo.setTitle(title);
		productInfo.setBv(bv);
		productInfo.setPv(pv);
		productInfo.setPrice(price);
		productInfo.setNum(num);
		productInfo.setTotalPrice(totalPrice);
		return productInfo;
	}
	
	


	// plp-search
	public static final String USE_TICKET_INFO_CSS = ".order-detail-coupon .order-list-detail";
	@FindBy(css = USE_TICKET_INFO_CSS)
	private List<WebElement> useTicketInfos;
	
	private WebElement getUseTicketInfoElement(String str) {
		for (WebElement element : useTicketInfos) {
			
			String nameElement = element.findElement(By.cssSelector(".order-list-text")).getText().trim();

			if (str.equals("ticket1") && nameElement.contains("使用產品抵用券")) {
				scrollMoveToElement(element);
				return element.findElement(By.cssSelector(".order-list-value"));
			}
			
			if (str.equals("ticket2") && nameElement.contains("e化現金代用券")) {
				scrollMoveToElement(element);
				return element.findElement(By.cssSelector(".order-list-value"));
			}
			
			if (str.equals("ticket3") && nameElement.contains("使用現金抵用券")) {
				scrollMoveToElement(element);
				return element.findElement(By.cssSelector(".order-list-value"));
			}
		}
		return null;
	}
	
	
	
	//.order-detail-coupon .order-list-detail .order-list-value
	public static final String USE_COUPON_LIST_CSS = ".order-detail-coupon .order-list-detail .order-list-value";
	@FindBy(css = USE_COUPON_LIST_CSS)
	private List<WebElement> userCouponList;

	public String getTicket1Used() {
		return getUseTicketInfoElement("ticket1").getText().trim();
	}

	public String getTicket2Used() {
		return getUseTicketInfoElement("ticket2").getText().trim();
	}

	public String getTicket3Used() {
		return getUseTicketInfoElement("ticket3").getText().trim();
	}
	
	
	/**
	 * *******************************送出订单*******************************
	 */
	public static final String CREATE_ORDER_CSS = ".cart-check-button-next";
	@FindBy(css = CREATE_ORDER_CSS)
	private WebElement createOrderBtn;

	public void createOrder() {
		scrollMoveToElement(createOrderBtn);
		createOrderBtn.click();
		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL3).untilElementToBeClickable(confirmBtn);
			confirmBtn.click();
		} catch (TimeoutException e) {
			
		}

		WaitUtil.waitOn(myDriver).untilPageDown();

		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL4)
				.untilShown(By.cssSelector(OrderConfirmationPage.ORDERID_LINK_CSS));
	}

	//.submit-button-wrapper button[type=submit].atl-btn
	public static final String CONFIRM_BUTTON_CSS = ".submit-button-wrapper button[type=submit].atl-btn";
	@FindBy(css = CONFIRM_BUTTON_CSS)
	private WebElement confirmBtn;
	
}
