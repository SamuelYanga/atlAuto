package com.objectivasoftware.atl.core.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;
import com.objectivasoftware.atl.base.wait.WaitUtil;
import com.objectivasoftware.atl.base.wait.WaitUtil.UntilEvent;
import com.objectivasoftware.atl.core.vo.ProductInfo;

public class PromotionPage extends BasePage {

	// productNum
	public static final String PRODUCT_ITEMS_CSS = ".prolist-item-li";
	@FindBy(css = PRODUCT_ITEMS_CSS)
	private List<WebElement> productItems;
	

	public static final String PRODUCT_TITLE_CSS = ".prolist-info-zh";//TEXT
	public static final String PRODUCT_CODE_CSS = "label[for=productCode]";//TEXT
	public static final String PRODUCT_PRICE_CSS = ".productlisting-item-price .list-item-value";
	public static final String PRODUCT_PV_CSS = ".productlisting-item-pv .list-item-value";
	public static final String PRODUCT_INCREASE_CSS = ".btn-increase";
	public static final String PRODUCT_INPUT_NUM_CSS = ".qtybox-text";
	public static final String PRODUCT_IMG_CSS = ".prolist-img-detail";
	

	public List<ProductInfo> selectProduct(Map<String, Integer> map) {
		List<ProductInfo> list = new ArrayList<>();
		ProductInfo productInfo = null;
		for (Entry<String, Integer> e : map.entrySet()) {
			productInfo = selectProduct(e.getKey(), e.getValue());
			list.add(productInfo);
		}
		return list;
	}

	private ProductInfo selectProduct(String productCode, int num) {
		WebElement product = getProductElement(productCode);
		ProductInfo productInfo = new ProductInfo();
		String title = product.findElement(By.cssSelector(PRODUCT_TITLE_CSS)).getText().trim();
//		String price = product.findElement(By.cssSelector(PRODUCT_PRICE_CSS)).getText().trim();
//		String pv = product.findElement(By.cssSelector(PRODUCT_PV_CSS)).getText().trim();
		String img = product.findElement(By.cssSelector(PRODUCT_IMG_CSS)).getAttribute("src").trim();
		productInfo.setCode(productCode);
		productInfo.setTitle(title);
//		productInfo.setPv(pv);
//		productInfo.setPrice(price);
		productInfo.setImg(img);

		for (int i = 0; i < num; i++) {
			increaseNum(product, num);
		}
		productInfo.setNum(String.valueOf(num));

		return productInfo;
	}
	
	private void increaseNum(final WebElement element, int num) {
		scrollMoveToElement(element);
		String numValue = element.findElement(By.cssSelector(PRODUCT_INPUT_NUM_CSS)).getAttribute("value").trim();
		WebElement increaseElement = element.findElement(By.cssSelector(PRODUCT_INCREASE_CSS));
		increaseElement.click();
		
		WaitUtil.waitOn(myDriver).untilHidden(By.cssSelector(LOADING));
		
		WaitUtil.waitOn(myDriver, new UntilEvent() {
			
			@Override
			public boolean excute() {
				String temp = element.findElement(By.cssSelector(PRODUCT_INPUT_NUM_CSS)).getText().trim();
				return !temp.equals(numValue);
			}
		}).untilEventHappened();

	}
	
	private WebElement getProductElement(String productCode) {
		for (WebElement element : productItems) {
			WebElement codeElement = element.findElement(By.cssSelector(PRODUCT_CODE_CSS));
			String codeValue = codeElement.getText().trim();
			if (productCode.equals(codeValue)) {
				return element;
			}
		}
		return null;
	}

	public static final String CAN_ARCHIEVE_CSS = ".canArchieve";
	@FindBy(css = CAN_ARCHIEVE_CSS)
	private WebElement canArchieve;

	public static final String HAS_CHOOSE_PRODUCTS_CSS = ".hasChoose-list";
	@FindBy(css = HAS_CHOOSE_PRODUCTS_CSS)
	private List<WebElement> chooseProducts;
	public static final String HAS_CHOOSE_PRODUCT_IMG_CSS = ".hasChoose-list-img";
	public static final String HAS_CHOOSE_PRODUCT_NUM_CSS = ".circle";
	
	public boolean isGiftDisplayed() {
		try {
			myDriver.findElement(By.cssSelector(CAN_ARCHIEVE_CSS));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public boolean checkChooseProduct(List<ProductInfo> list) {
		boolean flag = false;
		for (WebElement element : chooseProducts) {
			scrollMoveToElement(element);
			flag = false;
			String img = element.findElement(By.cssSelector(HAS_CHOOSE_PRODUCT_IMG_CSS)).getAttribute("src").trim();
			String num = element.findElement(By.cssSelector(HAS_CHOOSE_PRODUCT_NUM_CSS)).getText().trim();
			
			for (ProductInfo product : list) {
				String img0 = product.getImg();
				String num0 = product.getNum();
				if (img.equals(img0)) {
					if (num.equals(num0)) {
						flag = true;
						break;
					}
				}
			}
			if (!flag) {
				return false;
			}
		}
		return true;
	}
	
	
	//promotion-footer-button
	public static final String ATC_BUTTON_CSS = ".promotion-footer-button";
	@FindBy(css = ATC_BUTTON_CSS)
	private WebElement addToCardBtn;

	public static final String REMOVE_CONFIRM_BUTTON_CSS = ".alt-popup.show .submit-button-wrapper .atl-btn";
	@FindBy(css = REMOVE_CONFIRM_BUTTON_CSS)
	private WebElement removeConfirmBtn;
	
	public void addToCard() {
		scrollMoveToElement(addToCardBtn);
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(addToCardBtn);
		addToCardBtn.click();

		WaitUtil.waitOn(myDriver).untilElementToBeClickable(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
		removeConfirmBtn.click();
		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilRemoved(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
		WaitUtil.waitOn(myDriver).untilPageDown();
	}
}
