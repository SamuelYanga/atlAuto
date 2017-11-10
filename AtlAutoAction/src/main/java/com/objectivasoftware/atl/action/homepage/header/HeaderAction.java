package com.objectivasoftware.atl.action.homepage.header;

import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.objectivasoftware.atl.core.component.HeaderComponent;
import com.objectivasoftware.atl.core.page.HomePage;
import com.objectivasoftware.atl.core.page.SearchPage;
import com.objectivasoftware.atl.core.util.Location;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class HeaderAction {

	@Given("打开主页。")
	public void openHomePage() {
		HomePage.open();
	}

	@And("ABO用户登录. userId=\"(.*)\" password=\"(.*)\"")
	public void aboLogin(String userId, String password) {
		HeaderComponent headerComponent = new HeaderComponent();
		headerComponent.checkLogin(userId, password);
	}

	@And("ABO用户登录（空购物车）. userId=\"(.*)\" password=\"(.*)\"")
	public void aboLoginWithCleanCart(String userId, String password) {
		HeaderComponent headerComponent = new HeaderComponent();
		headerComponent.checkLogin(userId, password, true);
	}

	@Then("验证成功登录后，页面头部显示用户名称。userName=\"(.*)\"")
	public void aboLoginSuccess(String userName) {
		HeaderComponent headerComponent = new HeaderComponent();
		String loginUserName = headerComponent.getLoginUserName();
		Assert.assertThat(loginUserName, containsString(userName));
	}

	@Then("验证过期用户登录后，页面头部显示续约提示信息。")
	public void aboLoginFail() {
		HomePage homePage = new HomePage();
		String noticeInfo = homePage.getExpiredUserMsg();
		Assert.assertThat(noticeInfo, containsString("日前需 完成續約以恢復相關會員權益"));
	}

	@And("点击mini购物车图标，进入购物车页面。")
	public void navigateToCart() {
		HeaderComponent headerComponent = new HeaderComponent();
		headerComponent.navigateToCartPage();
	}

	@And("点击購物專區，选择特惠活動。")
	public void navigateToPromotionListing() {
		HeaderComponent headerComponent = new HeaderComponent();
		headerComponent.openSecondMenu();
		headerComponent.selectPromotionListing();
	}

	@And("更换送货地点。 newLocation=\"(.*)\"")
	public void changeLocation(String newLocation) {
		HeaderComponent headerComponent = new HeaderComponent();
		headerComponent.openLocationDropdown();
		headerComponent.changeLocation(Location.getLocationByName(newLocation));
	}

	@And("验证header部的一二级菜单是否和主页左侧一二级菜单保持一致。")
	public void checkMenuUi() {
		HeaderComponent headerComponent = new HeaderComponent();
		Map<String, List<String>> headerInfo = headerComponent.getMenuInfo();

		SearchPage searchPage = new SearchPage();
		Map<String, List<String>> leftInfo = searchPage.getLeftMenuInfo();
		
		Assert.assertTrue(checkMap(headerInfo, leftInfo));
		
	}
	
	private boolean checkMap(Map<String, List<String>> headerInfo, Map<String, List<String>> leftInfo) {
		String message = null;
		for (Map.Entry<String, List<String>> header : headerInfo.entrySet()) {
			boolean flag0 = false;
			String headerFirstName = header.getKey();
			List<String> headerSecondName = header.getValue();
			for (Map.Entry<String, List<String>> left : leftInfo.entrySet()) {
				String leftFirstName = left.getKey();
				List<String> leftSecondName = left.getValue();
				if (headerFirstName.equals(leftFirstName)) {
					flag0 = true;
					int headerSize = headerSecondName.size();
					int leftSize = leftSecondName.size();
					if (headerSize != leftSize) {
						message = "【" + leftFirstName + "】" + " 目录下的二级菜单不一致。 ";
						flag0 = false;
						break;
					}
					for (int i = 0; i < headerSize; i++) {
						if (!headerSecondName.get(i).equals(leftSecondName.get(i))) {
							message = "【" + leftFirstName + "】" + " 目录下的 " + headerSecondName.get(i) + " 和 " + leftSecondName.get(i) + "有差异";
							flag0 = false;
							break;
						}
					}
					break;
				}
			}
			if (!flag0) {
				if (message != null) {
					System.out.println("***********************************************");
					System.out.println(message);
				}
				return false;
			}
		}
		return true;
	}
	


	@And("测试用。")
	public void test() {
		SearchPage searchPage = new SearchPage();
		searchPage.test();
	}
	
	
	
}
