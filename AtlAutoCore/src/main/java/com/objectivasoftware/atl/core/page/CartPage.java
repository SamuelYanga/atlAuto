package com.objectivasoftware.atl.core.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;
import com.objectivasoftware.atl.base.wait.WaitUtil;
import com.objectivasoftware.atl.core.vo.ProductInfo;
import com.objectivasoftware.atl.core.vo.ProductTotalInfo;

public class CartPage extends BasePage {

	// cart-list-content

	// plp-search
	public static final String CART_LIST_CSS = ".cart-list-comp .cart-list-content";
	@FindBy(css = CART_LIST_CSS)
	private List<WebElement> cartList;

	public static final String PRODUCT_ITEM_CHECK = ".fake-check";
	public static final String PRODUCT_ITEM_CODE = ".info-code-num-input";
	public static final String PRODUCT_ITEM_TITLE = ".list-title .blue-a";
	public static final String PRODUCT_ITEM_BV = ".list-bv";
	public static final String PRODUCT_ITEM_PV = ".list-pv";
	public static final String PRODUCT_ITEM_PRICE = ".list-price";
	public static final String PRODUCT_ITEM_NUM = "input[name=productNum]";
	public static final String PRODUCT_ITEM_TOTAL_PRICE = ".list-total-price";

	public static final String PRODUCT_ITEM_ADD_FAV = ".btn-add";
	public static final String PRODUCT_ITEM_DELETE = ".btn-delete";

	private WebElement getProductByCode(String productCode) {
		WebElement result = null;

		for (WebElement element : cartList) {
			WebElement codeElement = element.findElement(By.cssSelector(PRODUCT_ITEM_CODE));
			String code = codeElement.getText().trim();
			if (code.equals(productCode)) {
				result = element;
				break;
			}
		}
		return result;
	}

	public ProductInfo getProductInfo(String productCode) {
		WebElement productElement = getProductByCode(productCode);
		ProductInfo productInfo = getProductInfo(productElement);
		return productInfo;
	}

