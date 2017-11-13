package com.objectivasoftware.atl.core.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.Configurations;
import com.objectivasoftware.atl.base.Constants;
import com.objectivasoftware.atl.base.frame.BaseComponent;
import com.objectivasoftware.atl.base.wait.WaitUtil;
import com.objectivasoftware.atl.base.wait.WaitUtil.UntilEvent;
import com.objectivasoftware.atl.core.page.CartPage;
import com.objectivasoftware.atl.core.page.LoginPage;
import com.objectivasoftware.atl.core.util.FirstMenu;
import com.objectivasoftware.atl.core.util.Location;

public class HeaderComponent extends BaseComponent {

	public static final String REMOVE_CONFIRM_BUTTON_CSS = ".alt-popup.show .submit-button-wrapper .atl-btn";
	@FindBy(css = REMOVE_CONFIRM_BUTTON_CSS)
	private WebElement removeConfirmBtn;
	
	public static final String LOGIN_LINK_CSS = ".user-detail .login-msg";
	@FindBy(css = LOGIN_LINK_CSS)
	private WebElement loginLink;

	public void checkLogin(String userName, String password) {
		checkLogin(userName, password, false);
	}

	public void checkLogin(String userName, String password, boolean cleanCartFlag) {
		if (isLogin()) {
			logout();
		}

		login(userName, password);

		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL2).untilElementToBeClickable(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
			removeConfirmBtn.click();
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilRemoved(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
			WaitUtil.waitOn(myDriver).untilPageDown();
		} catch (TimeoutException e) {
			
		}

		if (cleanCartFlag) {
			if (!isCartNull()) {
				cleanCart();
				myDriver.get(Configurations.getConfiguration(Constants.SELENIUM_TARGETURL));
			}
		}
	}

	public void login(String userName, String password) {

		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL2);
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(loginLink);
		loginLink.click();

