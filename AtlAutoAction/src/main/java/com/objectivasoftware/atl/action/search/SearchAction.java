package com.objectivasoftware.atl.action.search;

import static org.hamcrest.Matchers.*;

import com.objectivasoftware.atl.core.component.HeaderComponent;
import com.objectivasoftware.atl.core.page.SearchPage;

import cucumber.api.java.en.And;

public class SearchAction {


	@And("搜索一个产品，然后点击进入产品详情页. productId=\"(.*)\"")
	public void searchProductAndNavigateToPdp(String productId) {
		HeaderComponent headerComponent = new HeaderComponent();
		headerComponent.searchProduct(productId);
		
		SearchPage searchPage = new SearchPage();
		searchPage.navigateToPdpByImg(productId);
	}
}