	public List<ProductInfo> getProductInfo() {
		List<ProductInfo> list = new ArrayList<>();
		for (WebElement element : cartList) {
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
		String num = element.findElement(By.cssSelector(PRODUCT_ITEM_NUM)).getAttribute("value").trim();
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
	public static final String CART_TOTAL_INFO_CSS = ".cart-footer-list .cart-footer-list-child";
	@FindBy(css = CART_TOTAL_INFO_CSS)
	private List<WebElement> cartTotalInfos;
	
	private WebElement getTotalInfoElement(String str) {
		for (WebElement element : cartTotalInfos) {
			
			if (str.equals("BV") && element.getText().contains("BV")) {
				scrollMoveToElement(element);
				return element.findElement(By.cssSelector(".cart-footer-list-child-val"));
			}
			if (str.equals("PV") && element.getText().contains("PV")) {
				scrollMoveToElement(element);
				return element.findElement(By.cssSelector(".cart-footer-list-child-val"));
			}
			if (str.equals("AMOUNT") && element.getText().contains("總金額")) {
				scrollMoveToElement(element);
				return element.findElement(By.cssSelector(".cart-footer-list-child-val"));
			}
		}
		return null;
	}
	
	public ProductTotalInfo getTotalInfo() {
		ProductTotalInfo productTotalInfo = new ProductTotalInfo();
		String totalBv = getTotalInfoElement("BV").getText().trim();
		String totalPv = getTotalInfoElement("PV").getText().trim();
		String totalPrice = getTotalInfoElement("AMOUNT").getText().trim();
		productTotalInfo.setBv(totalBv);
		productTotalInfo.setPv(totalPv);
		productTotalInfo.setAmount(totalPrice);
		return productTotalInfo;
	}

	// cart-footer-box-btn-child1
	public static final String CHECK_OUT_BTN_CSS = ".cart-footer-box-btn-child1";
	@FindBy(css = CHECK_OUT_BTN_CSS)
	private WebElement checkoutBtn;

	public void checkout() {
		scrollMoveToElement(checkoutBtn);
		checkoutBtn.click();
	}

	
	/**
	 * ******************************删除*********************************
	 */
	public static final String REMOVE_ALL_BUTTON_CSS = ".btn-remove-all";
	@FindBy(css = REMOVE_ALL_BUTTON_CSS)
	private WebElement removeAllBtn;

	public static final String REMOVE_CONFIRM_BUTTON_CSS = ".alt-popup.show .submit-button-wrapper .atl-btn";
	@FindBy(css = REMOVE_CONFIRM_BUTTON_CSS)
	private WebElement removeConfirmBtn;
	
	//label[for=cart-check-all]

	public static final String CHECK_ALL_CSS = "label[for=cart-check-all]";
	@FindBy(css = CHECK_ALL_CSS)
	private WebElement checkAll;
	
	public void removeAll() {
		scrollMoveToElement(removeAllBtn);
//		checkAll.click();
//		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		
		removeAllBtn.click();
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
		removeConfirmBtn.click();
		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilRemoved(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
		WaitUtil.waitOn(myDriver).untilPageDown();
	}
	
	public boolean removeAllSuccess() {
		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL2).untilRemoved(By.cssSelector(CART_LIST_CSS));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public void removeBatch(String... productCodes) {
		scrollMoveToElement(checkAll);
		checkAll.click();
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		for (String productCode : productCodes) {
			WebElement productElement = getProductByCode(productCode);
			WebElement checkElement = productElement.findElement(By.cssSelector(PRODUCT_ITEM_CHECK));
			checkElement.click();
			WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);	
		}
		removeAllBtn.click();
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
		removeConfirmBtn.click();
		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilRemoved(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
		WaitUtil.waitOn(myDriver).untilPageDown();
	}

	public boolean removeBatchSuccess(String... productCodes) {
		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL2).untilRemoved(By.cssSelector(CART_LIST_CSS));
		} catch (TimeoutException e) {
			return true;
		}

		List<ProductInfo> productInfoList = getProductInfo();
		for (ProductInfo productInfo : productInfoList) {
			for (String productCode : productCodes) {
				if (productInfo.getCode().equals(productCode)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void removeProduct(String... productCodes) {
		scrollMoveToElement(checkAll);
		checkAll.click();
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		for (String productCode : productCodes) {
			WebElement productElement = getProductByCode(productCode);
			WebElement deleteElement = productElement.findElement(By.cssSelector(PRODUCT_ITEM_DELETE));
			deleteElement.click();
			WaitUtil.waitOn(myDriver).untilElementToBeClickable(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
			removeConfirmBtn.click();
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilRemoved(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
			WaitUtil.waitOn(myDriver).untilPageDown();
		}
	}

	public boolean removeProductSuccess(String... productCodes) {
		return removeBatchSuccess(productCodes);
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * **********************************訂貨、付款、收貨人編號**********************************
	 */
	//radioOther
	public static final String CONSIFNEE_SELF_CSS = "label[for=radioSeft]";
	@FindBy(css = CONSIFNEE_SELF_CSS)
	private WebElement consigneeSelf;

	public static final String CONSIFNEE_OTHER_CSS = "label[for=radioOther]";
	@FindBy(css = CONSIFNEE_OTHER_CSS)
	private WebElement consigneeOther;

	public static final String CONSIFNEE_INPUT_POPUP_CSS = ".consignee-container .alt-popup .changebuyer-form";
	@FindBy(css = CONSIFNEE_INPUT_POPUP_CSS)
	private WebElement consigneeInputPopup;

	// 付款人編號
	public static final String PAYER_NO_CSS = "input[name=payerdistNo]";
	@FindBy(css = PAYER_NO_CSS)
	private WebElement payerdistNoInput;

	// 付款人密码
	public static final String PAYER_PASSWORD_CSS = "input[name=payerpassword]";
	@FindBy(css = PAYER_PASSWORD_CSS)
	private WebElement payerPasswordInput;

	// 订货人編號
	public static final String BUYER_NO_CSS = "input[name=buyerdistNo]";
	@FindBy(css = BUYER_NO_CSS)
	private WebElement buyerdistNoInput;

	// 代收人編號
	public static final String RECEIVER_NO_CSS = "input[name=receiverdistNo]";
	@FindBy(css = RECEIVER_NO_CSS)
	private WebElement receiverdistNoInput;

	public static final String CHANGE_BUYER_CONFIRM_CSS = ".changebuyer-form .atl-btn";
	@FindBy(css = CHANGE_BUYER_CONFIRM_CSS)
	private WebElement changeBuyerConfirm;

	public void popupConsigneeInput() {
		consigneeSelf.click();
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		consigneeOther.click();
		WaitUtil.waitOn(myDriver).untilShown(By.cssSelector(CONSIFNEE_INPUT_POPUP_CSS));
	}
	
	public void inputBuyerInfo(String payerdistNo, String payerPassword, String buyerdistNo, String receiverdistNo) {
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(payerdistNoInput);
		payerdistNoInput.clear();
		payerdistNoInput.sendKeys("");
		payerPasswordInput.clear();
		payerPasswordInput.sendKeys(payerPassword);
		receiverdistNoInput.clear();
		receiverdistNoInput.sendKeys(buyerdistNo);
		changeBuyerConfirm.clear();
		changeBuyerConfirm.sendKeys(receiverdistNo);
		
		changeBuyerConfirm.click();
	}
	
	
	/**
	 * ***********************************送货方式****************************************
	 */
	public static final String CART_LOCATION_CONTENT_CSS = ".cart-location-content-text";
	@FindBy(css = CART_LOCATION_CONTENT_CSS)
	private WebElement cartLocationContent;
	
	public String getLocation() {
		return cartLocationContent.getText().trim();
	}
	
	
}