		LoginPage loginPage = new LoginPage();
		loginPage.login(userName, password);
	}

	public static final String LOGIN_USERNAME_CSS = ".user-detail .login-msg";
	@FindBy(css = LOGIN_USERNAME_CSS)
	private WebElement userName;

	public String getLoginUserName() {
		return userName.getText().trim();
	}

	public boolean isLogin() {
		String classValue = loginLink.getAttribute("class");
		return classValue.contains("login-true");
	}

	public static final String USER_DROPDOWN_CSS = ".dropdown.user-dropdown";
	@FindBy(css = USER_DROPDOWN_CSS)
	private WebElement userDropDown;

	public static final String LOGOUT_LINK_CSS = "a.dropdown-a[href*=logout]";
	@FindBy(css = LOGOUT_LINK_CSS)
	private WebElement logoutLink;

	public static final String USER_DETAIL_CSS = ".user-detail .login-true";
	@FindBy(css = USER_DETAIL_CSS)
	private WebElement userDetailLink;
	public void logout() {
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL2);
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(userDetailLink);
		userDetailLink.click();

		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1, new UntilEvent() {

			@Override
			public boolean excute() {
				String classValue = userDropDown.getAttribute("class");
				return classValue.contains("show");
			}
		}).untilEventHappened();

		logoutLink.click();

		WaitUtil.waitOn(myDriver).untilPageDown();

	}

	public static final String PLP_SEARCH_INPUT_ID = "plp-search";
	@FindBy(id = PLP_SEARCH_INPUT_ID)
	private WebElement searchInput;

	public static final String PLP_SEARCH_ICON_CLASS = "search-icon";
	@FindBy(className = PLP_SEARCH_ICON_CLASS)
	private WebElement searchButton;
	
	public void searchProduct(String productCode) {
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(searchInput);
		searchInput.clear();
		searchInput.sendKeys(productCode);
		
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(searchButton);
		searchButton.click();
		WaitUtil.waitOn(myDriver).untilPageDown();

	}

	public static final String MINICART_LINK_CSS = "minicart-detail";
	@FindBy(className = MINICART_LINK_CSS)
	private WebElement miniCartLink;
	
	public void navigateToCartPage() {
		scrollMoveToElement(miniCartLink);
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(miniCartLink);
		miniCartLink.click();
	}

	public static final String MINICART_NUM_CSS = ".cart-num";
	@FindBy(css = MINICART_NUM_CSS)
	private WebElement miniCartNum;

	public boolean isCartNull() {
		String numStr = miniCartNum.getText().trim();
		return "".equals(numStr) || "0".equals(numStr);
	}
	
	public void cleanCart() {
		this.navigateToCartPage();
		CartPage cartPage = new CartPage();
		cartPage.removeAll();
	}

	/**
	 * ********************************promotion***********************************
	 */
	//.nav-menu .navi-container
	public static final String FIRST_MENU_CSS = ".nav-menu .navi-container";
	@FindBy(css = FIRST_MENU_CSS)
	private List<WebElement> firstMenus;
	
	public void openSecondMenu() {
		openSecondMenu(FirstMenu.購物專區);
	}
	
	public void openSecondMenu(FirstMenu first) {
		WebElement firstMenu = firstMenus.get(first.getNum());
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(firstMenu);
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL0);
		firstMenu.click();

		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL3, new UntilEvent() {
			@Override
			public boolean excute() {
				WebElement popup = firstMenu.findElement(By.cssSelector(".subnavi-none"));
				String value = popup.getAttribute("value");
				return value != null && value.contains("showsublist");
			}
		}).untilEventHappened();
	}

	public static final String SECOND_MENU_PROMOTION_CSS = ".navi-block-0 a[href*=promotionlisting]";
	@FindBy(css = SECOND_MENU_PROMOTION_CSS)
	private WebElement promotionListingLink;
	
	public void selectPromotionListing() {
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(promotionListingLink);
		promotionListingLink.click();
		WaitUtil.waitOn(myDriver).untilPageDown();
	}
	
	
	/**
	 * ************************************送货方式变更*********************************************
	 */
	//header-change-location
	public static final String CURRENT_LOCATION_CSS = "#header-change-location";
	@FindBy(css = CURRENT_LOCATION_CSS)
	private WebElement currentLocation;

	//change-location
	public static final String CHANGE_LOCATION_LINK_CSS = ".change-location";
	@FindBy(css = CHANGE_LOCATION_LINK_CSS)
	private WebElement changeLocationLink;
	
	//location-dropdown
	public static final String LOCATION_DROPDOWN_CSS = ".location-dropdown";
	@FindBy(css = LOCATION_DROPDOWN_CSS)
	private WebElement locationDropdown;

	public void openLocationDropdown() {
		String classValue = locationDropdown.getAttribute("class");
		if (classValue.contains("show")) {
			return;
		}
		
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(changeLocationLink);
		changeLocationLink.click();
		WaitUtil.waitOn(myDriver, new UntilEvent() {
			@Override
			public boolean excute() {
				String temp = locationDropdown.getAttribute("class");
				return temp.contains("show");
			}
		}).untilEventHappened();
	}

	public static final String LOCATION_LIST_CSS = ".location-dropdown .dropdown-li a";
	@FindBy(css = LOCATION_LIST_CSS)
	private List<WebElement> locationList;

	public void changeLocation(Location location) {
		String name = location.getName();
		for (WebElement element : locationList) {
			String temp = element.getText();
			if (name.equals(temp)) {
				element.click();
				
				WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilShown(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
				List<WebElement> btns = myDriver.findElements(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
				btns.get(1).click();
				
				WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilRemoved(By.cssSelector(REMOVE_CONFIRM_BUTTON_CSS));
				
				WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL2, new UntilEvent() {
					@Override
					public boolean excute() {
						String current = currentLocation.getText().trim();
						return current.equals(name);
					}
				}).untilEventHappened();
				
				break;
			}
		}
	}
	
	
	/**
	 * ****************************************UI******************************************
	 */
	// 營養保健
	public static final String YINGYANGBAOJIAN_SECOND_MENU_CSS = ".navi-block-2 .subnavi-content-link";
	@FindBy(css = YINGYANGBAOJIAN_SECOND_MENU_CSS)
	private List<WebElement> yingyangbaojian2st;

	// 美容保養
	public static final String MEIREONGBAOYANG_SECOND_MENU_CSS = ".navi-block-3 .subnavi-content-link";
	@FindBy(css = MEIREONGBAOYANG_SECOND_MENU_CSS)
	private List<WebElement> meirongbaoyang2st;

	// 個人保養
	public static final String GERENBAOYANG_SECOND_MENU_CSS = ".navi-block-4 .subnavi-content-link";
	@FindBy(css = GERENBAOYANG_SECOND_MENU_CSS)
	private List<WebElement> gerenbaoyang2st;

	// 家用產品
	public static final String JIAYONGCHANPIN_SECOND_MENU_CSS = ".navi-block-5 .subnavi-content-link";
	@FindBy(css = JIAYONGCHANPIN_SECOND_MENU_CSS)
	private List<WebElement> jiayongchanpin;

	// 其他產品
	public static final String QITACHANPIN_SECOND_MENU_CSS = ".navi-block-6 .subnavi-content-link";
	@FindBy(css = QITACHANPIN_SECOND_MENU_CSS)
	private List<WebElement> qitachanpin2st;

	public Map<String, List<String>> getMenuInfo() {
		Map<String, List<String>> map = new HashMap<>();

		for (FirstMenu first : FirstMenu.values()) {
			if (FirstMenu.isProductCategory(first)) {
				openSecondMenu(first);
				map.put(first.name(), getSecondMenuInfo(first));
				searchInput.click();
				WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL0);
			}
		}

		return map;
	}

	private List<String> getSecondMenuInfo(FirstMenu first) {

		List<String> list = null;
		list = getSecondMenuInfo(firstMenus.get(first.getNum()));

		return list;
	}

	private List<String> getSecondMenuInfo(WebElement element) {
		WebElement sub = null;

		try {
			sub = element.findElement(By.cssSelector(".subnavi-right-sub"));
		} catch (NoSuchElementException e) {
			sub = element.findElement(By.cssSelector(".subnavi-left-sub"));
		}

		List<WebElement> seconds = sub.findElements(By.cssSelector(".subnavi-content-link"));

		List<String> list = new ArrayList<String>();
		for (WebElement temp : seconds) {
			list.add(temp.getText().trim());
		}
		return list;
	}

}
