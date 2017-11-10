package com.objectivasoftware.atl.core.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;
import com.objectivasoftware.atl.base.wait.WaitUtil;
import com.objectivasoftware.atl.base.wait.WaitUtil.UntilEvent;
import com.objectivasoftware.atl.core.util.LeftMenu;

public class SearchPage extends BasePage {

	// prolist-img-detail
	public static final String PRODUCT_IMG_CSS = ".prolist-img-detail";
	public static final String PRODUCT_CODE_CSS = ".prolist-info-code label span";

	// plp-search
	public static final String PRODUCT_LIST_CSS = ".prolist-ul .prolist-item-li";
	@FindBy(css = PRODUCT_LIST_CSS)
	private List<WebElement> productList;

	public void navigateToPdpByImg(String productCode) {
		WebElement product = selectProductByCode(productCode);
		WebElement imgElement = product.findElement(By.cssSelector(PRODUCT_IMG_CSS));
		imgElement.click();
	}

	int counter = 0;

	private WebElement selectProductByCode(String productCode) {
		WebElement result = null;

		for (; counter < productList.size(); counter++) {
			WebElement temp = productList.get(counter);
			WebElement codeElement = temp.findElement(By.cssSelector(PRODUCT_CODE_CSS));
			String code = codeElement.getText();
			if (productCode.equals(code)) {
				result = temp;
				break;
			}
		}

		if (result == null) {
			if (loadMoreProduct()) {
				result = selectProductByCode(productCode);
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
	
	
	/**
	 * ****************************************leftMenu******************************************
	 */

	// .atl-filter-plp>li.filter-list
	public static final String H1 = ".atl-filter-plp>li.filter-list>a.filter-list-title[href*=ATL-Catalogue]";
	@FindBy(css = H1)
	private WebElement h1;

	public void test() {
		WebElement temp = h1.findElement(By.cssSelector("~a.filter-list-elastic"));
		String classValue = temp.getAttribute("class");
		System.out.println(classValue);
	}

	public static final String LEFT_MENU_SECTION_CSS = ".atl-filter-plp>li.filter-list";
	@FindBy(css = LEFT_MENU_SECTION_CSS)
	private List<WebElement> leftMenus;

	public Map<String, List<String>> getLeftMenuInfo() {
		Map<String, List<String>> map = new HashMap<>();
		for (LeftMenu firstMenu : LeftMenu.values()) {
			WebElement first = getLeftSection(firstMenu);
			openLeftSecondMenu(first);

			List<String> list = getSecondMenuInfo(first);
			map.put(firstMenu.name(), list);
		}
		return map;
	}

	public WebElement getNutritionHealthSection() {
		return getLeftSection(LeftMenu.營養保健);
	}

	public WebElement getBeautyMaintenanceSection() {
		return getLeftSection(LeftMenu.美容保養);
	}

	public WebElement getPersonalCareSection() {
		return getLeftSection(LeftMenu.個人保養);
	}

	public WebElement getHomeCareSection() {
		return getLeftSection(LeftMenu.家用產品);
	}

	public WebElement getOtherProductsSection() {
		return getLeftSection(LeftMenu.其他產品);
	}

	public WebElement getLeftSection(LeftMenu left) {
		for (WebElement temp : leftMenus) {
			try {
				temp.findElement(By.cssSelector("a.filter-list-title[href*=\"" + left.getName() + "\"]"));
				return temp;
			} catch (NoSuchElementException e) {
				continue;
			}
		}
		return null;
	}

	public static String ELASTIC_CSS = "a.filter-list-title[href*=ATL-Catalogue]~a.filter-list-elastic";

	private void openLeftSecondMenu(WebElement first) {
		WebElement elastic = first.findElement(By.cssSelector(ELASTIC_CSS));

		scrollMoveToElement(elastic);
		String classValue = elastic.getAttribute("class");
		if (classValue.contains("active")) {
			return;
		}
		elastic.click();
		WaitUtil.waitOn(myDriver, new UntilEvent() {

			@Override
			public boolean excute() {
				String classValue0 = elastic.getAttribute("class");
				return classValue0.contains("active");
			}
		}).untilEventHappened();
	}

	public static String SECOND_ITEMS_CSS = "a.filter-list-title[href*=ATL-Catalogue]~div.collapse-wrapper>ul>li.filter-list-sub>p.filter-list-title>label.atl-checkbox>span.check-text";

	private List<String> getSecondMenuInfo(WebElement first) {
		List<WebElement> items = first.findElements(By.cssSelector(SECOND_ITEMS_CSS));
		List<String> result = new ArrayList<>();
		for (WebElement item : items) {
			String value = item.getText().trim();
			if (value.contains("(")) {
				value = value.substring(0, value.indexOf("(")).trim();
			}
			result.add(value);
		}
		return result;
	}

}
