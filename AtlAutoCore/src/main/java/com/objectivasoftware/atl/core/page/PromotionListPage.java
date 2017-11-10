package com.objectivasoftware.atl.core.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;
import com.objectivasoftware.atl.base.wait.WaitUtil;

public class PromotionListPage extends BasePage {


	// productNum
	public static final String PROMOTION_ITEM_CSS = ".PromotionList-wrap-child-will";
	@FindBy(css = PROMOTION_ITEM_CSS)
	private List<WebElement> promotionItems;

	public static final String PROMOTION_TITLE_CSS = ".promotionbanner-content-title";
	public static final String PROMOTION_JOIN_NOW_CSS = ".PromotionList-wrap-child-btn";
	
	public void joinPromotion(String code) {
		WebElement promotionItem = getPromotionElement(code);
		promotionItem.click();
		WaitUtil.waitOn(myDriver).untilPageDown();
	}

	int counter = 0;
	private WebElement getPromotionElement(String code) {
		WebElement result = null;

		for (; counter < promotionItems.size(); counter++) {
			WebElement temp = promotionItems.get(counter);
			scrollMoveToElement(temp);
			WebElement codeElement = temp.findElement(By.cssSelector(PROMOTION_JOIN_NOW_CSS));
			String codeValue = codeElement.getAttribute("href");
			if (codeValue.contains(code)) {
				result = codeElement;
				break;
			}
		}

		if (result == null) {
			if (loadMoreProduct()) {
				result = getPromotionElement(code);
			} else {
				return null;
			}
		}
		return result;
	}


	// loadmore-icon
	private boolean loadMoreProduct() {
		try {
			myDriver.findElement(By.className("loadmore-icon"));
		} catch (NoSuchElementException e) {
			return false;
		}
		windowScrollToBottom();
		return true;
	}
}
